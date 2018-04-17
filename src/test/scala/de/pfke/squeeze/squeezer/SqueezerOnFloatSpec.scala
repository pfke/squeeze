package de.pfke.squeeze.squeezer

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer

class SqueezerOnFloatSpec
  extends BaseSqueezerSpec {
  "testing squeezer with simple Float type" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    val tto = Squeezer().serialize[Float](2147483648f)

    "testing with ByteOrder.BIG_ENDIAN" should {
      "should return a ByteString with correct length" in {
        tto.length should be (4)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString(0x4f, 0x00, 0x00, 0x00))
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
      val tto = Squeezer().serialize[Float](0xf308.toFloat)

      "should return a ByteString with correct length" in {
        tto.length should be (4)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString(0x00, 0x08, 0x73, 0x47))
      }
    }
  }

  "testing Squeezer.unpack with simple Float type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder = ByteOrder.BIG_ENDIAN
      val value = 0x27451d3f
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Float](ByteString.newBuilder.putFloat(value).result()) should be (value)
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
      val value = 0x27451d3f
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Float](ByteString.newBuilder.putFloat(value).result()) should be (value)
      }
    }
  }
}
