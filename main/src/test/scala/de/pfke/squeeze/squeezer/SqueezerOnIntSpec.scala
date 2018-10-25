package de.pfke.squeeze.squeezer

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer

class SqueezerOnIntSpec
  extends BaseSqueezerSpec {
  "testing squeezer with simple Int type" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    val value = 0x12350047
    val tto = Squeezer().serialize[Int](value)

    "testing with ByteOrder.BIG_ENDIAN" should {
      "should return a ByteString with correct length" in {
        tto.length should be (4)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString.newBuilder.putInt(value).result())
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder: ByteOrder = ByteOrder.LITTLE_ENDIAN
      val value = 0xf308
      val tto = Squeezer().serialize[Int](value)

      "should return a ByteString with correct length" in {
        tto.length should be (4)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString.newBuilder.putInt(value).result())
      }
    }
  }

  "testing Squeezer.unpack with simple Int type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
      val value = 0x27451d3
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Int](ByteString.newBuilder.putInt(value).result()) should be (value)
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder: ByteOrder = ByteOrder.LITTLE_ENDIAN
      val value = 0x27451d3
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Int](ByteString.newBuilder.putInt(value).result()) should be (value)
      }
    }
  }
}
