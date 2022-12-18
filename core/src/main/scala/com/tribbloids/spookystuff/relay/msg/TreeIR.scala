//package com.tribbloids.spookystuff.relay.msg
//
//import com.tribbloids.spookystuff.relay.RootTagged
//import com.tribbloids.spookystuff.relay.utils.TreeView
//
//import scala.collection.immutable.ListMap
//
///**
//  * IR stands for "Intermediate Representation"
//  * @tparam LEAF
//  *   leaf type
//  */
//trait TreeIR[LEAF] extends IR[Any] {
//
//  import TreeIR._
//
//  def rootTagOvrd: Option[String]
//  final override lazy val rootTag = rootTagOvrd.getOrElse {
//    RootTagged.RootTagOf(data).default
//  }
//
//  def children: Seq[TreeIR[LEAF]]
//
//  case class DepthFirstTransform[V1 >: LEAF, V2, RES <: TreeIR[V2]](
//      downFn: TreeIR[V1] => TreeIR[V1],
//      onLeafFn: V1 => V2,
//      upFn: TreeIR[V2] => RES
//  ) {
//
//    def execute: RES = {
//
//      val afterDown: TreeIR[V1] = downFn(TreeIR.this.asInstanceOf[TreeIR[V1]])
//
//      val transformed: TreeIR[V2] = afterDown match {
//        case sub: Struct[V1] =>
//          sub.copy(
//            sub.repr.map {
//              case (k, v) =>
//                k -> v.DepthFirstTransform(downFn, onLeafFn, upFn).execute
//            }
//          )(sub.rootTagOvrd)
//        case Leaf(v) =>
//          Leaf(onLeafFn(v))()
//      }
//
//      val afterUp = upFn(transformed)
//
//      afterUp
//    }
//
//    def down[V1N >: LEAF](fn: TreeIR[V1N] => TreeIR[V1N]): DepthFirstTransform[V1N, V1N, TreeIR[V1N]] = { // reset onLeaf & up
//      DepthFirstTransform[V1N, V1N, TreeIR[V1N]](fn, identity _, identity _)
//    }
//
//    def onLeaf[V2N](fn: V1 => V2N): DepthFirstTransform[V1, V2N, TreeIR[V2N]] = { // reset up
//      DepthFirstTransform(downFn, fn, identity _)
//    }
//
//    def up[V2N >: V2, RES <: TreeIR[V2N]](fn: TreeIR[V2N] => RES): DepthFirstTransform[V1, V2N, RES] = {
//      DepthFirstTransform(downFn, onLeafFn, fn)
//    }
//  }
//
//  def depthFirstTransform: DepthFirstTransform[LEAF, LEAF, TreeIR[LEAF]] =
//    DepthFirstTransform[LEAF, LEAF, TreeIR[LEAF]](identity _, identity _, identity _)
//
//  def pathToValueMap: Map[Seq[String], LEAF]
//
//  def treeView: _TreeView = _TreeView(this)
//}
//
//object TreeIR {
//
//  lazy val _CLASS_NAME: String = classOf[TreeIR[_]].getSimpleName
//
//  case class Leaf[LEAF](repr: LEAF)(val rootTagOvrd: Option[String] = None) extends TreeIR[LEAF] with IR[LEAF] {
//
//    override def data: LEAF = repr
//
//    override def children: Seq[TreeIR[LEAF]] = Nil
//
//    override lazy val pathToValueMap: ListMap[Seq[String], LEAF] = ListMap(Nil -> repr)
//  }
//
//  case class Struct[LEAF](repr: ListMap[String, TreeIR[LEAF]])(val rootTagOvrd: Option[String] = None)
//      extends TreeIR[LEAF]
//      with IR[ListMap[String, Any]] {
//
//    override lazy val data: ListMap[String, Any] = {
//
//      val view: ListMap[String, Any] = repr.map {
//        case (k, v) =>
//          k -> v.data
//      }
//
//      view
//    }
//
//    override lazy val children: Seq[TreeIR[LEAF]] = repr.values.toSeq
//
//    override lazy val pathToValueMap: ListMap[Seq[String], LEAF] = {
//      repr.flatMap {
//        case (k, v) =>
//          v.pathToValueMap.map {
//            case (kk, v) =>
//              (Seq(k) ++ kk) -> v
//          }
//      }
//    }
//  }
//
//  case class Builder[V](rootTagOvrd: Option[String] = None) {
//
//    def struct(kvs: (String, TreeIR[_ <: V])*): Struct[V] = {
//      val _kvs = kvs.toSeq.map {
//        case (k, v) =>
//          k -> v.asInstanceOf[TreeIR[V]]
//      }
//      Struct(ListMap(_kvs: _*))(rootTagOvrd)
//    }
//
//    def fromMessage(v: Any): TreeIR[V] = {
//
//      v match {
//        case vs: collection.Map[String, Any] =>
//          val mapped: ListMap[String, TreeIR[V]] = ListMap(vs.toSeq: _*).map {
//            case (kk, vv) =>
//              kk -> Builder[V]().fromMessage(vv)
//          }
////          val prefixOpt = vs match {
////            case x: Product => Some(x.productPrefix)
////            case _          => None
////          }
//
//          Struct[V](mapped)(rootTagOvrd)
//
//        case vv: V =>
//          Leaf(vv)(rootTagOvrd)
//        case null =>
//          Leaf(null.asInstanceOf[V])(rootTagOvrd)
//        case _ =>
//          throw new UnsupportedOperationException(
//            s"JVM class ${v.getClass} cannot be mapped to ${_CLASS_NAME}"
//          )
//      }
//    }
//  }
//
//  case class _TreeView(self: TreeIR[_]) extends TreeView.Immutable[_TreeView] {
//    override lazy val nodeName: String = self.getClass.getSimpleName
//
//    override lazy val children: Seq[_TreeView] = self.children.map(_TreeView)
//
//    override def stringArgs: Iterator[Any] =
//      if (children.isEmpty) self.productIterator
//      else Iterator.empty
//  }
//}
