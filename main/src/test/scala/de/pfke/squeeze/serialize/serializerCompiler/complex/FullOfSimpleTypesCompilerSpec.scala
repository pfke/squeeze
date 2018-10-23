package de.pfke.squeeze.serialize.serializerCompiler.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.FullOfSimpleTypesMock
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class FullOfSimpleTypesCompilerSpec
  extends BaseCompilerSpec {
  "testing with simple FullOfSimpleTypesMock type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    implicit val version = None

    val tto = createTTO[FullOfSimpleTypesMock]()
    val value = FullOfSimpleTypesMock(
      _1stParam = true,
      _2ndParam = 34.toByte,
      _3rdParam = 0x4390.toChar,
      _4thParam = 592382d,
      _5thParam = -12345f,
      _6thParam = 0x901f2600,
      _7thParam = 901001020304l,
      _8thParam = 0xe9a2.toShort
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[FullOfSimpleTypesMock](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x01,
          34,
          0x43, 0x90.toByte,
          0x41, 0x22, 0x13, 0xfc.toByte, 0x00, 0x00, 0x00, 0x00,
          0xc6.toByte, 0x40, 0xe4.toByte, 0x00,
          0x90.toByte, 0x1f, 0x26, 0x00,
          0x00, 0x00, 0x00, 0xd1.toByte, 0xc7.toByte, 0xd8.toByte, 0x83.toByte, 0x90.toByte,
          0xe9.toByte, 0xa2.toByte
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(30)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x01,
          34,
          0x43, 0x90.toByte,
          0x41, 0x22, 0x13, 0xfc.toByte, 0x00, 0x00, 0x00, 0x00,
          0xc6.toByte, 0x40, 0xe4.toByte, 0x00,
          0x90.toByte, 0x1f, 0x26, 0x00,
          0x00, 0x00, 0x00, 0xd1.toByte, 0xc7.toByte, 0xd8.toByte, 0x83.toByte, 0x90.toByte,
          0xe9.toByte, 0xa2.toByte
        ))
      }
    }
  }
}