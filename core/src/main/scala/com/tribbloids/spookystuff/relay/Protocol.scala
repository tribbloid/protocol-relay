package com.tribbloids.spookystuff.relay

trait Protocol {

  trait ThisIR extends Protocol.IR {

    final type _Protocol = Protocol.this.type
    final val protocol: Protocol.this.type = Protocol.this

  }

  trait _ThisIR[D] extends ThisIR {

    final override type Data = D
  }

  type _IR[D] <: _ThisIR[D]

  // protocol should always be a type class of its IR
//  implicit def irToProtocol[T <: ThisIR]: Protocol.Lt[T] = this
}

object Protocol {

  type Lt[T <: Protocol.IR] = Protocol { type IR >: T }

  /**
    * Intermediate Representation
    */
  trait IR {
    type _Protocol <: Protocol
    val protocol: _Protocol

    type Data
    def data: Data
  }
}
