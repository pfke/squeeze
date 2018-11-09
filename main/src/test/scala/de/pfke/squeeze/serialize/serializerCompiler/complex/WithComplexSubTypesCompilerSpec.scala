package de.pfke.squeeze.serialize.serializerCompiler.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.{SubSubWithComplexSubTypesMock, SubWithComplexSubTypesMock, WithComplexSubTypesMock}
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class WithComplexSubTypesCompilerSpec
  extends BaseCompilerSpec {
  "testing with simple WithComplexSubTypesMock type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    def tto = createTTO[WithComplexSubTypesMock]()
    val value = WithComplexSubTypesMock(
      _1stParam = 0x14,
      _2ndParam = SubWithComplexSubTypesMock(
        _1stParam = true,
        _2ndParam = 0x32,
        _3rdParam = SubSubWithComplexSubTypesMock(
          _1stParam = 0x87654321,
          _2ndParam = 215468741l,
          _3rdParam = 0x8974.toShort
        ),
        _4thParam = 5468d,
        _5thParam = 988773f
      ),
      _3rdParam = 12346d
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[WithComplexSubTypesMock](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x14,
          0x01,
          0x32,
          0x87.toByte, 0x65, 0x43, 0x21,
          0x00, 0x00, 0x00, 0x00, 0x0c, 0xd7.toByte, 0xca.toByte, 0xc5.toByte,
          0x89.toByte, 0x74,
          0x40, 0xb5.toByte, 0x5c, 0x00, 0x00, 0x00, 0x00, 0x00,
          0x49, 0x71, 0x66, 0x50,
          0x40, 0xc8.toByte, 0x1d, 0x00, 0x00, 0x00, 0x00, 0x00
        ) should be (value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(37)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x14,
          0x01,
          0x32,
          0x87.toByte, 0x65, 0x43, 0x21,
          0x00, 0x00, 0x00, 0x00, 0x0c, 0xd7.toByte, 0xca.toByte, 0xc5.toByte,
          0x89.toByte, 0x74,
          0x40, 0xb5.toByte, 0x5c, 0x00, 0x00, 0x00, 0x00, 0x00,
          0x49, 0x71, 0x66, 0x50,
          0x40, 0xc8.toByte, 0x1d, 0x00, 0x00, 0x00, 0x00, 0x00
        ))
      }
    }
  }
}
