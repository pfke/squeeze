package de.pfke.squeeze.squeezer

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer

class SqueezerOnByteSpec
  extends BaseSqueezerSpec {
  "testing squeezer with simple Byte type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder = ByteOrder.BIG_ENDIAN
      val tto = Squeezer().serialize[Byte](0x13)

      "should return a ByteString with correct length" in {
        tto.length should be (1)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString(0x13))
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
      val tto = Squeezer().serialize[Byte](0xf3.toByte)

      "should return a ByteString with correct length" in {
        tto.length should be (1)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString(0xf3))
      }
    }
  }

  "testing Squeezer.unpack with simple Byte type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder = ByteOrder.BIG_ENDIAN
      val value = 0x27.toByte
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Byte](ByteString.newBuilder.putByte(value).result()) should be (value)
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
      val value = 0x13.toByte
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Byte](ByteString.newBuilder.putByte(value).result()) should be (value)
      }
    }
  }
}
