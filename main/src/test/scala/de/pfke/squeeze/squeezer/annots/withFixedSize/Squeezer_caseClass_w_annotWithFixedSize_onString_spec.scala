package de.pfke.squeeze.squeezer.annots.withFixedSize

import akka.util.ByteString
import de.pfke.squeeze.annots.fieldAnnots.withFixedSize
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.annots.withFixedSize.Squeezer_caseClass_w_annotWithFixedSize_onString_spec.caseClass_w_annotWithFixedLength_onString

object Squeezer_caseClass_w_annotWithFixedSize_onString_spec {
  case class caseClass_w_annotWithFixedLength_onString(
    param1: Long,
    @withFixedSize(size = 5)
    param2: String,
    param3: Byte,
  )
}

class Squeezer_caseClass_w_annotWithFixedSize_onString_spec
  extends BaseSqueezerSpec {
  private val inPojo = caseClass_w_annotWithFixedLength_onString(
    param1 = 17433124l,
    param2 = "jklas",
    param3 = 123,
  )
  private val inPojo_tooMuch = caseClass_w_annotWithFixedLength_onString(
    param1 = 17433124l,
    param2 = "jklassdfsdfggegr",
    param3 = 123,
  )
  private val inPojo_tooFew = caseClass_w_annotWithFixedLength_onString(
    param1 = 17433124l,
    param2 = "jkl",
    param3 = 123,
  )
  private val outPojo = caseClass_w_annotWithFixedLength_onString(
    param1 = 17433124l,
    param2 = "jklas",
    param3 = 123,
  )
  private val outPojo_tooFew = caseClass_w_annotWithFixedLength_onString(
    param1 = 17433124l,
    param2 = "jkl",
    param3 = 123,
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,

    0x6a, 0x6b, 0x6c, 0x61, 0x73,

    0x7b
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,

    0x6a, 0x6b, 0x6c, 0x61, 0x73,

    0x7b
  )
  private val beBinaryData_tooFew = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,

    0x6a, 0x6b, 0x6c, 0x00, 0x00,

    0x7b
  )
  private val leBinaryData_tooFew = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,

    0x6a, 0x6b, 0x6c, 0x00, 0x00,

    0x7b
  )

  runBE_n_LE[caseClass_w_annotWithFixedLength_onString](
    descr = "annot size matching string length",
    inPojo,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )

  runBE_n_LE[caseClass_w_annotWithFixedLength_onString](
    descr = "too few string chars",
    inPojo_tooFew,
    beBinaryData_tooFew,
    leBinaryData_tooFew,
    outPojo = Some(outPojo_tooFew)
  )

  runBE_n_LE[caseClass_w_annotWithFixedLength_onString](
    descr = "too much string chars",
    inPojo_tooMuch,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )
}
