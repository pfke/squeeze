package de.pfke.squeeze.squeezer

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer

class SqueezerOnCharSpec
  extends BaseSqueezerSpec {
  "testing squeezer with simple Char type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder = ByteOrder.BIG_ENDIAN
      val tto = Squeezer().serialize[Char](0x1312.toChar)

      "should return a ByteString with correct length" in {
        tto.length should be (2)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString(0x13, 0x12))
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
      val tto = Squeezer().serialize[Char](0xf308.toChar)

      "should return a ByteString with correct length" in {
        tto.length should be (2)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString(0x08, 0xf3))
      }
    }
  }

  "testing Squeezer.unpack with simple Char type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder = ByteOrder.BIG_ENDIAN
      val value = 0x2745.toChar
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Char](ByteString.newBuilder.putShort(value).result()) should be (value)
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
      val value = 0x2745.toChar
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Char](ByteString.newBuilder.putShort(value).result()) should be (value)
      }
    }
  }
}
