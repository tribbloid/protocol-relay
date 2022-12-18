package com.tribbloids.spookystuff.relay.msg

import com.tribbloids.spookystuff.relay.msg.Beans.RawToDummy.{fromProtocol, toProtocol}
import com.tribbloids.spookystuff.relay.{Protocol, RawProtocol, RelaySystem}

import java.util.Date

import scala.language.implicitConversions

object Beans {

  object DummyProtocol extends Protocol {

    case class _IR[D](data: D) extends _ThisIR[D]
  }

  object RawToDummy extends RelaySystem {

    override val fromProtocol = RawProtocol
    override val toProtocol = DummyProtocol
  }

  case class TimeWrapper(time: Date)

//  object TimeWrapper {
//
//    implicit object DummyBiRelay extends RawToDummy._BiRelayLike {
//
//      override def rootTag: String = "root"
//
//      override type From = Int
//      override type To = String
//
//      override object Adjoint extends ThisAdjoint {
//
//        override def convert(v: From): To = {
//
//          ???
//        }
//      }
//
//      override def convert(v: From): To = {
//
//        ???
//      }
//    }
//  }

  case class UsersWrapper(a: String, users: Users)

  case class Users(user: Seq[User])

  case class User(
      name: String,
      roles: Option[Roles] = None
  )

  case class Roles(role: Seq[String])

  case class Multipart(a: String, b: String)(c: Int = 10)

  case class BiRelayed(v: Int)

  object BiRelayed extends RawToDummy.BiRelayFrom[BiRelayed]() {

    object _Relay extends ~>[String] {

      override object Adjoint extends ThisAdjoint {

        override def convert(v: From): To = ???
      }

      override def convert(v: fromProtocol._IR[BiRelayed]): toProtocol._IR[String] = {
        DummyProtocol._IR(v.data.toString)
      }

      override def rootTag: String = "root"
    }

    implicit def getRelay: _Relay.type = _Relay
  }

//  case class Wrapper(vs: WithBiRelay) extends CanWrite

}
