package de.pfke.squeeze.serialize.serializerCompiler.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class StringCompilerSpec
  extends BaseCompilerSpec {
  "testing with simple Byte type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val tto = createTTO[String]()

    "read+write bit(s)" should {
      "[read] should read correct" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should return a ByteString with correct length" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[String](tto, 5, ".."))
      }

      "[write] should return correct packed ByteString" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[String](tto, 2, ".-"))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto, 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x68, 0x65, 0x69, 0x6b, 0x6f) should be("hello heiko")
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString[String](tto, "hello heiko").length should be(11)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString[String](tto, "hello heiko") should be(ByteString(0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x68, 0x65, 0x69, 0x6b, 0x6f))
      }
    }
  }
}
