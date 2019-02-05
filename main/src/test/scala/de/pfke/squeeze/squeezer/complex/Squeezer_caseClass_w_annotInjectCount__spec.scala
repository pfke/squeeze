package de.pfke.squeeze.squeezer.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.annots.injectCount
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.complex.Squeezer_caseClass_w_annotInjectCount__spec.caseClass_w_annotInjectCount_simpleSub

object Squeezer_caseClass_w_annotInjectCount__spec {
  case class caseClass_w_annotInjectCount_simpleSub(
    param1: Long,
    @injectCount(fromField = "param3")
    param2: Byte,
    param3: List[Byte],
  )
}

class Squeezer_caseClass_w_annotInjectCount__spec
  extends BaseSqueezerSpec {
  private val pojo_simpleSub_in = caseClass_w_annotInjectCount_simpleSub(
    param1 = 17433124l,
    param2 = 0,
    param3 = List(1, 3, 6, 7),
  )
  private val pojo_simpleSub_out = caseClass_w_annotInjectCount_simpleSub(
    param1 = 17433124l,
    param2 = 4,
    param3 = List(1, 3, 6, 7),
  )
  private val beBinaryData_simpleSub = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,

    0x04,

    0x01, 0x03, 0x06, 0x07,
  )
  private val leBinaryData_simpleSub = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,

    0x04,

    0x01, 0x03, 0x06, 0x07,
  )

  "[simpleSub] using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_annotInjectCount_simpleSub](ByteOrder.BIG_ENDIAN, pojo_simpleSub_in, beBinaryData_simpleSub, outPojo = Some(pojo_simpleSub_out))
  }

  "[simpleSub] using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_annotInjectCount_simpleSub](ByteOrder.LITTLE_ENDIAN, pojo_simpleSub_in, leBinaryData_simpleSub, outPojo = Some(pojo_simpleSub_out))
  }
}
