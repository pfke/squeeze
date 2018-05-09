package de.pfke.squeeze.squeezer

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer

class SqueezerOnBooleanSpec
  extends BaseSqueezerSpec {
  "testing Squeezer.pack with simple Boolean type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder = ByteOrder.BIG_ENDIAN
      val tto = Squeezer().serialize(true)

      "should return a ByteString with correct length" in {
        tto.length should be (1)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString(0x01))
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
      val tto = Squeezer().serialize(false)

      "should return a ByteString with correct length" in {
        tto.length should be (1)
      }

      "should return correct packed ByteString" in {
        tto should be (ByteString(0x00))
      }
    }
  }

  "testing Squeezer.unpack with simple Boolean type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder = ByteOrder.BIG_ENDIAN
      val squeezer = Squeezer()

      "should unpack 0x01 as true" in {
        squeezer.deSerialize[Boolean](ByteString(0x01)) shouldBe (right = true)
      }

      "should unpack 0x00 as false" in {
        squeezer.deSerialize[Boolean](ByteString(0x00)) shouldBe (right = false)
      }

      "should unpack >0x00 as true" in {
        (1 to Byte.MaxValue).foreach{ i =>
          withClue(s"checking $i") {
            squeezer.deSerialize[Boolean](ByteString(i)) shouldBe (right = true)
          }
        }
      }
    }

    "testing with ByteOrder.LITTLE_ENDIAN" should {
      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
      val squeezer = Squeezer()

      "should unpack 0x01 as true" in {
        squeezer.deSerialize[Boolean](ByteString(0x01)) shouldBe (right = true)
      }

      "should unpack 0x00 as false" in {
        squeezer.deSerialize[Boolean](ByteString(0x00)) shouldBe (right = false)
      }

      "should unpack >0x00 as true" in {
        (1 to Byte.MaxValue).foreach{ i =>
          withClue(s"checking $i") {
            squeezer.deSerialize[Boolean](ByteString(i)) shouldBe(right = true)
          }
        }
      }
    }
  }
}
