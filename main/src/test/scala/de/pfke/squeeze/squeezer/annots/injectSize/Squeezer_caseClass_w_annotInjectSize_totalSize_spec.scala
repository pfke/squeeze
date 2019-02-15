package de.pfke.squeeze.squeezer.annots.injectSize

import akka.util.ByteString
import de.pfke.squeeze.annots.fieldAnnots.injectSize
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.annots.injectSize.Squeezer_caseClass_w_annotInjectSize_totalSize_spec.caseClass_w_annotInjectSize_totalSize

object Squeezer_caseClass_w_annotInjectSize_totalSize_spec {
  case class caseClass_w_annotInjectSize_totalSize(
    param1: Long,
    @injectSize(from = ".")
    param2: Byte,
    param3: Short,
  )
}

class Squeezer_caseClass_w_annotInjectSize_totalSize_spec
  extends BaseSqueezerSpec {
  private val inPojo = caseClass_w_annotInjectSize_totalSize(
    param1 = 17433124l,
    param2 = 11,
    param3 = 0x7841,
  )
  private val outPojo = caseClass_w_annotInjectSize_totalSize(
    param1 = 17433124l,
    param2 = 11,
    param3 = 0x7841,
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x0b,
    0x78, 0x41
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x0b,
    0x41, 0x78
  )

  private val inPojo_inLengthIsZero = caseClass_w_annotInjectSize_totalSize(
    param1 = 17433124l,
    param2 = 0,
    param3 = 0x7841,
  )

  runBE_n_LE[caseClass_w_annotInjectSize_totalSize](
    descr = "all params filled",
    inPojo,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )

  runBE_n_LE[caseClass_w_annotInjectSize_totalSize](
    descr = "in length is 0",
    inPojo_inLengthIsZero,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )
}
