package de.pfke.squeeze.serialize.serializerCompiler.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class LongCompilerSpec
  extends BaseCompilerSpec {
  "testing with simple Long type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    implicit val version = None

    val tto = createTTO[Long]()

    "read+write bit(s)" should {
      "[read] should read correct" in {
        readBitString(tto, 64, 525l) should be(525l)
      }

      "[write] should return a ByteString with correct length" in {
        writeBitString[Long](tto, 64, 13125432l).length should be(8)
      }

      "[write] should return correct packed ByteString" in {
        writeBitString[Long](tto, 64, 0x1389778560l) should be(ByteString(0, 0, 0, 0x13, 0x89, 0x77, 0x85, 0x60))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto, 0, 0, 0, 0, -11, 31, -99, 61) should be(4112489789l)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString[Long](tto, 133443l).length should be(8)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString[Long](tto, 4112489789l) should be(ByteString(0, 0, 0, 0, -11, 31, -99, 61))
      }
    }
  }
}
