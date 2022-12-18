package com.tribbloids.spookystuff.relay.msg

import com.tribbloids.spookystuff.relay.{Protocol, RawProtocol, RelaySystem}

object RawToMsg extends RelaySystem {

  override val fromProtocol = RawProtocol
  override val toProtocol = MsgProtocol

//  case class ReadView[F, T](
//      reader: JFieldReader[D]
//  )
}
