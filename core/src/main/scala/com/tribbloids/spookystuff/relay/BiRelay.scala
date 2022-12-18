package com.tribbloids.spookystuff.relay

trait BiRelay extends Relay {

  import BiRelay._

  trait ThisAdjoint extends BiRelay {

    final override def Adjoint = BiRelay.this

    final override def rootTag: String = BiRelay.this.rootTag

    final override type From = BiRelay.this.To
    final override type To = BiRelay.this.From
  }

  def Adjoint: Aux[To, From]
}

object BiRelay {

  type Aux[F, T] = BiRelay {
    type From = F
    type To = T
  } // TODO: remove this alias after symbol can be used directly
}
