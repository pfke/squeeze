package de.pfke.squeeze.squeezer.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.annots.withFixedLength
import de.pfke.squeeze.serialize.serializerBuilder.BuildByReflection
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.complex.Squeezer_caseClass_w_annotWithFixedLength_spec.caseClass_w_annotWithFixedLength_simpleSub

object Squeezer_caseClass_w_annotWithFixedLength_spec {
  case class caseClass_w_annotWithFixedLength_simpleSub(
    param1: Long,
    @withFixedLength(size = 5)
    param2: String,
  )

  case class caseClass_w_annotWithFixedLength_invalid(
    param1: Long,
    @withFixedLength(size = 5)
    param2: Byte,
  )
}

class Squeezer_caseClass_w_annotWithFixedLength_spec
  extends BaseSqueezerSpec {
  private val inPojo = caseClass_w_annotWithFixedLength_simpleSub(
    param1 = 17433124l,
    param2 = "jklas",
  )
  private val inPojo_tooMuch = caseClass_w_annotWithFixedLength_simpleSub(
    param1 = 17433124l,
    param2 = "jklassdfsdfggegr",
  )
  private val inPojo_tooFew = caseClass_w_annotWithFixedLength_simpleSub(
    param1 = 17433124l,
    param2 = "jkl",
  )
  private val outPojo = caseClass_w_annotWithFixedLength_simpleSub(
    param1 = 17433124l,
    param2 = "jklas",
  )
  private val outPojo_tooFew = caseClass_w_annotWithFixedLength_simpleSub(
    param1 = 17433124l,
    param2 = "jkl",
  )
  private val beBinaryData_simpleSub = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,

    0x6a, 0x6b, 0x6c, 0x61, 0x73
  )
  private val leBinaryData_simpleSub = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,

    0x6a, 0x6b, 0x6c, 0x61, 0x73
  )
  private val beBinaryData_simpleSub_tooFew = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,

    0x6a, 0x6b, 0x6c, 0x00, 0x00
  )
  private val leBinaryData_simpleSub_tooFew = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,

    0x6a, 0x6b, 0x6c, 0x00, 0x00
  )

  "[simpleSub] using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedLength_simpleSub](ByteOrder.BIG_ENDIAN, inPojo, beBinaryData_simpleSub, outPojo = Some(outPojo))
  }

  "[simpleSub] using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedLength_simpleSub](ByteOrder.LITTLE_ENDIAN, inPojo, leBinaryData_simpleSub, outPojo = Some(outPojo))
  }

  "[simpleSub_tooFew] using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedLength_simpleSub](ByteOrder.BIG_ENDIAN, inPojo_tooFew, beBinaryData_simpleSub_tooFew, outPojo = Some(outPojo_tooFew))
  }

  "[simpleSub_tooFew] using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedLength_simpleSub](ByteOrder.LITTLE_ENDIAN, inPojo_tooFew, leBinaryData_simpleSub_tooFew, outPojo = Some(outPojo_tooFew))
  }

  "[simpleSub_tooMuch] using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedLength_simpleSub](ByteOrder.BIG_ENDIAN, inPojo_tooMuch, beBinaryData_simpleSub, outPojo = Some(outPojo))
  }

  "[simpleSub_tooMuch] using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_annotWithFixedLength_simpleSub](ByteOrder.LITTLE_ENDIAN, inPojo_tooMuch, leBinaryData_simpleSub, outPojo = Some(outPojo))
  }
}
