package de.pfke.squeeze.serialize.serializerCompiler.annots

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.annots.WithFixedCountMock
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class WithFixedCountSpec
  extends BaseCompilerSpec {
  "testing with simple WithFixedCountMock type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    implicit val version = None

    val tto = createTTO[WithFixedCountMock]()
    val value = WithFixedCountMock(
      _1stParam = List(101, 202, 303)
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[WithFixedCountMock](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x00, 0x00, 0x00, 0x65,
          0x00, 0x00, 0x00, 0xca.toByte,
          0x00, 0x00, 0x01, 0x2f
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(12)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x00, 0x00, 0x00, 0x65,
          0x00, 0x00, 0x00, 0xca.toByte,
          0x00, 0x00, 0x01, 0x2f
        ))
      }
    }
  }
}
