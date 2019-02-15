package de.pfke.squeeze.squeezer.annots.asBitfield

import akka.util.ByteString
import de.pfke.squeeze.annots.asBitfield
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.annots.asBitfield.Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_spec.{Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_aligned, Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_unaligned_hole, Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_unaligned_noHole}

object Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_spec {
  case class Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_aligned(
    param1: Long,
    @asBitfield(bits = 8)
    param2: Byte,
    @asBitfield(bits = 8)
    param3: Short,
    @asBitfield(bits = 16)
    param4: Short,
    param5: Short,
  )
  case class Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_unaligned_noHole(
    param1: Long,
    @asBitfield(bits = 3)
    param2: Byte,
    @asBitfield(bits = 9)
    param3: Short,
    @asBitfield(bits = 20)
    param4: Int,
    param5: Short,
  )
  case class Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_unaligned_hole(
    param1: Long,
    @asBitfield(bits = 3)
    param2: Byte,
    @asBitfield(bits = 9)
    param3: Short,
    @asBitfield(bits = 10)
    param4: Int,
    param5: Short,
  )
}

class Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_spec
  extends BaseSqueezerSpec {
  private val inPojo = Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_aligned(
    param1 = 17433124l,
    param2 = 0x3,
    param3 = 0x0c.toShort,
    param4 = 0x1a.toShort,
    param5 = 0x1234,
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x03, 0x0c, 0x00, 0x1a,
    0x12, 0x34,
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x1a, 0x00, 0x0c, 0x03,
    0x34, 0x12,
  )

  private val inPojo_unaligend_noHoles = Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_unaligned_noHole(
    param1 = 17433124l,
    param2 = 0x1,
    param3 = 0x11c.toShort,
    param4 = 0x2341a,
    param5 = 0x1234,
  )

  private val inPojo_unaligend_noHoles_valueTooMuchBits = Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_unaligned_noHole(
    param1 = 17433124l,
    param2 = 0x1,
    param3 = 0x11c.toShort,
    param4 = 0x882341a,
    param5 = 0x1234,
  )
  private val beBinaryData_unaligend_noHoles = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x31, 0xc2, 0x34, 0x1a,
    0x12, 0x34,
  )
  private val leBinaryData_unaligend_noHoles = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x1a, 0x34, 0xc2, 0x31,
    0x34, 0x12,
  )

  private val inPojo_unaligend_holes = Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_unaligned_hole(
    param1 = 17433124l,
    param2 = 0x1,
    param3 = 0x11c.toShort,
    param4 = 0x213,
    param5 = 0x1234,
  )
  private val beBinaryData_unaligend_holes = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x00, 0x0c, 0x72, 0x13,
    0x12, 0x34,
  )
  private val leBinaryData_unaligend_holes = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x13, 0x72, 0x0c, 0x00,
    0x34, 0x12,
  )

  runBE_n_LE[Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_aligned](
    descr = "aligned zeuch",
    inPojo,
    beBinaryData,
    leBinaryData
  )

  runBE_n_LE[Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_unaligned_noHole](
    descr = "unaligned no hole",
    inPojo_unaligend_noHoles,
    beBinaryData_unaligend_noHoles,
    leBinaryData_unaligend_noHoles
  )

  runBE_n_LE[Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_unaligned_noHole](
    descr = "unaligned no hole (one value is to much for its bits)",
    inPojo_unaligend_noHoles_valueTooMuchBits,
    beBinaryData_unaligend_noHoles,
    leBinaryData_unaligend_noHoles,
    outPojo = Some(inPojo_unaligend_noHoles)
  )

  runBE_n_LE[Squeezer_caseClass_w_annotAsBitfield_woAlignBitfieldsBy_unaligned_hole](
    descr = "unaligned w/ hole",
    inPojo_unaligend_holes,
    beBinaryData_unaligend_holes,
    leBinaryData_unaligend_holes
  )
}
