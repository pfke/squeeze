package de.pfke.squeeze.squeezer

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer

class SqueezerOnLongSpec
  extends BaseSqueezerSpec {
  "testing squeezer with simple Long type" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    val value = 0x12350047l
    val tto = Squeezer().serialize[Long](value)

    "testing with ByteOrder.BIG_ENDIAN" should {
      "should return a ByteString with correct length" in {
        tto.length should be (8)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString.newBuilder.putLong(value).result())
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
      val value = 0xf308l
      val tto = Squeezer().serialize[Long](value)

      "should return a ByteString with correct length" in {
        tto.length should be (8)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString.newBuilder.putLong(value).result())
      }
    }
  }

  "testing Squeezer.unpack with simple Long type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder = ByteOrder.BIG_ENDIAN
      val value = 0x27451d3l
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Long](ByteString.newBuilder.putLong(value).result()) should be (value)
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
      val value = 0x27451d3l
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Long](ByteString.newBuilder.putLong(value).result()) should be (value)
      }
    }
  }
}
