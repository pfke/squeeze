package de.pfke.squeeze.serialize.serializerCompiler.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class IntCompilerSpec
  extends BaseCompilerSpec {
  "testing with simple Int type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val tto = createTTO[Int]()

    "read+write bit(s)" should {
      "[read] should read correct" in {
        readBitString(tto, 32, 5256.toLong) should be(5256)
      }

      "[write] should return a ByteString with correct length" in {
        writeBitString[Int](tto, 32, 135432).length should be(4)
      }

      "[write] should return correct packed ByteString" in {
        writeBitString[Int](tto, 32, 13897785) should be(ByteString(0, -44, 16, 57))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto, 0, 0, -96, -92) should be(41124)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString[Int](tto, 133443).length should be(4)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString[Int](tto, 41124) should be(ByteString(0, 0, -96, -92))
      }
    }
  }
}
