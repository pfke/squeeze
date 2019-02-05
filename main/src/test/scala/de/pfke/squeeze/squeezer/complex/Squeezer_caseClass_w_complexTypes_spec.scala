package de.pfke.squeeze.squeezer.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.complex.Squeezer_caseClass_w_complexTypes_spec.{caseClass_w_complexTypes, caseClass_w_complexTypes_in1, caseClass_w_complexTypes_in2}

object Squeezer_caseClass_w_complexTypes_spec {
  case class caseClass_w_complexTypes_in1(
    param1: Long,
    param2: Boolean,
    param3: Short,
  )
  case class caseClass_w_complexTypes_in2(
    param1: Short,
    param2: Boolean,
  )

  case class caseClass_w_complexTypes(
    param1: Long,
    param2: caseClass_w_complexTypes_in2,
    param3: Short,
    param4: caseClass_w_complexTypes_in1,
  )
}

class Squeezer_caseClass_w_complexTypes_spec
  extends BaseSqueezerSpec {
  private val pojo = caseClass_w_complexTypes(
    param1 = 17433124l,
    param2 = caseClass_w_complexTypes_in2(
      param1 = 2344.toShort,
      param2 = false
    ),
    param3 = 1234.toShort,
    param4 = caseClass_w_complexTypes_in1(
      param1 = 1743312324l,
      param2 = true,
      param3 = 23442.toShort,
    ),
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,

    0x09, 0x28,
    0x00,

    0x04, 0xd2,

    0x00, 0x00, 0x00, 0x00, 0x67, 0xe8, 0xd5, 0xc4,
    0x01,
    0x5b, 0x92
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,

    0x28, 0x09,
    0x00,

    0xd2, 0x04,

    0xc4, 0xd5, 0xe8, 0x67, 0x00, 0x00, 0x00, 0x00,
    0x01,
    0x92, 0x5b
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_complexTypes](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_complexTypes](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
