package com.tribbloids.spookystuff.relay

trait RootTagged {

  def rootTag: String // = RootTagOf(this).fallback
}

object RootTagged {

  case class RootTagOf(chain: Any*) {

    val first: Any = chain.head // sanity check

    lazy val explicitOpt: Option[String] = {

      val trials = chain.toStream.map {
        case vv: RootTagged =>
          Some(vv.rootTag)

        case vv: Product =>
          Some(vv.productPrefix)
        case _ =>
          None
      }

      trials.collectFirst {
        case Some(v) => v
      }
    }

    lazy val fallback: String = first match {

      case _ =>
        first.getClass.getSimpleName.stripSuffix("$")

    }

    lazy val default: String = explicitOpt.getOrElse(fallback)
  }
}
