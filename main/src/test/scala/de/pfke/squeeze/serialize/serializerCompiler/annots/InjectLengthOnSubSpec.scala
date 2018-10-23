package de.pfke.squeeze.serialize.serializerCompiler.annots

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.annots.{InjectLengthOnSubMock, SubInjectLengthOnSubMock}
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class InjectLengthOnSubSpec
  extends BaseCompilerSpec {
  "testing with simple InjectLengthOnSubMock type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    implicit val version = None

    val tto = createTTO[InjectLengthOnSubMock]()
    val value = InjectLengthOnSubMock(
      _1stParam = SubInjectLengthOnSubMock(
        _1stParam = 5,
        _2ndParam = "heiko"
      )
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[InjectLengthOnSubMock](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x00, 0x05,
          0x68, 0x65, 0x69, 0x6b, 0x6f
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(7)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x00, 0x05,
          0x68, 0x65, 0x69, 0x6b, 0x6f
        ))
      }
    }
  }
}
