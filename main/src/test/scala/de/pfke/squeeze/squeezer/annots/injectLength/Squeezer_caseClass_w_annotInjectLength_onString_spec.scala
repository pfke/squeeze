package de.pfke.squeeze.squeezer.annots.injectLength

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.annots.injectLength
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.annots.injectLength.Squeezer_caseClass_w_annotInjectLength_onString_spec.caseClass_w_annotInjectLength_onString

object Squeezer_caseClass_w_annotInjectLength_onString_spec {
  case class caseClass_w_annotInjectLength_onString(
    param1: Long,
    @injectLength(fromField = "param3")
    param2: Byte,
    param3: String,
    param4: Short,
  )
}

class Squeezer_caseClass_w_annotInjectLength_onString_spec
  extends BaseSqueezerSpec {
  private val inPojo = caseClass_w_annotInjectLength_onString(
    param1 = 17433124l,
    param2 = 4,
    param3 = "jklj",
    param4 = 0x7841,
  )
  private val outPojo = caseClass_w_annotInjectLength_onString(
    param1 = 17433124l,
    param2 = 4,
    param3 = "jklj",
    param4 = 0x7841,
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x04,
    0x6a, 0x6b, 0x6c, 0x6a,
    0x78, 0x41
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x04,
    0x6a, 0x6b, 0x6c, 0x6a,
    0x41, 0x78
  )

  private val inPojo_inLengthIsZero = caseClass_w_annotInjectLength_onString(
    param1 = 17433124l,
    param2 = 0,
    param3 = "jklj",
    param4 = 0x7841,
  )

  runBE_n_LE[caseClass_w_annotInjectLength_onString](
    descr = "all params filled",
    inPojo,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )

  runBE_n_LE[caseClass_w_annotInjectLength_onString](
    descr = "in length is 0",
    inPojo_inLengthIsZero,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )
}
