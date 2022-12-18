//package com.tribbloids.spookystuff.relay
//
///**
//  * a simple MessageRelay that use object directly as Message
//  */
//class SelfRelay[T] extends BiRelay {
//  // TODO: should be removed and use only reader & writer API
//
//  type From = T
//  type To = T
//
//  override def convert(v: IR[T]): IR[T] = v
//
//  override def rootTag: String = "root"
//
//  override def Dual: SelfRelay[T] = this
//}
