package de.pfke.squeeze.squeezer.annots.asBitfield

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.annots.classAnnots.alignBitfieldsBy
import de.pfke.squeeze.annots.fieldAnnots.asBitfield
import de.pfke.squeeze.serialize.serializerBuilder.BuildByReflection
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.annots.asBitfield.Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_spec.{Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_16bit, Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_32bit, Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_8bit, Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_invalidFieldAnnot}

object Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_spec {
  case class Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_invalidFieldAnnot(
    @alignBitfieldsBy(bits = 32)
    param1: Long,
    @asBitfield(bits = 8)
    param2: Byte,
    @asBitfield(bits = 8)
    param3: Short,
    @asBitfield(bits = 16)
    param4: Short,
    param5: Short,
  )

  @alignBitfieldsBy(bits = 32)
  case class Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_32bit(
    param1: Long,
    @asBitfield(bits = 8)
    param2: Byte,
    @asBitfield(bits = 8)
    param3: Short,
    @asBitfield(bits = 16)
    param4: Short,
    param5: Short,
  )

  @alignBitfieldsBy(bits = 16)
  case class Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_16bit(
    param1: Long,
    @asBitfield(bits = 8)
    param2: Byte,
    @asBitfield(bits = 8)
    param3: Short,
    @asBitfield(bits = 16)
    param4: Short,
    param5: Short,
  )

  @alignBitfieldsBy(bits = 8)
  case class Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_8bit(
    param1: Long,
    @asBitfield(bits = 8)
    param2: Byte,
    @asBitfield(bits = 8)
    param3: Short,
    @asBitfield(bits = 8)
    param4: Short,
    param5: Short,
  )
}


class Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_spec
  extends BaseSqueezerSpec {
  private val inPojo_invalid = Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_invalidFieldAnnot(
    param1 = 17433124l,
    param2 = 0x3,
    param3 = 0x0c.toShort,
    param4 = 0x1a.toShort,
    param5 = 0x1234,
  )

  private val inPojo_32bit = Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_32bit(
    param1 = 17433124l,
    param2 = 0x3,
    param3 = 0x0c.toShort,
    param4 = 0x1a.toShort,
    param5 = 0x1234,
  )
  private val beBinaryData_32bit = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x03, 0x0c, 0x00, 0x1a,
    0x12, 0x34,
  )
  private val leBinaryData_32bit = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x1a, 0x00, 0x0c, 0x03,
    0x34, 0x12,
  )

  private val inPojo_16bit = Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_16bit(
    param1 = 17433124l,
    param2 = 0x3,
    param3 = 0x0c.toShort,
    param4 = 0x1a.toShort,
    param5 = 0x1234,
  )
  private val beBinaryData_16bit = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x03, 0x0c, 0x00, 0x1a,
    0x12, 0x34,
  )
  private val leBinaryData_16bit = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x0c, 0x03, 0x1a, 0x00,
    0x34, 0x12,
  )

  private val inPojo_8bit = Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_8bit(
    param1 = 17433124l,
    param2 = 0x3,
    param3 = 0x0c.toShort,
    param4 = 0x1a.toShort,
    param5 = 0x1234,
  )
  private val beBinaryData_8bit = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x03, 0x0c, 0x1a,
    0x12, 0x34,
  )
  private val leBinaryData_8bit = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x03, 0x0c, 0x1a,
    0x34, 0x12,
  )

  s"invalid field annot" when {
    "testing w/ big endian byte order" should {
      implicit val byteOrderToUse: ByteOrder = ByteOrder.BIG_ENDIAN
      "should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          Squeezer()
            .toBinary(in = inPojo_invalid)
            .length should be(beBinaryData_32bit.size)
        )
      }
    }

    "testing w/ little endian byte order" should {
      implicit val byteOrderToUse: ByteOrder = ByteOrder.LITTLE_ENDIAN
      "should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          Squeezer()
            .toBinary(in = inPojo_invalid)
            .length should be(leBinaryData_32bit.size)
        )
      }
    }
  }

  runBE_n_LE[Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_32bit](
    descr = "32bit",
    inPojo_32bit,
    beBinaryData_32bit,
    leBinaryData_32bit
  )

  val tto = BuildByReflection().build[Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_8bit]()
  println()

  runBE_n_LE[Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_16bit](
    descr = "16bit",
    inPojo_16bit,
    beBinaryData_16bit,
    leBinaryData_16bit
  )

  runBE_n_LE[Squeezer_caseClass_w_annotAsBitfield_wAlignBitfieldsBy_8bit](
    descr = "8bit",
    inPojo_8bit,
    beBinaryData_8bit,
    leBinaryData_8bit
  )
}
