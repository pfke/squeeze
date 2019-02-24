package de.pfke.squeeze.squeezer.annots.injectSize

import akka.util.ByteString
import de.pfke.squeeze.annots.fieldAnnots.injectSize
import de.pfke.squeeze.serialize.serializerBuilder.BuildByReflection
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.annots.injectSize.Squeezer_caseClass_w_annotInjectSize_onList_spec.caseClass_w_annotInjectSize_onList

object Squeezer_caseClass_w_annotInjectSize_onList_spec {
  case class caseClass_w_annotInjectSize_onList(
    param1: Long,
    @injectSize(from = "param3")
    param2: Byte,
    param3: List[Byte],
    param4: Short,
  )
}

class Squeezer_caseClass_w_annotInjectSize_onList_spec
  extends BaseSqueezerSpec {
  private val inPojo = caseClass_w_annotInjectSize_onList(
    param1 = 17433124l,
    param2 = 4,
    param3 = List(1, 3, 6, 7),
    param4 = 0x5422,
  )
  private val outPojo = caseClass_w_annotInjectSize_onList(
    param1 = 17433124l,
    param2 = 4,
    param3 = List(1, 3, 6, 7),
    param4 = 0x5422,
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x04,
    0x01, 0x03, 0x06, 0x07,
    0x54, 0x22
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x04,
    0x01, 0x03, 0x06, 0x07,
    0x22, 0x54
  )

  private val inPojo_inLengthIsZero = caseClass_w_annotInjectSize_onList(
    param1 = 17433124l,
    param2 = 4,
    param3 = List(1, 3, 6, 7),
    param4 = 0x5422,
  )

  runBE_n_LE[caseClass_w_annotInjectSize_onList](
    descr = "all params filled",
    inPojo,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )

  runBE_n_LE[caseClass_w_annotInjectSize_onList](
    descr = "in length is 0",
    inPojo_inLengthIsZero,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )
}
