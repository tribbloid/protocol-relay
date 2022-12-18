package com.tribbloids.spookystuff.relay

import com.tribbloids.spookystuff.relay.xml.XMLFormats
import org.json4s.Formats

trait Relay extends RootTagged {

  type From <: Protocol.IR
  type To <: Protocol.IR

  def fallbackFormats: Formats = XMLFormats.defaultFormats

  def convert(v: From): To

  final def toData(v: From) = convert(v).data

  //    final def write(v: From): JFieldWriter[To] = {
  //
  //      val ir = convert(v)
  //      JFieldWriter[To](
  //        ir,
  //        this.fallbackFormats
  //      )
  //    }
  //
  //    final def read(
  //        implicit
  //        typeInfo: Manifest[From]
  //    ): JFieldPlainReader[this.type] = {
  //
  //      JFieldReader[this.type](
  //        this,
  //        fallbackFormats,
  //        rootTag
  //      )
  //    }

  //    trait HasRelay {
  //
  //      def _relay: Relay.this.type = Relay.this
  //    }

  //    implicit def getRelay: Relay.this.type = this
}

object Relay {

  type Aux[F, T] = Relay {

    type From = F
    type To = T
  }

  type AuxFrom[F] = Relay {

    type From >: F
  }

  implicit def summonByAdjoint[T, F](
      implicit
      adjoint: BiRelay.Aux[T, F]
  ): Relay.AuxFrom[F] = {

    adjoint.Adjoint
  }
}
