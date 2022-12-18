package com.tribbloids.spookystuff.relay

trait RelaySystem {

  val fromProtocol: Protocol
  val toProtocol: Protocol

  object Dual extends RelaySystem {

    final override val fromProtocol: RelaySystem.this.toProtocol.type = RelaySystem.this.toProtocol
    final override val toProtocol: RelaySystem.this.fromProtocol.type = RelaySystem.this.fromProtocol
  }

  sealed trait _RelayLike extends Relay {

    type From <: fromProtocol._IR[_]
    type To <: toProtocol._IR[_]
  }

  object _RelayLike {

//    trait Factory {
//
//      // can summon a Relay
//
//      // TODO: only applied to relay to JFieldProtocol
////      implicit class OpsOnFrom[F](v: F) {
////
////        def write(
////            implicit
////            relay: Relay.LtFrom[F]
////        ) = relay.write(v.asInstanceOf)
////      }
//    }
  }

  type _Relay[F, T] = Relay {

    type From = fromProtocol._IR[F]
    type To = toProtocol._IR[T]
  }

  class RelayFrom[F] {

    trait ~>[T] extends _RelayLike {

      final type From = fromProtocol._IR[F]
      final type To = toProtocol._IR[T]
    }
  }

  trait _BiRelayLike extends BiRelay with _RelayLike {}

  type _BiRelay[F, T] = BiRelay with _Relay[F, T] {}

  class BiRelayFrom[F] {

    trait ~>[T] extends _BiRelayLike {

      final type From = fromProtocol._IR[F]
      final type To = toProtocol._IR[T]
    }
  }

  trait CanArrowTo[T <: toProtocol.ThisIR] {

    def arrow: T
  }

  object CanArrowTo {

    trait AdHocRelay[TO <: toProtocol._IR[_]] extends _RelayLike {

      override type From = fromProtocol._IR[_ <: CanArrowTo[TO]]
      override type To = TO

      override def rootTag: String = "root"

      final override def convert(v: From): To = v.data.arrow
    }

    implicit def adHocRelay[TO <: toProtocol._IR[_]]: AdHocRelay[TO] = new AdHocRelay[TO] {}
  }

  // alternative definition
  trait CanArrowToRelay extends _RelayLike {

    trait _CanArrowTo extends CanArrowTo[To] {}
    override type From <: fromProtocol._IR[_ <: _CanArrowTo]

    final override def convert(v: From): To = v.data.arrow

//    implicit def asReader[T <: Relay](v: T)(
//        implicit
//        typeInfo: Manifest[v.From]
//    ): JFieldPlainReader[v.type] = v.read(typeInfo)

    //  trait Imp0 extends Implicits
    //
    //  trait Imp1 extends Imp0
  }
}

object RelaySystem {

//  trait Dual[T <: RelaySystem] extends RelaySystem {
//
//    protected val _outer: T
//
//    final override val fromProtocol: Protocol = _outer.toProtocol
//    final override val toProtocol: Protocol = _outer.fromProtocol
//  }

}
