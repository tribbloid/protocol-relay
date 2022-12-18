package com.tribbloids.spookystuff.relay

import scala.language.implicitConversions

object RawProtocol extends Protocol {

  case class _IR[D](data: D) extends _ThisIR[D] {}

  implicit def toIR[D](data: D): _IR[D] = _IR(data)
}
