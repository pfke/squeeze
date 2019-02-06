package de.pfke.squeeze.squeezer.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.annots.withFixedWidth
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.complex.Squeezer_caseClass_w_annotWithFixedWidth_spec.{caseClass_w_annotWithFixedWidth_invalid, caseClass_w_annotWithFixedWidth_simpleSub}
import de.pfke.squeeze.zlib.SerializerBuildException

object Squeezer_caseClass_w_annotWithFixedWidth_spec {
  case class caseClass_w_annotWithFixedWidth_simpleSub(
    param1: Long,
    @withFixedWidth(size = 2)
    param2: Long,
  )

  case class caseClass_w_annotWithFixedWidth_invalid(
    param1: Long,
    @withFixedWidth(size = 4)
    param2: String,
  )
}

class Squeezer_caseClass_w_annotWithFixedWidth_spec
  extends BaseSqueezerSpec {
  private val inPojo = caseClass_w_annotWithFixedWidth_simpleSub(
    param1 = 17433124l,
    param2 = 0x1234l,
  )
  private val inPojo_tooMuch = caseClass_w_annotWithFixedWidth_simpleSub(
    param1 = 17433124l,
    param2 = 0x123456l,
  )
  private val inPojo_tooFew = caseClass_w_annotWithFixedWidth_simpleSub(
    param1 = 17433124l,
    param2 = 0x12l,
  )
  private val inPojo_invalid = caseClass_w_annotWithFixedWidth_invalid(
    param1 = 17433124l,
    param2 = "sss",
  )
  private val outPojo = caseClass_w_annotWithFixedWidth_simpleSub(
    param1 = 17433124l,
    param2 = 0x1234l,
  )
  private val outPojo_tooFew = caseClass_w_annotWithFixedWidth_simpleSub(
    param1 = 17433124l,
    param2 = 0x1200l,
  )
  private val beBinaryData_simpleSub = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,

    0x12, 0x34
  )
  private val leBinaryData_simpleSub = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,

    0x34, 0x12
  )
  private val beBinaryData_simpleSub_tooFew = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,

    0x12, 0x34, 0x00, 0x00
  )
  private val leBinaryData_simpleSub_tooFew = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,

    0x00, 0x00, 0x34, 0x12
  )

  "[simpleSub] using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedWidth_simpleSub](ByteOrder.BIG_ENDIAN, inPojo, beBinaryData_simpleSub, outPojo = Some(outPojo))
  }

  "[simpleSub] using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedWidth_simpleSub](ByteOrder.LITTLE_ENDIAN, inPojo, leBinaryData_simpleSub, outPojo = Some(outPojo))
  }

  "[simpleSub_tooFew] using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedWidth_simpleSub](ByteOrder.BIG_ENDIAN, inPojo_tooFew, beBinaryData_simpleSub_tooFew, outPojo = Some(outPojo_tooFew))
  }

  "[simpleSub_tooFew] using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedWidth_simpleSub](ByteOrder.LITTLE_ENDIAN, inPojo_tooFew, leBinaryData_simpleSub_tooFew, outPojo = Some(outPojo_tooFew))
  }

  "[simpleSub_tooMuch] using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedWidth_simpleSub](ByteOrder.BIG_ENDIAN, inPojo_tooMuch, beBinaryData_simpleSub, outPojo = Some(outPojo))
  }

  "[simpleSub_tooMuch] using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedWidth_simpleSub](ByteOrder.LITTLE_ENDIAN, inPojo_tooMuch, leBinaryData_simpleSub, outPojo = Some(outPojo))
  }

  "[simpleSub_invalid] using w/ big endian byte order (an exception should be thrown)" when {
    implicit val byteOrderToUse: ByteOrder = ByteOrder.BIG_ENDIAN

    "call die scheisse" should {
      "an exception should be thrown" in {
        an[SerializerBuildException] shouldBe thrownBy(
          Squeezer().toBinary(in = inPojo_invalid)
        )
      }
    }
  }

  "[simpleSub_invalid] using w/ little endian byte order (an exception should be thrown)" when {
    implicit val byteOrderToUse: ByteOrder = ByteOrder.LITTLE_ENDIAN

    "call die scheisse" should {
      "an exception should be thrown" in {
        an[SerializerBuildException] shouldBe thrownBy(
          Squeezer().toBinary(in = inPojo_invalid)
        )
      }
    }
  }
}
