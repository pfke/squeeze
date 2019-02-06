package de.pfke.squeeze.squeezer.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.complex.Squeezer_caseClass_w_list_spec.caseClass_w_list

object Squeezer_caseClass_w_list_spec {
  case class caseClass_w_list(
    param1: Long,
    param2: List[Byte],
  )
}

class Squeezer_caseClass_w_list_spec
  extends BaseSqueezerSpec {
  private val pojo = caseClass_w_list(
    param1 = 17433124l,
    param2 = List(1, 23, 44),
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x01, 23, 44
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x01, 23, 44
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_list](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_list](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
