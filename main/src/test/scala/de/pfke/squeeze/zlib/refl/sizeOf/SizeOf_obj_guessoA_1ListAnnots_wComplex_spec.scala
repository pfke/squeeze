package de.pfke.squeeze.zlib.refl.sizeOf

import de.pfke.squeeze.annots.classAnnots.alignBitfieldsBy
import de.pfke.squeeze.annots.fieldAnnots.{asBitfield, withFixedSize}
import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.refl.SizeOf
import de.pfke.squeeze.zlib.refl.sizeOf.SizeOf_obj_guessoA_1ListAnnots_wComplex_spec._
import org.scalatest.{Matchers, WordSpec}

object SizeOf_obj_guessoA_1ListAnnots_wComplex_spec {
  case class SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_plainTypesMock (
    _1stField: Boolean,
    _2ndField: Int,
    _3rdField: Int,
    _4thField: Double,
    _5thField: Float,
  )

  case class SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_stringMock (
    _1stField: Boolean,
    _2ndField: Int,
    _3rdField: Int,
    _4thField: Double,
    _5thField: Float,
    _6thField: String,
    _7thField: String,
  )

  case class SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_withFixedLengthAnnotMock (
    _1stField: Boolean,
    _2ndField: Int,
    _3rdField: Int,
    _4thField: Double,
    @withFixedSize(size = 50) _5thField: String,
    _6thField: Int,
    @withFixedSize(size = 23) _7thField: String,
  )

  case class SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_listMock (
    _1stField: Boolean,
    _2ndField: Int,
    _3rdField: List[Short],
  )

  case class SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_withFixedCountMock (
    _1stField: Boolean,
    _2ndField: Int,
    @withFixedSize(size = 10) _3rdField: List[Short],
  )

  @alignBitfieldsBy(bits = 8)
  case class SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_w_annotAsBitfield_wAlignBitfieldsBy_8bit (
    param1: Long,
    @asBitfield(bits = 8)
    param2: Byte,
    @asBitfield(bits = 8)
    param3: Short,
    @asBitfield(bits = 8)
    param4: Short,
    param5: Short,
  )

  @alignBitfieldsBy(bits = 16)
  case class SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_w_annotAsBitfield_wAlignBitfieldsBy_16bit (
    param1: Long,
    @asBitfield(bits = 8)
    param2: Byte,
    @asBitfield(bits = 8)
    param3: Short,
    @asBitfield(bits = 8)
    param4: Short,
    param5: Short,
  )

  @alignBitfieldsBy(bits = 32)
  case class SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_w_annotAsBitfield_wAlignBitfieldsBy_32bit (
    param1: Long,
    @asBitfield(bits = 8)
    param2: Byte,
    @asBitfield(bits = 8)
    param3: Short,
    @asBitfield(bits = 8)
    param4: Short,
    param5: Short,
  )
}

class SizeOf_obj_guessoA_1ListAnnots_wComplex_spec
  extends WordSpec
    with Matchers {
  "testing w/ complex types" when {
    "plain types w/o any annotations" should {
      "return correct size" in {
        SizeOf.guesso[SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_plainTypesMock]() should be (21 byte)
      }
    }

    "plain types and a string" should {
      "return correct size on clazz" in {
        SizeOf.guesso[SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_stringMock]() should be (21 byte)
      }

      "return correct size on object" in {
        SizeOf.guesso(
          obj = SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_stringMock(
            _1stField = false,
            _2ndField = 0x123,
            _3rdField = 5464123,
            _4thField = 856456456.2,
            _5thField = 12.34f,
            _6thField = "hello sohi",
            _7thField = "hsdsdi",
          )
        ) should be (37 byte)
      }
    }

    "plain types w/ width annots + withFixedLength" should {
      "return correct size" in {
        SizeOf.guesso[SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_withFixedLengthAnnotMock]() should be (94 byte)
      }

      "return correct size on object" in {
        SizeOf.guesso(
          obj = SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_withFixedLengthAnnotMock(
            _1stField = false,
            _2ndField = 0x123,
            _3rdField = 5464123,
            _4thField = 856456456.2,
            _5thField = "hello sohi",
            _6thField = 12,
            _7thField = "hsdsdi",
          )
        ) should be (94 byte)
      }
    }

    "plain types and a list" should {
      "return correct size on clazz" in {
        SizeOf.guesso[SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_listMock]() should be (5 byte)
      }

      "return correct size on object" in {
        SizeOf.guesso(
          obj = SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_listMock(
            _1stField = false,
            _2ndField = 0x123,
            _3rdField = List(1, 2, 3, 4),
          )
        ) should be (13 byte)
      }
    }

    "plain types and a list withFixedCount annot" should {
      "return correct size on clazz" in {
        SizeOf.guesso[SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_withFixedCountMock]() should be (25 byte)
      }

      "return correct size on object (if list matches annot)" in {
        SizeOf.guesso(
          obj = SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_withFixedCountMock(
            _1stField = false,
            _2ndField = 0x123,
            _3rdField = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
          )
        ) should be (25 byte)
      }

      "return correct size on object (if list does not matches annot)" in {
        an [IllegalArgumentException] shouldBe thrownBy(
          SizeOf.guesso(
            obj = SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_withFixedCountMock(
              _1stField = false,
              _2ndField = 0x123,
              _3rdField = List(1, 2, 3, 4, 5, 6, 7),
            )
          ) should be (19 byte)
        )
      }
    }

    "plain types and a list asBitfield annot 8bit alignment" should {
      "return correct size on clazz" in {
        SizeOf.guesso[SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_w_annotAsBitfield_wAlignBitfieldsBy_8bit]() should be (13 byte)
      }
    }

    "plain types and a list asBitfield annot 16bit alignment" should {
      "return correct size on clazz" in {
        SizeOf.guesso[SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_w_annotAsBitfield_wAlignBitfieldsBy_16bit]() should be (14 byte)
      }
    }

    "plain types and a list asBitfield annot 32bit alignment" should {
      "return correct size on clazz" in {
        SizeOf.guesso[SizeOf_obj_guessoA_1ListAnnots_wComplex_spec_w_annotAsBitfield_wAlignBitfieldsBy_32bit]() should be (14 byte)
      }
    }
  }
}
