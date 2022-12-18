package com.tribbloids.spookystuff.relay.utils

object Commons {

  def numLocalCores: Int = {
    val result = Runtime.getRuntime.availableProcessors()
    assert(result > 0)
    result
  }

  def objectSimpleName(v: Object): String = {
    v.getClass.getSimpleName
      .stripSuffix("$")
      .split('$')
      .filter(_.nonEmpty)
      .head
  }

  def indent(text: String, str: String = "\t"): String = {
    text.split('\n').filter(_.nonEmpty).map(str + _).mkString("\n")
  }
}
