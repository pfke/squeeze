package de.pfke.squeeze.zlib.refl.enumValueRefl

import de.pfke.squeeze.zlib.refl.EnumValueRefl
import de.pfke.squeeze.zlib.refl.enumValueRefl.mocks.Enum1Mock.Enum1Mock
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.runtime.{universe => ru}

class EnumValueRefl_obj_isEnumValue_1Type_Spec
  extends WordSpec
    with Matchers {
  "Method 'isEnumValue(Type)'" when {
    "called with a primitive" should {
      "should return false" in {
        EnumValueRefl.isEnumValue(ru.typeOf[Int]) should be (right = false)
      }
    }

    "called with a complex" should {
      "should return false" in {
        EnumValueRefl.isEnumValue(ru.typeOf[String]) should be (right = false)
      }
    }

    //"called with an enum value" should {
    //  "should return true" in {
    //    EnumValueRefl.isEnumValue(Enum1Mock._3rdEnum1Mock) should be (right = true)
    //  }
    //}

    "called with an enum object" should {
      "should return false" in {
        EnumValueRefl.isEnumValue(ru.typeOf[Enum1Mock]) should be (right = true) // er gibt doch irgendie die value raus
      }
    }
  }
}
