package de.pfke.squeeze.serialize.serializerCompiler.complex.asBitfieldsSpecs

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class asBitfield_mock_unaligned_multiSpec
  extends BaseCompilerSpec {
  "testing with simple asBitfield_mock_unaligned_multi type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val tto = createTTO[asBitfield_mock_unaligned_multi]()
    val value = asBitfield_mock_unaligned_multi(
      field01 = 0x3,
      field02 = 0x2
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[asBitfield_mock_unaligned_multi](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x00, 0x1a
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(2)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x00, 0x1a
        ))
      }
    }
  }
}
