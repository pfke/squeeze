package de.pfke.squeeze.serialize.serializerCompiler.annots

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.annots.WithFixedLengthMock
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class WithFixedLengthSpec
  extends BaseCompilerSpec {
  "testing with simple WithFixedLengthMock type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    implicit val version = None

    val tto = createTTO[WithFixedLengthMock]()
    val value = WithFixedLengthMock(
      _1stParam = "hello"
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[WithFixedLengthMock](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x68, 0x65, 0x6c, 0x6c, 0x6f
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(5)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x68, 0x65, 0x6c, 0x6c, 0x6f
        ))
      }
    }
  }
}
