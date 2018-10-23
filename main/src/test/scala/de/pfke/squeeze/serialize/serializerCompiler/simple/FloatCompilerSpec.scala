package de.pfke.squeeze.serialize.serializerCompiler.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class FloatCompilerSpec
  extends BaseCompilerSpec {
  "testing with simple Float type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    implicit val version = None

    val tto = createTTO[Float]()

    "read+write bit(s)" should {
      "[read] should read correct" in {
        readBitString(tto, 32, 5.25f.toLong) should be(5.0f)
      }

      "[write] should return a ByteString with correct length" in {
        writeBitString[Float](tto, 32, 13.5432f).length should be(4)
      }

      "[write] should return correct packed ByteString" in {
        writeBitString[Float](tto, 32, 138.97785f) should be(ByteString(0, 0, 0, 138))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto, 66, 36, 126, -6) should be(41.124f)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString[Float](tto, 13.3443f).length should be(4)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString[Float](tto, 41.124f) should be(ByteString(66, 36, 126, -6))
      }
    }
  }
}
