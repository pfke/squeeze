package de.pfke.squeeze.serialize.serializerCompiler.aha

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.aha.{AhaMessage, Poll}
import de.pfke.squeeze.serialize.serializerBuilder.BuildByReflection
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums.AhaMessageType.AhaMessageType
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums.{AhaMessageSeq, AhaMessageType}

class PollBodySpec
  extends BaseCompilerSpec {
  "testing with Poll body [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN
    implicit val version = None

    val tto = createTTO[AhaMessage]()
    val value = AhaMessage(
      mType = AhaMessageType.POLL,
      mSeq = AhaMessageSeq.WHOLE_INFORMATION,
      length = 0,
      handle = 0x1234,
      body = Poll(
        number = 12
      )
    )

    val r1 = BuildByReflection().build[AhaMessage]()
    val r1_1 = r1.code
    val r2 = BuildByReflection().build[AhaMessageType]()
    val r2_1 = r2.code

    println()


    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[AhaMessage](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x08,
          0x03,
          0x00, 0x20,
          0x00, 0x00, 0x12, 0x34,
          0x0c
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(30)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x08,
          0x03,
          0x00, 0x20,
          0x00, 0x00, 0x12, 0x34,
          0x0c
        ))
      }
    }
  }
}
