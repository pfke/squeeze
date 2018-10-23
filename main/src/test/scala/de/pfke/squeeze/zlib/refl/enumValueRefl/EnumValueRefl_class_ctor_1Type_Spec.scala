package de.pintono.grind.refl.core.enumValueRefl

import de.pintono.grind.refl.core.EnumValueRefl
import de.pintono.grind.refl.core.enumRefl.mocks.Enum1Mock
import de.pintono.grind.refl.core.enumValueRefl.mocks.Enum1Mock.Enum1Mock
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.runtime.{universe => ru}

class EnumValueRefl_class_ctor_1Type_Spec
  extends WordSpec
    with Matchers {
  "create an object" when {
    "passing an enum as type" should {
      "throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(new EnumValueRefl(ru.typeOf[Enum1Mock]))
      }
    }

    "passing an enum#value as value" should {
      "dont throw an exception" in {
        val r1 = EnumValueRefl.isEnumValue(Enum1Mock._2ndEnum1Mock)

        an[IllegalArgumentException] shouldBe thrownBy(new EnumValueRefl(ru.typeOf[Enum1Mock._4thEnum1Mock.type]))
//        new EnumValueRefl(Enum1Mock._4thEnum1Mock.getClass) should not be null
      }
    }
  }
}
