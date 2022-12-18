package com.tribbloids.spookystuff.relay.utils

import org.apache.spark.sql.catalyst.trees.TreeNode

// TODO: Spark TreeNode doesn't support multi-row for a node, will move to my own implementation
abstract class TreeView[BaseType <: TreeView[BaseType]] extends TreeNode[BaseType] {
  self: BaseType =>

  final override def verboseString(maxFields: Int): String = {
    simpleString(maxFields)
  }

  final override def simpleStringWithNodeId(): String = simpleString(0)
}

object TreeView {

  trait Immutable[BaseType <: Immutable[BaseType]] extends TreeView[BaseType] {
    self: BaseType =>

    final override def withNewChildrenInternal(newChildren: IndexedSeq[BaseType]): BaseType = {
//      ???
      throw new UnsupportedOperationException(s"${this.getClass.getName} is an immutable tree view")
    }
  }
}
