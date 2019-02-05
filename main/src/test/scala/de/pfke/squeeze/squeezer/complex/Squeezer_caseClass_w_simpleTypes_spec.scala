package de.pfke.squeeze.squeezer.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.complex.Squeezer_caseClass_w_simpleTypes_spec.caseClass_w_simpleTypes

object Squeezer_caseClass_w_simpleTypes_spec {
  case class caseClass_w_simpleTypes(
    param1: Long,
    param2: Boolean,
    param3: Short
  )
}

class Squeezer_caseClass_w_simpleTypes_spec
  extends BaseSqueezerSpec {
  private val pojo = caseClass_w_simpleTypes(
    param1 = 17433124l,
    param2 = false,
    param3 = 1234.toShort
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x00,
    0x04, 0xd2
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x00,
    0xd2, 0x04
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_simpleTypes](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_simpleTypes](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
