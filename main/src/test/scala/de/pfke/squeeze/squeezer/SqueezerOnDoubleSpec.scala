package de.pfke.squeeze.squeezer

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer

class SqueezerOnDoubleSpec
  extends BaseSqueezerSpec {
  "testing squeezer with simple Double type" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    val tto = Squeezer().serialize[Double](2147483648d)

    "testing with ByteOrder.BIG_ENDIAN" should {
      "should return a ByteString with correct length" in {
        tto.length should be (8)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString(0x41, 0xe0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00))
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder: ByteOrder = ByteOrder.LITTLE_ENDIAN
      val tto = Squeezer().serialize[Double](0xf308.toDouble)

      "should return a ByteString with correct length" in {
        tto.length should be (8)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString(0x00, 0x00, 0x00, 0x00, 0x00, 0x61, 0xee, 0x40))
      }
    }
  }

  "testing Squeezer.unpack with simple Double type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
      val value = 0x27451d3d
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Double](ByteString.newBuilder.putDouble(value).result()) should be (value)
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder: ByteOrder = ByteOrder.LITTLE_ENDIAN
      val value = 0x27451d3d
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[Double](ByteString.newBuilder.putDouble(value).result()) should be (value)
      }
    }
  }
}
