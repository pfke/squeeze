package de.pfke.squeeze.squeezer.annots.withFixedSize

import java.nio.ByteOrder

import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.annots.withFixedSize
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.annots.withFixedSize.Squeezer_caseClass_w_annotWithFixedSize_onInvalid_spec.caseClass_w_annotWithFixedSize_onInvalid

object Squeezer_caseClass_w_annotWithFixedSize_onInvalid_spec {
  case class caseClass_w_annotWithFixedSize_onInvalid(
    param1: Long,
    @withFixedSize(size = 5)
    param2: Byte,
  )
}

class Squeezer_caseClass_w_annotWithFixedSize_onInvalid_spec
  extends BaseSqueezerSpec {
    private val inPojo_invalid = caseClass_w_annotWithFixedSize_onInvalid(
      param1 = 17433124l,
      param2 = 23,
    )

    "[simpleSub_invalid] using w/ big endian byte order (an exception should be thrown)" when {
      implicit val byteOrderToUse: ByteOrder = ByteOrder.BIG_ENDIAN

      "call die scheisse" should {
        "an exception should be thrown" in {
          an[IllegalArgumentException] shouldBe thrownBy(
            Squeezer().toBinary(in = inPojo_invalid)
          )
        }
      }
    }

    "[simpleSub_invalid] using w/ little endian byte order (an exception should be thrown)" when {
      implicit val byteOrderToUse: ByteOrder = ByteOrder.LITTLE_ENDIAN

      "call die scheisse" should {
        "an exception should be thrown" in {
          an[IllegalArgumentException] shouldBe thrownBy(
            Squeezer().toBinary(in = inPojo_invalid)
          )
        }
      }
    }
  }
