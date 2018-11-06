package de.pfke.squeeze.serialize.serializerCompiler.complex.asBitfieldsSpecs

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_multi
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class asBitfield_mock_aligned_multiSpec
  extends BaseCompilerSpec {
  "testing with simple asBitfield_mock_aligned_multi type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val tto = createTTO[asBitfield_mock_aligned_multi]()
    val value = asBitfield_mock_aligned_multi(
      field01 = 0x81.toByte,
      field02 = 0x7e7e.toShort,
      field03 = 0x32.toByte,
      field04 = 0x12345678
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[asBitfield_mock_aligned_multi](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x81.toByte,
          0x7e, 0x7e,
          0x32,
          0x12, 0x34, 0x56, 0x78
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(8)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x81.toByte,
          0x7e, 0x7e,
          0x32,
          0x12, 0x34, 0x56, 0x78
        ))
      }
    }
  }
}
