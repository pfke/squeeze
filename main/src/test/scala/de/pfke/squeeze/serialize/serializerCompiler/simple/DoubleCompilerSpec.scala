package de.pfke.squeeze.serialize.serializerCompiler.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class DoubleCompilerSpec
  extends BaseCompilerSpec {
  "testing with simple Double type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val tto = createTTO[Double]()

    "read+write bit(s)" should {
      "[read] should read correct" in {
        readBitString(tto, 64, 5.25d.toLong) should be(5.0d)
      }

      "[write] should return a ByteString with correct length" in {
        writeBitString[Double](tto, 64, 13.5432d).length should be(8)
      }

      "[write] should return correct packed ByteString" in {
        writeBitString[Double](tto, 64, 138.97785d) should be(ByteString(0, 0, 0, 0, 0, 0, 0, 138))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto, 64, 68, -113, -33, 59, 100, 90, 29) should be(41.124d)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString[Double](tto, 13.3443d).length should be(8)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString[Double](tto, 41.124d) should be(ByteString(64, 68, -113, -33, 59, 100, 90, 29))
      }
    }
  }
}
