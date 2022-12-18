package com.tribbloids.spookystuff.relay.msg

import com.tribbloids.spookystuff.relay.xml.{XMLFormats, Xml}
import org.json4s.JsonAST.JObject
import org.json4s.jackson.JsonMethods._
import org.json4s.{Extraction, Formats, JValue}

import scala.language.implicitConversions
import scala.xml.NodeSeq

case class JFieldEncoder[M](
    ir: MsgProtocol._IR[M],
    formats: Formats = XMLFormats.defaultFormats
) extends Serializable {

  // TODO: move into case class WFormats(.) and enable lazy val
  def toJValue: JValue = Extraction.decompose(ir)(formats)
  def compactJSON: String = compact(render(toJValue))
  def prettyJSON: String = pretty(render(toJValue))
  def toJSON(pretty: Boolean = true): String = {
    if (pretty) prettyJSON
    else compactJSON
  }

  def toXMLNode: NodeSeq =
    Xml.toXml(JObject(ir.rootTag -> toJValue))
  def compactXML: String = toXMLNode.toString().replaceAllLiterally("\n", "")
  def prettyXML: String = XMLFormats.defaultXMLPrinter.formatNodes(toXMLNode)
  def toXMLStr(pretty: Boolean = true): String = {
    if (pretty) prettyXML
    else compactXML
  }

  def cast[R](
      implicit
      readerOps: JFieldDecoder.ReaderOps[R]
  ): R = {
    val jv = toJValue
    val result = readerOps.fromJValue(jv)

    result
  }

  object MemberStr {}

  //  TODO: this should be a capability of TreeIR?
//  def getMemberStr(
//      start: String = "(",
//      sep: String = ",",
//      end: String = ")",
//      indentFn: Int => String = _ => "",
//      recursion: Int = 0
//  ): String = {
//
//    val indentStr = indentFn(recursion)
//
//    def listRecursion(elems: Traversable[Any]): List[String] = {
//      elems.toList
//        .map { vv =>
//          val str = JFieldWriter(vv).getMemberStr(start, sep, end, indentFn, recursion + 1)
//          Commons.indent(str, indentStr)
//        }
//    }
//
//    def mapRecursion[T](map: Map[T, Any]): Seq[String] = {
//      map.toSeq
//        .map {
//          case (kk, vv) =>
//            val vvStr = JFieldWriter(vv).getMemberStr(start, sep, end, indentFn, recursion + 1)
//            Commons.indent(s"$kk=$vvStr", indentStr)
//        }
//    }
//
//    def product2Str(v: Product): String = {
//      val elems = v.productIterator.toList
//
//      val vIsSingleton = {
//
//        val className = v.getClass.getName
//        className.endsWith("$")
//      }
//
//      val concat = if (elems.isEmpty || vIsSingleton) {
//        ir.rootTag
//      } else {
//        val strs: List[String] = listRecursion(elems)
//
//        strs.mkString(ir.rootTag + start, sep, end)
//      }
//      concat
//    }
//
//    ir match {
//
////      case is: TreeIR.ProductMap =>
////        product2Str(is)
//
//      case is: Map[_, _] =>
//        val strs = mapRecursion(is)
//        val concat =
//          if (strs.nonEmpty)
//            strs.mkString(ir.rootTag + start, sep, end)
//          else
//            ir.rootTag + start + end
//        concat
//
//      case is: Traversable[_] =>
//        val strs = listRecursion(is)
//        val concat =
//          if (strs.nonEmpty)
//            strs.mkString(ir.rootTag + start, sep, end)
//          else
//            ir.rootTag + start + end
//        concat
//
//      case v: Product =>
//        product2Str(v)
//
//      case _ =>
//        "" + ir // TODO: should we allow this fallback?
//    }
//  }
//
//  lazy val memberStr: String = this.getMemberStr()
//  lazy val memberStr_\\\ : String = this.getMemberStr(File.separator, File.separator, File.separator)
//  lazy val memberStr_/:/ : String = this.getMemberStr("/", "/", "/")
//  lazy val memberStrPretty: String = this.getMemberStr(
//    "(\n",
//    ",\n",
//    "\n)",
//    { _ =>
//      "  "
//    }
//  )
}

object JFieldEncoder {

//  trait HasIRWriter[FROM] {
//
//    implicit def toWriter(v: FROM)(
//        implicit
//        relay: FROM >~> _
//    ): JFieldEncoder[_] = relay.write(v)
//  }
}
