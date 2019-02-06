package de.pfke.squeeze.squeezer.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.complex.Squeezer_caseClass_w_array_spec.caseClass_w_array

object Squeezer_caseClass_w_array_spec {
  case class caseClass_w_array(
    param1: Long,
    param2: Array[Byte],
  )
}

class Squeezer_caseClass_w_array_spec
  extends BaseSqueezerSpec {
  private val pojo = caseClass_w_array(
    param1 = 17433124l,
    param2 = Array(1, 23, 44),
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x01, 23, 44
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x01, 23, 44
  )

  "using w/ big endian byte order" when {
    implicit val byteOrderToUse: ByteOrder = ByteOrder.BIG_ENDIAN

    val squeezer = Squeezer()
    val binaryData = beBinaryData

    "convert correct to binary" should {
      "should return a ByteString with correct length" in {
        squeezer
          .toBinary(in = pojo)
          .length should be(binaryData.size)
      }

      "should return the correct ByteString" in {
        squeezer
          .toBinary(in = pojo) should be(binaryData)
      }
    }

    "convert correct from binary" should {
      "should return the correct param 1" in {
        squeezer
          .deSerialize[caseClass_w_array](binaryData).param1 should be (pojo.param1)
      }
      "should return the correct param 2.type" in {
        squeezer
          .deSerialize[caseClass_w_array](binaryData).param2.isInstanceOf[Array[Byte]] should be (right = true)
      }
      "should return the correct param 2.content" in {
        squeezer
          .deSerialize[caseClass_w_array](binaryData).param2.toList should be (pojo.param2.toList)
      }
    }
  }

  "using w/ little endian byte order" when {
    implicit val byteOrderToUse: ByteOrder = ByteOrder.LITTLE_ENDIAN

    val squeezer = Squeezer()
    val binaryData = leBinaryData

    "convert correct to binary" should {
      "should return a ByteString with correct length" in {
        squeezer
          .toBinary(in = pojo)
          .length should be(binaryData.size)
      }

      "should return the correct ByteString" in {
        squeezer
          .toBinary(in = pojo) should be(binaryData)
      }
    }

    "convert correct from binary" should {
      "should return the correct param 1" in {
        squeezer
          .deSerialize[caseClass_w_array](binaryData).param1 should be (pojo.param1)
      }
      "should return the correct param 2.type" in {
        squeezer
          .deSerialize[caseClass_w_array](binaryData).param2.isInstanceOf[Array[Byte]] should be (right = true)
      }
      "should return the correct param 2.content" in {
        squeezer
          .deSerialize[caseClass_w_array](binaryData).param2.toList should be (pojo.param2.toList)
      }
    }
  }
}
