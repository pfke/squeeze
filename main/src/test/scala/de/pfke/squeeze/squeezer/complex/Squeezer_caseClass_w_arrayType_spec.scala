package de.pfke.squeeze.squeezer.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.complex.Squeezer_caseClass_w_arrayType_spec.caseClass_w_arrayTypes

object Squeezer_caseClass_w_arrayType_spec {
  case class caseClass_w_arrayTypes(
    param1: Long,
    param2: Array[Byte],
  )
}

class Squeezer_caseClass_w_arrayType_spec
  extends BaseSqueezerSpec {
  private val pojo = caseClass_w_arrayTypes(
    param1 = 17433124l,
    param2 = Array(1, 23, 44),
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
    runSqueezerTests[caseClass_w_arrayTypes](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_arrayTypes](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }

  "" when {
    "" should {
      "" in {
        implicit val byteOrderToUse: ByteOrder = ByteOrder.BIG_ENDIAN

        val squeezer = Squeezer()

        val r1 = squeezer
          .deSerialize[caseClass_w_arrayTypes](beBinaryData)
        val r2 = squeezer
          .deSerialize[caseClass_w_arrayTypes](beBinaryData).param2.toString
        val r3 = r1.param2.toList.toString()
        squeezer
          .deSerialize[caseClass_w_arrayTypes](beBinaryData) should be (pojo)


      }
    }
  }
}
