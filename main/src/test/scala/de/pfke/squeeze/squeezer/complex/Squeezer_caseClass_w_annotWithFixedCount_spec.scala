package de.pfke.squeeze.squeezer.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.annots.withFixedCount
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.complex.Squeezer_caseClass_w_annotWithFixedCount_spec.caseClass_w_annotWithFixedCount_simpleSub

object Squeezer_caseClass_w_annotWithFixedCount_spec {
  case class caseClass_w_annotWithFixedCount_simpleSub(
    param1: Long,
    @withFixedCount(count= 4)
    param2: List[Byte],
  )
}

class Squeezer_caseClass_w_annotWithFixedCount_spec
  extends BaseSqueezerSpec {
  private val inPojo = caseClass_w_annotWithFixedCount_simpleSub(
    param1 = 17433124l,
    param2 = List(1, 3, 6, 7, 8, 9, 4),
  )
  private val inPojo_tooMuch = caseClass_w_annotWithFixedCount_simpleSub(
    param1 = 17433124l,
    param2 = List(1, 3, 6, 7, 8, 9, 4),
  )
  private val inPojo_tooFew = caseClass_w_annotWithFixedCount_simpleSub(
    param1 = 17433124l,
    param2 = List(1, 3),
  )
  private val outPojo = caseClass_w_annotWithFixedCount_simpleSub(
    param1 = 17433124l,
    param2 = List(1, 3, 6, 7),
  )
  private val beBinaryData_simpleSub = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,

    0x01, 0x03, 0x06, 0x07,
  )
  private val leBinaryData_simpleSub = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,

    0x01, 0x03, 0x06, 0x07,
  )

  "[simpleSub] using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedCount_simpleSub](ByteOrder.BIG_ENDIAN, inPojo, beBinaryData_simpleSub, outPojo = Some(outPojo))
  }

  "[simpleSub] using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedCount_simpleSub](ByteOrder.LITTLE_ENDIAN, inPojo, leBinaryData_simpleSub, outPojo = Some(outPojo))
  }

  "[simpleSub_tooMuch] using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedCount_simpleSub](ByteOrder.BIG_ENDIAN, inPojo_tooMuch, beBinaryData_simpleSub, outPojo = Some(outPojo))
  }

  "[simpleSub_tooMuch] using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedCount_simpleSub](ByteOrder.LITTLE_ENDIAN, inPojo_tooMuch, leBinaryData_simpleSub, outPojo = Some(outPojo))
  }

  "[simpleSub_toFew] using w/ big endian byte order (an exception should be thrown)" when {
    implicit val byteOrderToUse: ByteOrder = ByteOrder.BIG_ENDIAN

    "call die scheisse" should {
      "an exception should be thrown" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          Squeezer().toBinary(in = inPojo_tooFew)
        )
      }
    }
  }

  "[simpleSub_toFew] using w/ little endian byte order (an exception should be thrown)" when {
    implicit val byteOrderToUse: ByteOrder = ByteOrder.LITTLE_ENDIAN

    "call die scheisse" should {
      "an exception should be thrown" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          Squeezer().toBinary(in = inPojo_tooFew)
        )
      }
    }
  }
}
