package de.pfke.squeeze.squeezer.annots.injectSize

import akka.util.ByteString
import de.pfke.squeeze.annots.fieldAnnots.{injectSize, withFixedSize}
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.annots.injectSize.Squeezer_caseClass_w_annotInjectSize_onString_wFixedSize_spec.caseClass_w_annotInjectSize_onString_wFixedSize

object Squeezer_caseClass_w_annotInjectSize_onString_wFixedSize_spec {
  case class caseClass_w_annotInjectSize_onString_wFixedSize(
    param1: Long,
    @injectSize(from = "param3")
    param2: Byte,
    @withFixedSize(size = 3)
    param3: String,
    param4: Short,
  )
}

class Squeezer_caseClass_w_annotInjectSize_onString_wFixedSize_spec
  extends BaseSqueezerSpec {
  private val inPojo = caseClass_w_annotInjectSize_onString_wFixedSize(
    param1 = 17433124l,
    param2 = 3,
    param3 = "jlj",
    param4 = 0x7841,
  )
  private val inPojo_tooMuch = caseClass_w_annotInjectSize_onString_wFixedSize(
    param1 = 17433124l,
    param2 = 3,
    param3 = "jljsdfdasdj",
    param4 = 0x7841,
  )
  private val inPojo_tooFew = caseClass_w_annotInjectSize_onString_wFixedSize(
    param1 = 17433124l,
    param2 = 3,
    param3 = "jj",
    param4 = 0x7841,
  )
  private val outPojo = caseClass_w_annotInjectSize_onString_wFixedSize(
    param1 = 17433124l,
    param2 = 3,
    param3 = "jlj",
    param4 = 0x7841,
  )
  private val outPojo_tooFew = caseClass_w_annotInjectSize_onString_wFixedSize(
    param1 = 17433124l,
    param2 = 3,
    param3 = "jj",
    param4 = 0x7841,
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x03,
    0x6a, 0x6c, 0x6a,
    0x78, 0x41
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x03,
    0x6a, 0x6c, 0x6a,
    0x41, 0x78
  )
  private val beBinaryData_tooFew = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x03,
    0x6a, 0x6a, 0x00,
    0x78, 0x41
  )
  private val leBinaryData_tooFew = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x03,
    0x6a, 0x6a, 0x00,
    0x41, 0x78
  )

//  private val inPojo_inLengthIsZero = caseClass_w_annotInjectSize_onString(
//    param1 = 17433124l,
//    param2 = 0,
//    param3 = "jklj",
//    param4 = 0x7841,
//  )

  runBE_n_LE[caseClass_w_annotInjectSize_onString_wFixedSize](
    descr = "annot size matching string length",
    inPojo,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )

  runBE_n_LE[caseClass_w_annotInjectSize_onString_wFixedSize](
    descr = "too few string chars",
    inPojo_tooFew,
    beBinaryData_tooFew,
    leBinaryData_tooFew,
    outPojo = Some(outPojo_tooFew)
  )

  runBE_n_LE[caseClass_w_annotInjectSize_onString_wFixedSize](
    descr = "too much string chars",
    inPojo_tooMuch,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )
}
