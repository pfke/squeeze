package de.pfke.squeeze.zlib.refl.fieldHelper

import de.pfke.squeeze.zlib.refl.FieldHelper
import de.pfke.squeeze.zlib.refl.fieldHelper.mocks.BitfieldsSeparated
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.runtime.{universe => ru}

class FieldHelper_groupByBitfields_Spec
  extends WordSpec
    with Matchers{
  "passing a class w/ separated bitfields" when {
    "check general zeuch" should {
      "return correct list size" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated]).size should be(6)
      }
    }

    "check 1st" should {
      "return correct list size" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated]).head.size should be(3)
      }

      "return correct 1st obj" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated]).head.head.name should be("field01")
      }

      "return correct 2nd obj" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated]).head(1).name should be("field02")
      }

      "return correct 3rd obj" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated]).head(2).name should be("field03")
      }
    }

    "check 2nd" should {
      "return correct list size" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated])(1).size should be(1)
      }

      "return correct 1st obj" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated])(1).head.name should be("field04")
      }
    }

    "check 3rd" should {
      "return correct list size" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated])(2).size should be(2)
      }

      "return correct 1st obj" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated])(2).head.name should be("field05")
      }

      "return correct 2nd obj" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated])(2)(1).name should be("field06")
      }
    }

    "check 4th" should {
      "return correct list size" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated])(3).size should be(1)
      }

      "return correct 1st obj" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated])(3).head.name should be("field07")
      }
    }

    "check 5th" should {
      "return correct list size" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated])(4).size should be(1)
      }

      "return correct 1st obj" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated])(4).head.name should be("field08")
      }
    }

    "check 6th" should {
      "return correct list size" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated])(5).size should be(1)
      }

      "return correct 1st obj" in {
        FieldHelper.groupByBitfields(ru.typeOf[BitfieldsSeparated])(5).head.name should be("field09")
      }
    }
  }
}
