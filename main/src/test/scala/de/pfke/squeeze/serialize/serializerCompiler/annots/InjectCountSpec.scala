package de.pfke.squeeze.serialize.serializerCompiler.annots

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.annots.InjectCountMock
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class InjectCountSpec
  extends BaseCompilerSpec {
  "testing with simple InjectCountMock type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    implicit val version = None

    val tto = createTTO[InjectCountMock]()
    val value = InjectCountMock(
      _1stParam = 4,
      _2ndParam = List(1, 2, 3, 4),
      _3rdParam = 4
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[InjectCountMock](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x00, 0x04,
          0x01, 0x02, 0x03, 0x04,
          0x00, 0x00, 0x00, 0x04
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(10)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x00, 0x04,
          0x01, 0x02, 0x03, 0x04,
          0x00, 0x00, 0x00, 0x04
        ))
      }
    }
  }
}
