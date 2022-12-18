//package com.tribbloids.spookystuff.relay
//
//import org.apache.spark.annotation.DeveloperApi
//
///**
//  * :: DeveloperApi :: ML Param only supports string & vectors, this class extends support to all objects
//  */
//@DeveloperApi
//case class RelayedMLParam[FROM, TO](
//    biRelay: FROM <~> TO,
//    override val parent: String,
//    override val name: String,
//    override val doc: String,
//    override val isValid: FROM => Boolean
//)(
//    implicit
//    typeInfo: Manifest[TO]
//) extends org.apache.spark.ml.param.Param[FROM](parent, name, doc, isValid) {
//
//  override def jsonEncode(value: FROM): String = {
//
//    biRelay.write(value).compactJSON
//  }
//
//  override def jsonDecode(json: String): FROM = {
//
//    val ir: IR[FROM] = biRelay.Dual.read(typeInfo).fromJSON(json)
//    ir.data
//  }
//}
