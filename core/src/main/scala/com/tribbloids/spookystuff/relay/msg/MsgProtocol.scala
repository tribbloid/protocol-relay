package com.tribbloids.spookystuff.relay.msg

import com.tribbloids.spookystuff.relay.{Protocol, RootTagged}

import scala.language.implicitConversions

object MsgProtocol extends Protocol {

  trait _IR[D] extends _ThisIR[D] with RootTagged {

    def data: D

    override def rootTag: String = "root"
  }

  case class PlainIR[D](
      override val data: D,
      override val rootTag: String
  ) extends _IR[D]

  object PlainIR {

    def apply[D](msg: D): PlainIR[D] = PlainIR(msg, RootTagged.RootTagOf(msg).default)

    implicit def _apply[MSG](msg: MSG): PlainIR[MSG] = apply(msg)
  }
}
