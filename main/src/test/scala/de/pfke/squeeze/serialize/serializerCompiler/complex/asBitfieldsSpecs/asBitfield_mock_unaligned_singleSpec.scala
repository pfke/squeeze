package de.pfke.squeeze.serialize.serializerCompiler.complex.asBitfieldsSpecs

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single
import de.pfke.squeeze.serialize.serializerBuilder.BuildByReflection
import de.pfke.squeeze.serialize.serializerCompiler.{BaseCompilerSpec, SerializerCompiler}

class asBitfield_mock_unaligned_singleSpec
  extends BaseCompilerSpec {
  "testing with simple asBitfield_mock_unaligned_single type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    implicit val version = None

    val tto = createTTO[asBitfield_mock_unaligned_single]()
    val value = asBitfield_mock_unaligned_single(
      field01 = 0x3
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[asBitfield_mock_unaligned_single](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x00, 0x03
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(2)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x00, 0x03
        ))
      }
    }
  }

  private def create() = SerializerCompiler.compile[asBitfield_mock_unaligned_single](BuildByReflection().build())
}
