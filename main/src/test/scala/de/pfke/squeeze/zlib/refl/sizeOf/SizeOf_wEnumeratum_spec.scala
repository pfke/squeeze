package de.pfke.squeeze.zlib.refl.sizeOf

import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.refl.SizeOf
import enumeratum.values._
import org.scalatest.{Matchers, WordSpec}

object SizeOf_wEnumeratum_spec {
  sealed abstract class sowe_asByteEnum(val value: Byte) extends ByteEnumEntry
  sealed abstract class sowe_asCharEnum(val value: Char) extends CharEnumEntry
  sealed abstract class sowe_asIntEnum(val value: Int) extends IntEnumEntry
  sealed abstract class sowe_asLongEnum(val value: Long) extends LongEnumEntry
  sealed abstract class sowe_asShortEnum(val value: Short) extends ShortEnumEntry
  sealed abstract class sowe_asStringEnum(val value: String) extends StringEnumEntry
}

class SizeOf_wEnumeratum_spec
  extends WordSpec
    with Matchers {
  "testing w/ all supported types" when {
    "using ByteEnum" should {
      "return correct size" in {
        SizeOf.guesso[SizeOf_wEnumeratum_spec.sowe_asByteEnum]() should be (1 byte)
      }
    }
    "using CharEnum" should {
      "return correct size" in {
        SizeOf.guesso[SizeOf_wEnumeratum_spec.sowe_asCharEnum]() should be (2 byte)
      }
    }
    "using IntEnum" should {
      "return correct size" in {
        SizeOf.guesso[SizeOf_wEnumeratum_spec.sowe_asIntEnum]() should be (4 byte)
      }
    }
    "using LongEnum" should {
      "return correct size" in {
        SizeOf.guesso[SizeOf_wEnumeratum_spec.sowe_asLongEnum]() should be (8 byte)
      }
    }
    "using ShortEnum" should {
      "return correct size" in {
        SizeOf.guesso[SizeOf_wEnumeratum_spec.sowe_asShortEnum]() should be (2 byte)
      }
    }
    "using StringEnum" should {
      "return correct size" in {
        SizeOf.guesso[SizeOf_wEnumeratum_spec.sowe_asStringEnum]() should be (0 byte)
      }
    }
  }
}
