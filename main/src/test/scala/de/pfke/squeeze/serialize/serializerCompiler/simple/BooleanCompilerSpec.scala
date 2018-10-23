package de.pfke.squeeze.serialize.serializerCompiler.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class BooleanCompilerSpec
  extends BaseCompilerSpec
{
  "testing with simple Boolean type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    implicit val version = None

    val tto = createTTO[Boolean]()

    "read+write bit(s)" should {
      "[read] should read 0x01 as true" in {
        readBitString(tto, 5, 1l) should be (right = true)
      }

      "[read] should read 0x00 as false" in {
        readBitString(tto, 3, 0l) should be (right = false)
      }

      "[read] should read >0x00 as true" in {
        (1 to 32).foreach{ i =>
          withClue(s"checking value $i:") {
            readBitString(tto, 32, i) should be(right = true)
          }
        }
      }

      "[write] should return a ByteString with correct length" in {
        writeBitString(tto, 5, true).length should be (4)
      }

      "[write] should return correct packed ByteString" in {
        writeBitString(tto, 2, true) should be (ByteString(0, 0, 0, 0x01))
      }
    }

    "read+write byte(s)" should {
      "[read] should read 0x01 as true" in {
        readByteString(tto, 0x01) should be (right = true)
      }

      "[read] should read 0x00 as false" in {
        readByteString(tto, 0x00) should be (right = false)
      }

      "[read] should read >0x00 as true" in {
        (1 to Byte.MaxValue).foreach{ i =>
          withClue(s"checking $i") {
            readByteString(tto, i.toByte) should be(right = true)
          }
        }
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, true).length should be (1)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, true) should be (ByteString(0x01))
      }
    }
  }
}
