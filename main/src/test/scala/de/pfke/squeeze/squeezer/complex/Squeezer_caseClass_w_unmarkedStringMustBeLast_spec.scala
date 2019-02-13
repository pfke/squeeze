package de.pfke.squeeze.squeezer.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.complex.Squeezer_caseClass_w_unmarkedStringMustBeLast_spec.{Squeezer_caseClass_w_unmarkedStirngMustBeLast_invalid, Squeezer_caseClass_w_unmarkedStirngMustBeLast_valid}
import de.pfke.squeeze.zlib.SerializerBuildException

object Squeezer_caseClass_w_unmarkedStringMustBeLast_spec {
  case class Squeezer_caseClass_w_unmarkedStirngMustBeLast_valid(
    param1: Long,
    param2: Boolean,
    param3: Short,
    param4: String,
  )
  case class Squeezer_caseClass_w_unmarkedStirngMustBeLast_invalid(
    param1: Long,
    param2: Boolean,
    param3: String,
    param4: Short,
  )
}

class Squeezer_caseClass_w_unmarkedStringMustBeLast_spec
  extends BaseSqueezerSpec {
  private val inPojo = Squeezer_caseClass_w_unmarkedStirngMustBeLast_valid(
    param1 = 17433124l,
    param2 = true,
    param3 = 0x7841,
    param4 = "kloklkol"
  )
  private val outPojo = Squeezer_caseClass_w_unmarkedStirngMustBeLast_valid(
    param1 = 17433124l,
    param2 = true,
    param3 = 0x7841,
    param4 = "kloklkol"
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x01,
    0x78, 0x41,
    0x6b, 0x6c, 0x6f, 0x6b, 0x6c, 0x6b, 0x6f, 0x6c
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x01,
    0x41, 0x78,
    0x6b, 0x6c, 0x6f, 0x6b, 0x6c, 0x6b, 0x6f, 0x6c
  )

  private val inPojo_invalid = Squeezer_caseClass_w_unmarkedStirngMustBeLast_invalid(
    param1 = 17433124l,
    param2 = true,
    param3 = "klöklköl",
    param4 = 0x7841,
  )

  runBE_n_LE[Squeezer_caseClass_w_unmarkedStirngMustBeLast_valid](
    descr = "string is last",
    inPojo,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )

  "passing a object on which a unannotated string is not the last param" when {
    "using invalid case class" should {
      "an exception should be thrown" in {
        implicit val byteOrderToUse: ByteOrder = ByteOrder.BIG_ENDIAN

        an[SerializerBuildException] shouldBe thrownBy(
          Squeezer().deSerialize[Squeezer_caseClass_w_unmarkedStirngMustBeLast_invalid](beBinaryData) should be (outPojo)
        )
      }
    }
  }
}
