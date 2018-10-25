package de.pfke.squeeze.serialize.serializerCompiler.complex.asBitfieldsSpecs

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class asBitfield_mock_aligned_single_8bitSpec
  extends BaseCompilerSpec {
  "testing with simple asBitfield_mock_aligned_single_8bit type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val tto = createTTO[asBitfield_mock_aligned_single_8bit]()
    val value = asBitfield_mock_aligned_single_8bit(
      field01 = 0x81
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[asBitfield_mock_aligned_single_8bit](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x81.toByte
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(1)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x81.toByte
        ))
      }
    }
  }

//  "testing with simple asBitfield_mock_aligned_single8bit type" when {
//    val tto = create()
//    val value = asBitfield_mock_aligned_single_8bit(
//      field01 = 0x81
//    )
//
//    "testing the bit reader/writer" should {
//      implicit val byteOrder = ByteOrder.BIG_ENDIAN
//      implicit val version = None
//
//      "method 'readBits(...)' should not be implemented" in {
//        an[NotImplementedError] shouldBe thrownBy {
//          create().serializerObject.readBits(null, BitIterator.empty, SerializerHints.none)
//        }
//      }
//
//      "method 'writeBits(...)' should not be implemented" in {
//        an[NotImplementedError] shouldBe thrownBy {
//          create().serializerObject.writeBits(null, value, BitStringBuilder.newBuilder(), SerializerHints.none)
//        }
//      }
//    }
//
//    "testing with ByteOrder.BIG_ENDIAN" should {
//      implicit val byteOrder = ByteOrder.BIG_ENDIAN
//      implicit val version = None
//
//      "[read] should read correct" in {
//        tto.serializerObject.readBytes(Squeezer(), ByteString(
//          0x81
//        )) should be (value)
//      }
//
//      "[write] should return a ByteString with correct length" in {
//        tto.serializerObject.writeBytes(Squeezer(), value).length should be (1)
//      }
//
//      "[write] should return correct packed ByteString" in {
//        tto.serializerObject.writeBytes(Squeezer(), value) should be (ByteString(
//          0x81
//        ))
//      }
//    }
//
//    "testing with ByteOrder.LITTLE_ENDIAN" should {
//      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
//
//    }
//  }
}
