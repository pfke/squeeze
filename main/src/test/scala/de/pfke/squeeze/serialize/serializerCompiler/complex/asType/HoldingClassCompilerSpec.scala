package de.pfke.squeeze.serialize.serializerCompiler.complex.asType

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.zlib.version.PatchLevelVersion
import de.pfke.squeeze.serialize.mocks.asType._
import de.pfke.squeeze.serialize.serializerCompiler.BaseCompilerSpec

class HoldingClassCompilerSpec
  extends BaseCompilerSpec {
  "testing with simple HoldingClass type: sub class is correct annotated" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: None.type = None

    val value = HoldingClass(
      _ifaceType = 54,
      _iface = SubClassB(
        _1stParam = 0x123
      )
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        val tto = createTTO[HoldingClass]()

        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        val tto = createTTO[HoldingClass]()

        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[HoldingClass](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        val tto = createTTO[HoldingClass]()

        readByteString(tto,
          0x00, 0x00, 0x00, 0x36,
          0x01, 0x23
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        val tto = createTTO[HoldingClass]()

        writeByteString(tto, value).length should be(6)
      }

      "[write] should return correct packed ByteString" in {
        val tto = createTTO[HoldingClass]()

        writeByteString(tto, value) should be(ByteString(
          0x00, 0x00, 0x00, 0x36,
          0x01, 0x23
        ))
      }
    }
  }

  "testing with simple HoldingClass type: with version info: 1.5-123" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: Some[PatchLevelVersion] = Some(PatchLevelVersion("1.5-123"))

    val value = HoldingClass(
      _ifaceType = 54,
      _iface = SubClassB_fromVersion_1_5_123(
        _1stParam = 0x123,
        _2ndParam = 0x124
      )
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        val tto = createTTO[HoldingClass]()

        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        val tto = createTTO[HoldingClass]()

        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[HoldingClass](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        val tto = createTTO[HoldingClass]()

        readByteString(tto,
          0x00, 0x00, 0x00, 0x36,
          0x01, 0x23, 0x01, 0x24
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        val tto = createTTO[HoldingClass]()

        writeByteString(tto, value).length should be(8)
      }

      "[write] should return correct packed ByteString" in {
        val tto = createTTO[HoldingClass]()

        writeByteString(tto, value) should be(ByteString(
          0x00, 0x00, 0x00, 0x36,
          0x01, 0x23, 0x01, 0x24
        ))
      }
    }
  }

  "testing with simple HoldingClass type: with version info: 1.5-124" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: Some[PatchLevelVersion] = Some(PatchLevelVersion("1.5-124"))

    val value = HoldingClass(
      _ifaceType = 54,
      _iface = SubClassB_fromVersion_1_5_124(
        _1stParam = 0x123,
        _2ndParam = 0x124,
        _3rdParam = 0x125
      )
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        val tto = createTTO[HoldingClass]()

        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        val tto = createTTO[HoldingClass]()

        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[HoldingClass](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        val tto = createTTO[HoldingClass]()

        readByteString(tto,
          0x00, 0x00, 0x00, 0x36,
          0x01, 0x23, 0x01, 0x24, 0x01, 0x25
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        val tto = createTTO[HoldingClass]()

        writeByteString(tto, value).length should be(10)
      }

      "[write] should return correct packed ByteString" in {
        val tto = createTTO[HoldingClass]()

        writeByteString(tto, value) should be(ByteString(
          0x00, 0x00, 0x00, 0x36,
          0x01, 0x23, 0x01, 0x24, 0x01, 0x25
        ))
      }
    }
  }

  "testing with simple HoldingClass type: with version info: 1.6-123" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: Some[PatchLevelVersion] = Some(PatchLevelVersion("1.6-123"))

    val value = HoldingClass(
      _ifaceType = 54,
      _iface = SubClassB_fromVersion_1_6_123(
        _1stParam = 0x123,
        _2ndParam = 0x124,
        _3rdParam = 0x125,
        _4thParam = 0x126
      )
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        val tto = createTTO[HoldingClass]()

        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        val tto = createTTO[HoldingClass]()

        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[HoldingClass](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        val tto = createTTO[HoldingClass]()

        readByteString(tto,
          0x00, 0x00, 0x00, 0x36,
          0x01, 0x23, 0x01, 0x24, 0x01, 0x25, 0x01, 0x26
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        val tto = createTTO[HoldingClass]()

        writeByteString(tto, value).length should be(12)
      }

      "[write] should return correct packed ByteString" in {
        val tto = createTTO[HoldingClass]()

        writeByteString(tto, value) should be(ByteString(
          0x00, 0x00, 0x00, 0x36,
          0x01, 0x23, 0x01, 0x24, 0x01, 0x25, 0x01, 0x26
        ))
      }
    }
  }

  "testing with simple HoldingClass type: with version info: 2.5-123" when {
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    implicit val version: Some[PatchLevelVersion] = Some(PatchLevelVersion("2.5-123"))

    val value = HoldingClass(
      _ifaceType = 54,
      _iface = SubClassB_fromVersion_2_5_123(
        _1stParam = 0x123,
        _2ndParam = 0x124,
        _3rdParam = 0x125,
        _4thParam = 0x126,
        _5thParam = 0x127
      )
    )

    "read+write bit(s)" should {
      "[read] should throw an exception" in {
        val tto = createTTO[HoldingClass]()

        an[IllegalArgumentException] shouldBe thrownBy(readBitString(tto, 5, 0x03))
      }

      "[write] should throw an exception" in {
        val tto = createTTO[HoldingClass]()

        an[IllegalArgumentException] shouldBe thrownBy(writeBitString[HoldingClass](tto, 5, value))
      }
    }

    "read+write byte(s)" should {
      "[read] should read correct" in {
        val tto = createTTO[HoldingClass]()

        readByteString(tto,
          0x00, 0x00, 0x00, 0x36,
          0x01, 0x23, 0x01, 0x24, 0x01, 0x25, 0x01, 0x26, 0x01, 0x27
        ) should be(value)
      }

      "[write] should return a ByteString with correct length" in {
        val tto = createTTO[HoldingClass]()

        writeByteString(tto, value).length should be(14)
      }

      "[write] should return correct packed ByteString" in {
        val tto = createTTO[HoldingClass]()

        writeByteString(tto, value) should be(ByteString(
          0x00, 0x00, 0x00, 0x36,
          0x01, 0x23, 0x01, 0x24, 0x01, 0x25, 0x01, 0x26, 0x01, 0x27
        ))
      }
    }
  }
}
