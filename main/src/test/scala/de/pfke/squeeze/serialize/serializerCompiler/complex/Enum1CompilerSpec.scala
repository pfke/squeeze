package de.pfke.squeeze.serialize.serializerCompiler.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.Enum1Mock
import de.pfke.squeeze.serialize.mocks.Enum1Mock.Enum1Mock
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class Enum1CompilerSpec
  extends BaseCompilerSpec {
  "testing with simple Enum1Mock type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val tto = createTTO[Enum1Mock]()

    "read+write bit(s)" should {
      "[read] should read correct" in {
        readBitString(tto, 5, 0x00000004) shouldBe Enum1Mock._4
      }

      "[write] should write correct" in {
        writeBitString[Enum1Mock](tto, 5, Enum1Mock._1) should be(
          ByteString(
            0x00, 0x00, 0x00, 0x01
          ))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x00, 0x00, 0x00, 0x03
        ) should be(Enum1Mock._3)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, Enum1Mock._3).length should be(4)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, Enum1Mock._2) should be(ByteString(
          0x00, 0x00, 0x00, 0x02
        ))
      }
    }
  }
}