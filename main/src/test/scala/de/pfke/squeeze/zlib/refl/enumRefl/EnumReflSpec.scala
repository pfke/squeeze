package de.pfke.squeeze.zlib.refl.enumRefl

import de.pfke.squeeze.zlib.refl.EnumRefl
import de.pfke.squeeze.zlib.refl.enumRefl.mocks.Enum1Mock
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.runtime.{universe => ru}

class EnumReflSpec
  extends WordSpec
    with Matchers {
  "using method 'isChildOf(Type, Type)'" when {
    "passing (Enum#Value, Enum)" should {
      "return true, when Enum#Value is a child of Enum" in {
        EnumRefl.isChild(ru.typeOf[Enum1Mock._4thEnum1Mock.type], ru.typeOf[Enum1Mock.Enum1Mock]) should be (right = true)
      }

      //      TODO
      //      "return false, when Enum#Value is not a child of Enum" in {
      //        EnumRefl.isChild(ru.typeOf[Enum2Mock._4thEnum1Mock.type], ru.typeOf[Enum1Mock.Enum1Mock]) should be (right = false)
      //      }
    }
  }

  "Method 'isEnum(Type)'" when {
    "called with an enum value" should {
      "should return true" in {
        EnumRefl.isEnum(ru.typeOf[Enum1Mock.Enum1Mock]) should be (right = true)
      }
    }

    "called with a non-enum value" should {
      "should return false" in {
        EnumRefl.isEnum(ru.typeOf[String]) should be (right = false)
      }
    }

    "called with a enum value" should {
      "should return false" in {
        EnumRefl.isEnum(ru.typeOf[Enum1Mock._3rdEnum1Mock.type]) should be (right = false)
      }
    }
  }
}
