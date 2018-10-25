package de.pfke.squeeze.squeezer

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer

class SqueezerOnShortSpec
  extends BaseSqueezerSpec {
  "testing squeezer with simple Short type" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    val value = 0x1235.toShort
    val tto = Squeezer().serialize[Short](value)

    "testing with ByteOrder.BIG_ENDIAN" should {
      "should return a ByteString with correct length" in {
        tto.length should be (2)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString.newBuilder.putShort(value).result())
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder: ByteOrder = ByteOrder.LITTLE_ENDIAN
      val value = 0xf308.toShort
      val tto = Squeezer().serialize[Short](value)

      "should return a ByteString with correct length" in {
        tto.length should be (2)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString.newBuilder.putShort(value).result())
      }
    }
  }

  "testing Squeezer.unpack with simple Short type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
      val value = 0x2745.toShort
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Short](ByteString.newBuilder.putShort(value).result()) should be (value)
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder: ByteOrder = ByteOrder.LITTLE_ENDIAN
      val value = 0x2745.toShort
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Short](ByteString.newBuilder.putShort(value).result()) should be (value)
      }
    }
  }
}
