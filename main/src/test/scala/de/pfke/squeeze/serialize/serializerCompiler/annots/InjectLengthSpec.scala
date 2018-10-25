package de.pfke.squeeze.serialize.serializerCompiler.annots

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.serialize.mocks.annots.InjectLengthMock
import de.pfke.squeeze.serialize.serializerBuilder.BuildByReflection
import de.pfke.squeeze.serialize.serializerCompiler.{BaseCompilerSpec, SerializerCompiler}

class InjectLengthSpec
  extends BaseCompilerSpec {
  "testing with simple InjectLengthMock type [ByteOrder.BIG_ENDIAN]" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val tto = createTTO[InjectLengthMock]()
    val value = InjectLengthMock(
      _1stParam = 5,
      _2ndParam = "heiko"
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[InjectLengthMock](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        readByteString(tto,
          0x00, 0x05,
          0x68, 0x65, 0x69, 0x6b, 0x6f
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        writeByteString(tto, value).length should be(7)
      }

      "[write] should return correct packed ByteString" in {
        writeByteString(tto, value) should be(ByteString(
          0x00, 0x05,
          0x68, 0x65, 0x69, 0x6b, 0x6f
        ))
      }
    }
  }

  private def create() = SerializerCompiler.compile[InjectLengthMock](BuildByReflection().build())

//  "testing with simple InjectLengthMock type" when {
//    val tto = create()
//    val value = InjectLengthMock(
  //      _1stParam = 5,
  //      _2ndParam = "heiko"
//    )
//
//    "testing with ByteOrder.BIG_ENDIAN" should {
//      implicit val byteOrder = ByteOrder.BIG_ENDIAN
//      implicit val version = None
//
//      "[read] should read correct" in {
//        tto.serializerObject.readBytes(Squeezer(), ByteString(
//          0x00, 0x05,
//          0x68, 0x65, 0x69, 0x6b, 0x6f
//        )) should be (value)
//      }
//
//      "[write] should return a ByteString with correct length" in {
//        tto.serializerObject.writeBytes(Squeezer(), value).length should be (7)
//      }
//
//      "[write] should return correct packed ByteString" in {
//        tto.serializerObject.writeBytes(Squeezer(), value) should be (ByteString(
//          0x00, 0x05,
//          0x68, 0x65, 0x69, 0x6b, 0x6f
//        ))
//      }
//    }
//
//    "testing with ByteOrder.LITTLE_ENDIAN" should {
//      implicit val byteOrder = ByteOrder.LITTLE_ENDIAN
//      implicit val version = None
//
//      "[read] should read correct" in {
//        tto.serializerObject.readBytes(Squeezer(), ByteString(
//          0x05, 0x00,
//          0x68, 0x65, 0x69, 0x6b, 0x6f
//        )) should be (value)
//      }
//
//      "[write] should return a ByteString with correct length" in {
//        tto.serializerObject.writeBytes(Squeezer(), value).length should be (7)
//      }
//
//      "[write] should return correct packed ByteString" in {
//        tto.serializerObject.writeBytes(Squeezer(), value) should be (ByteString(
//          0x05, 0x00,
//          0x68, 0x65, 0x69, 0x6b, 0x6f
//        ))
//      }
//    }
//  }
}
