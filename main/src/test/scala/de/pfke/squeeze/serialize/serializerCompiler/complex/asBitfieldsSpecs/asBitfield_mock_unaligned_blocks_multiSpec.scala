package de.pfke.squeeze.serialize.serializerCompiler.complex.asBitfieldsSpecs

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class asBitfield_mock_unaligned_blocks_multiSpec
  extends BaseCompilerSpec {
  "testing with simple asBitfield_mock_unaligned_blocks_multi type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val tto = createTTO[asBitfield_mock_unaligned_blocks_multi]()
    val value = asBitfield_mock_unaligned_blocks_multi(
      field01 = 0x81.toByte,
      field02 = 0x7e7e,
      field03 = 0x32,
      field04 = 0x12345678,
      field05 = 0x9a,
      field06 = 0xbc,
      field07 = 0x7841,
      field08 = 0x81.toByte,
      field09 = 0x145
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[asBitfield_mock_unaligned_blocks_multi](tto, 5, value))
      }
    }

    // 0x81 0x7e 0x7e 0x32 0x12 0x34 0x56 0x78 0x9a 0xbc
    // 0x78 0x41
    // 0x81
    // 0x00 0x00 0x01 0x45

    // resultat
    // 0x00 0x00 0x81 0x7e
    // 0x7e 0x32 0x12 0x34
    // 0x56 0x78 0x9a 0xbc
    // 0x78 0x41
    // 0x00 0x00 0x00 0x81
    // 0x00 0x00 0x01 0x45
    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x00, 0x00, 0x81.toByte, 0x7e,
          0x7e, 0x32, 0x12, 0x34,
          0x56, 0x78, 0x9a.toByte, 0xbc.toByte,
          0x78, 0x41,
          0x00, 0x00, 0x00, 0x81.toByte,
          0x00, 0x00, 0x01, 0x45
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(22)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x00, 0x00, 0x81.toByte, 0x7e,
          0x7e, 0x32, 0x12, 0x34,
          0x56, 0x78, 0x9a.toByte, 0xbc.toByte,
          0x78, 0x41,
          0x00, 0x00, 0x00, 0x81.toByte,
          0x00, 0x00, 0x01, 0x45
        ))
      }
    }
  }
}
