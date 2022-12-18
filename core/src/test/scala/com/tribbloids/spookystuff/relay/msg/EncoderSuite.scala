//package com.tribbloids.spookystuff.relay.msg
//
//import com.tribbloids.spookystuff.relay.FunSpecx
//import com.tribbloids.spookystuff.relay.msg.TestBeans._
//
//class EncoderSuite extends FunSpecx {
//
//  val user1 = User("1")
//  val user2 = User("2", Some(Roles(Seq("r1", "r2"))))
//  val map = Map(1 -> user1, 2 -> user2)
//
//  describe("memberStr") {
//
//    it("can print nested case classes") {
//      val writer = IRWriter(user1)
//      writer.memberStrPretty.shouldBe(
//        """
//          |User(
//          |  1,
//          |  None
//          |)
//        """.stripMargin
//      )
//    }
//    it("can print nested seq") {
//      val writer = IRWriter(user2)
//      writer.memberStrPretty.shouldBe(
//        """
//          |User(
//          |  2,
//          |  Some(
//          |    Roles(
//          |      ::(
//          |        r1,
//          |        r2
//          |      )
//          |    )
//          |  )
//          |)
//        """.stripMargin
//      )
//    }
//
//    it("can print nested map") {
//      val writer = IRWriter(map)
//      writer.memberStrPretty.shouldBe(
//        """
//          |Map2(
//          |  1=User(
//          |    1,
//          |    None
//          |  ),
//          |  2=User(
//          |    2,
//          |    Some(
//          |      Roles(
//          |        ::(
//          |          r1,
//          |          r2
//          |        )
//          |      )
//          |    )
//          |  )
//          |)
//        """.stripMargin
//      )
//    }
//  }
//}
