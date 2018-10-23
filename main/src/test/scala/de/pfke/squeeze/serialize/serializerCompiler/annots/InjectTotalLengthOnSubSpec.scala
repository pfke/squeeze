package de.pfke.squeeze.serialize.serializerCompiler.annots

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.annots.{InjectTotalLengthOnSubMock, SubInjectTotalLengthOnSubMock}
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class InjectTotalLengthOnSubSpec
  extends BaseCompilerSpec {
  "testing with simple InjectTotalLengthOnSubMock type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    implicit val version = None

    val tto = createTTO[InjectTotalLengthOnSubMock]()
    val value = InjectTotalLengthOnSubMock(
      _1stParam = SubInjectTotalLengthOnSubMock(
        _1stParam = 13,
        _2ndParam = 29,
        _3rdParam = 0x45667
      ),
      _2ndParam = 12
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[InjectTotalLengthOnSubMock](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x00, 0x0d,
          0x00, 0x08,
          0x00, 0x04, 0x56, 0x67,
          0x00, 0x0a
        ) should be(
          InjectTotalLengthOnSubMock(
            _1stParam = SubInjectTotalLengthOnSubMock(
              _1stParam = 13,
              _2ndParam = 8,
              _3rdParam = 0x45667
            ),
            _2ndParam = 10
          ))
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(10)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x00, 0x0d,
          0x00, 0x08,
          0x00, 0x04, 0x56, 0x67,
          0x00, 0x0a
        ))
      }
    }
  }
}
