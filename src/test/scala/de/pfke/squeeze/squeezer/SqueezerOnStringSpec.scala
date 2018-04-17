package de.pfke.squeeze.squeezer

import java.nio.ByteOrder
import java.nio.charset.StandardCharsets

import akka.util.ByteString
import de.pfke.squeeze.Squeezer

class SqueezerOnStringSpec
  extends BaseSqueezerSpec {
  "testing squeezer with simple String type" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    val value = "hallo du"
    val tto = Squeezer().serialize[String](value)

    "testing with ByteOrder.BIG_ENDIAN" should {
      "should return a ByteString with correct length" in {
        tto.length should be (value.length)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString(value, StandardCharsets.ISO_8859_1.name()))
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
      val value = "Hallo dingens"
      val tto = Squeezer().serialize[String](value)

      "should return a ByteString with correct length" in {
        tto.length should be (value.length)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString(value, StandardCharsets.ISO_8859_1.name()))
      }
    }
  }

  "testing Squeezer.unpack with simple String type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder = ByteOrder.BIG_ENDIAN
      val value = "klklök"
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[String](ByteString(value, StandardCharsets.ISO_8859_1.name())) should be (value)
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
      val value = "kljljlkjljkljkl"
      val squeezer = Squeezer()

      "should unpack correct" in {
        squeezer.deSerialize[String](ByteString(value, StandardCharsets.ISO_8859_1.name())) should be (value)
      }
    }
  }
}
