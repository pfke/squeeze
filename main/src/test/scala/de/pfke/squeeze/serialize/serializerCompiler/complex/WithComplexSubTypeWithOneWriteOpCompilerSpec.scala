package de.pfke.squeeze.serialize.serializerCompiler.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.{SubWithComplexSubTypeMockWithOneWriteOp, WithComplexSubTypeMockWithOneWriteOp}
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class WithComplexSubTypeWithOneWriteOpCompilerSpec
  extends BaseCompilerSpec {
  "testing with simple WithComplexSubTypeMockWithOneWriteOp type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val tto = createTTO[WithComplexSubTypeMockWithOneWriteOp]()
    val value = WithComplexSubTypeMockWithOneWriteOp(
      _1stParam = SubWithComplexSubTypeMockWithOneWriteOp(
        _1stParam = 0x56
      )
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[WithComplexSubTypeMockWithOneWriteOp](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x56
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(1)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x56
        ))
      }
    }
  }
}
