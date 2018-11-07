package de.pfke.squeeze.serialize.serializerCompiler.annots

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_staticSize_Mock
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class InjectTotalLengthSpec
  extends BaseCompilerSpec {
  "testing with simple InjectTotalLengthMock type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val tto = createTTO[InjectTotalLength_staticSize_Mock]()
    val value = InjectTotalLength_staticSize_Mock(
      _1stParam = 5,
      _2ndParam = 8,
      _3rdParam = 9
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[InjectTotalLength_staticSize_Mock](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x00, 0x05,
          0x00, 0x08,
          0x00, 0x00, 0x00, 0x09
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(8)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x00, 0x05,
          0x00, 0x08,
          0x00, 0x00, 0x00, 0x09
        ))
      }
    }
  }
}
