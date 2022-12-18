package com.tribbloids.spookystuff.relay.msg

import com.tribbloids.spookystuff.relay.msg.JFieldDecoder.ReaderOps
import com.tribbloids.spookystuff.relay.xml.Xml
import org.json4s.JsonAST.{JArray, JObject}
import org.json4s.jackson.JsonMethods
import org.json4s.{Extraction, Formats, JField, JValue}

import scala.xml.{Elem, NodeSeq, XML}

trait JFieldDecoder[D] {

  import MsgProtocol._

  def jFieldToIR(jf: JField): _IR[D]

  case class AndThen[R](
      defaultRootTag: String
  )(
      fn: _IR[D] => R
  ) extends ReaderOps[R] {

    final def fromJField(jf: JField): R = {

      fn(jFieldToIR(jf))
    }
  }
}

object JFieldDecoder {

  trait ReaderOps[R] {

    def defaultRootTag: String

    def fromJField(jf: JField): R

    final def fromJValue(jv: JValue) = {
      fromJField(JField(defaultRootTag, jv))
    }

    final def fromJSON(json: String) = fromJValue(JsonMethods.parse(json))

    final def fromXMLNode(ns: NodeSeq) = {
      val jv: JValue = Xml.toJson(ns)
      jv match {
        case JObject(kvs) =>
          fromJField(kvs.head)
        case JArray(vs) =>
          fromJValue(vs.head)
        case _ =>
          fromJValue(jv) // TODO: not possible!
      }
    }

    final def fromXML(xml: String) = {
      val nodes: Elem = JFieldDecoder.xmlStr2Node(xml)

      fromXMLNode(nodes)
    }
  }

  case class Plain[D](
      formats: Formats,
      rootTag: String
  )(
      implicit
      val typeInfo: Manifest[D]
  ) extends JFieldDecoder[D] {

    def jFieldToIR(jf: JField): MsgProtocol._IR[D] = {

      val m = Extraction.extract[D](jf._2)(formats, typeInfo)
      MsgProtocol.PlainIR(m)
    }
  }

//  case class Tree[D] // TODO: impl later for TreeIR

  final def xmlStr2Node(xml: String): Elem = {
    val bomRemoved = xml.replaceAll("[^\\x20-\\x7e]", "").trim // remove BOM (byte order mark)
    val prologRemoved = bomRemoved.replaceFirst("[^<]*(?=<)", "")
    val result = XML.loadString(prologRemoved)
    result
  }
}
