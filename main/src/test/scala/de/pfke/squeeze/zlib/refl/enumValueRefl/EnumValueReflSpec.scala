package de.pintono.grind.refl.core.enumValueRefl

import de.pintono.grind.refl.core.EnumValueRefl
import de.pintono.grind.refl.core.enumRefl.mocks.Enum1Mock
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.runtime.{universe => ru}

class EnumValueReflSpec
  extends WordSpec
    with Matchers {
  "Method 'isEnumValue[A](Any)'" when {
    "called with a primitive" should {
      "should return false" in {
        EnumValueRefl.isEnumValue(1) should be (right = false)
      }
    }

    "called with an enum value" should {
      "should return true" in {
        EnumValueRefl.isEnumValue(Enum1Mock._3rdEnum1Mock) should be (right = true)
      }
    }

    "called with an enum object" should {
      "should return false" in {
        EnumValueRefl.isEnumValue(Enum1Mock) should be (right = false)
      }
    }
  }

  "Method 'isEnumValue(Type)'" when {
    "called with a non-enum value" should {
      "should return false" in {
        EnumValueRefl.isEnumValue(ru.typeOf[String]) should be (right = false)
      }
    }

    "called with an enum value" should {
      "should return true" in {
        EnumValueRefl.isEnumValue(ru.typeOf[Enum1Mock._3rdEnum1Mock.type]) should be (right = true)
      }
    }

//    TODO
//    "called with an enum object" should {
//      "should return false" in {
//        EnumValueRefl.isEnumValue(ru.typeOf[Enum1Mock]) should be (right = false)
//      }
//    }
  }
}
