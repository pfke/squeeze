package de.pfke.squeeze.serialize.serializerCompiler.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class CharCompilerSpec
  extends BaseCompilerSpec {
  "testing with simple Byte type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    implicit val version = None

    val tto = createTTO[Char]()

    "read+write bit(s)" should {
      "[read] should read correct" in {
        readBitString(tto, 5, 0x03) should be(0x03)
      }

      "[write] should return a ByteString with correct length" in {
        writeBitString[Char](tto, 5, 0x13).length should be(4)
      }

      "[write] should return correct packed ByteString" in {
        writeBitString[Char](tto, 2, 0x13) should be(ByteString(0, 0, 0, 0x03))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto, 0x41, 0x51) should be(0x4151)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString[Char](tto, 0x1321).length should be(2)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString[Char](tto, 0x5413) should be(ByteString(0x54, 0x13))
      }
    }
  }
}
