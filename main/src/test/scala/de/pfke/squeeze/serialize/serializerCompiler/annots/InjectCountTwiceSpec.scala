package de.pfke.squeeze.serialize.serializerCompiler.annots

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.annots.InjectCountTwiceMock
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class InjectCountTwiceSpec
  extends BaseCompilerSpec {
  "testing with simple InjectCountTwiceMock type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val tto = createTTO[InjectCountTwiceMock]()
    val value = InjectCountTwiceMock(
      _1stParam = 7,
      _2ndParam = 3,
      _3rdParam = List(1, 2, 3),
      _4thParam = List(1, 2, 3, 4, 5, 6, 7)
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[InjectCountTwiceMock](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x00, 0x07,
          0x00, 0x03,
          0x01, 0x02, 0x03,
          0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(14)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x00, 0x07,
          0x00, 0x03,
          0x01, 0x02, 0x03,
          0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07
        ))
      }
    }
  }
}
