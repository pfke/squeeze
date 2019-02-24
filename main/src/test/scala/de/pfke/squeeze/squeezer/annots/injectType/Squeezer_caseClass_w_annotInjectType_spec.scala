package de.pfke.squeeze.squeezer.annots.injectType

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.annots.classAnnots.typeForIface
import de.pfke.squeeze.annots.fieldAnnots.injectType
import de.pfke.squeeze.serialize.serializerBuilder.BuildByReflection
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.annots.injectType.Squeezer_caseClass_w_annotInjectType_spec.{caseClass_w_annotInjectType_cc2, caseClass_w_annotInjectType_identAsInt, caseClass_w_annotInjectType_iface, caseClass_w_annotInjectType_noAnIface}

object Squeezer_caseClass_w_annotInjectType_spec {
  trait caseClass_w_annotInjectType_iface
  @typeForIface(ident = 1)
  case class caseClass_w_annotInjectType_cc1(param1: Byte  ) extends caseClass_w_annotInjectType_iface
  @typeForIface(ident = 2)
  case class caseClass_w_annotInjectType_cc2(param1: String) extends caseClass_w_annotInjectType_iface

  case class caseClass_w_annotInjectType_identAsInt(
    param1: Long,

    @injectType(fromField = "param3")
    param2: Byte,

    param3: caseClass_w_annotInjectType_iface
  )

  case class caseClass_w_annotInjectType_noAnIface(
    param1: Long,
    @injectType(fromField = "param3")
    param2: Byte,
    param3: String
  )
}

class Squeezer_caseClass_w_annotInjectType_spec
  extends BaseSqueezerSpec {
  private val inPojo_0injectType = caseClass_w_annotInjectType_identAsInt(
    param1 = 17433124l,
    param2 = 0,
    param3 = caseClass_w_annotInjectType_cc2(
      param1 = "123"
    )
  )
  private val inPojo_correctInjectType = caseClass_w_annotInjectType_identAsInt(
    param1 = 17433124l,
    param2 = 2,
    param3 = caseClass_w_annotInjectType_cc2(
      param1 = "123"
    )
  )
  private val outPojo = caseClass_w_annotInjectType_identAsInt(
    param1 = 17433124l,
    param2 = 2,
    param3 = caseClass_w_annotInjectType_cc2(
      param1 = "123"
    )
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,
    0x02,
    0x31, 0x32, 0x33
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,
    0x02,
    0x31, 0x32, 0x33
  )

  val r1 = BuildByReflection().build[caseClass_w_annotInjectType_identAsInt]()
  val r2 = BuildByReflection().build[caseClass_w_annotInjectType_iface]()
  println()

  runBE_n_LE[caseClass_w_annotInjectType_identAsInt](
    descr = "type not prefilled",
    inPojo_0injectType,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )

  runBE_n_LE[caseClass_w_annotInjectType_identAsInt](
    descr = "all params filled",
    inPojo_correctInjectType,
    beBinaryData,
    leBinaryData,
    outPojo = Some(outPojo)
  )

  private val inPojo_notAnIface = caseClass_w_annotInjectType_noAnIface(
    param1 = 17433124l,
    param2 = 23,
    param3 = "lö"
  )

  "[simpleSub_invalid] using w/ big endian byte order (an exception should be thrown)" when {
    implicit val byteOrderToUse: ByteOrder = ByteOrder.BIG_ENDIAN

    "call die scheisse" should {
      "an exception should be thrown" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          Squeezer().toBinary(in = inPojo_notAnIface)
        )
      }
    }
  }

  "[simpleSub_invalid] using w/ little endian byte order (an exception should be thrown)" when {
    implicit val byteOrderToUse: ByteOrder = ByteOrder.LITTLE_ENDIAN

    "call die scheisse" should {
      "an exception should be thrown" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          Squeezer().toBinary(in = inPojo_notAnIface)
        )
      }
    }
  }
}
