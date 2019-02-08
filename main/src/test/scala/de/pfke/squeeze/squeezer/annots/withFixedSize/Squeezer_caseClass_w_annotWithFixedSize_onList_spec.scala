package de.pfke.squeeze.squeezer.annots.withFixedSize

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.annots.withFixedSize
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.annots.withFixedSize.Squeezer_caseClass_w_annotWithFixedSize_onList_spec.caseClass_w_annotWithFixedSize_onList

object Squeezer_caseClass_w_annotWithFixedSize_onList_spec {
  case class caseClass_w_annotWithFixedSize_onList(
    param1: Long,
    @withFixedSize(size = 4)
    param2: List[Byte],
    param3: Short,
  )
}

class Squeezer_caseClass_w_annotWithFixedSize_onList_spec
  extends BaseSqueezerSpec {
  private val inPojo = caseClass_w_annotWithFixedSize_onList(
    param1 = 17433124l,
    param2 = List(1, 3, 6, 7, 8, 9, 4),
    param3 = 0x5463,
  )
  private val inPojo_tooMuch = caseClass_w_annotWithFixedSize_onList(
    param1 = 17433124l,
    param2 = List(1, 3, 6, 7, 8, 9, 4),
    param3 = 0x5463,
  )
  private val inPojo_tooFew = caseClass_w_annotWithFixedSize_onList(
    param1 = 17433124l,
    param2 = List(1, 3),
    param3 = 0x5463,
  )
  private val outPojo = caseClass_w_annotWithFixedSize_onList(
    param1 = 17433124l,
    param2 = List(1, 3, 6, 7),
    param3 = 0x5463,
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,

    0x01, 0x03, 0x06, 0x07,

    0x54, 0x63
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,

    0x01, 0x03, 0x06, 0x07,

    0x63, 0x54
  )

  runBE_n_LE[caseClass_w_annotWithFixedSize_onList](
    descr = "annot size matching list elements",
    inPojo,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )

  runBE_n_LE[caseClass_w_annotWithFixedSize_onList](
    descr = "too much list elements",
    inPojo_tooMuch,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )

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
