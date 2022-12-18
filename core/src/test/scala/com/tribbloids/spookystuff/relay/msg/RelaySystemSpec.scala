package com.tribbloids.spookystuff.relay.msg

import com.tribbloids.spookystuff.relay.{FunSpecx, RawProtocol, Relay}

class RelaySystemSpec extends FunSpecx {

  import Beans._

  it("summon Relay") {

    import BiRelayed.getRelay

//    val summoned = implicitly[Relay.AuxFrom[RawProtocol._IR[BiRelayed]]]
    val summoned2 = implicitly[RawToDummy._Relay[BiRelayed, _]]
    summoned2
  }

//  it("summon Adjoint") {
//
//    val summoned = implicitly[RawToDummy.Dual._Relay[_, WithBiRelay]]
//    summoned
//  }
}
