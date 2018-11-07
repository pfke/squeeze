package de.pfke.squeeze.zlib.refl.sizeOf

import de.pfke.squeeze.annots.{asBitfield, width}
import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.refl.{FieldDescr, FieldHelper, SizeOf}
import org.scalatest.{Matchers, WordSpec}

case class SizeOf_obj_guessoA_spec_BooleanMock (
  @asBitfield(bits = 4) _4Bits: Boolean,
  @asBitfield(bits = 8) _8Bits: Boolean,
  @asBitfield(bits = 9) _9Bits: Boolean,
  @width(size = 1) _1Byte: Boolean,
  @width(size = 4) _4Byte: Boolean,
  @width(size = 5) _5Byte: Boolean,
)

class SizeOf_obj_guessoA_spec
  extends WordSpec
    with Matchers {
  "testing w/ primitve types" when {
    "Boolean" should {
      "no annotation" in {
        SizeOf.guesso[Boolean]() should be (1 byte)
      }

      "4Bits" in {
        SizeOf.guesso[Boolean](
          annots = FieldHelper
            .getFields[SizeOf_obj_guessoA_spec_BooleanMock]()
            .find(_.name == "_4Bits").get.annos
        ) should be (4 bit)
      }
    }
  }
}
