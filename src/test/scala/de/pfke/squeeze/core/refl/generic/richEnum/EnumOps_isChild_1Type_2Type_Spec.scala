package de.pfke.squeeze.core.refl.generic.richEnum

import de.pfke.squeeze.core.refl.generic.EnumOps
import de.pfke.squeeze.core.refl.generic.richEnum.mocks.{Enum1Mock, Enum2Mock}
import de.pfke.squeeze.core.refl.generic.richEnum.mocks.Enum1Mock.Enum1Mock
import org.scalatest.{Matchers, WordSpec}

class EnumOps_isChild_1Type_2Type_Spec
  extends WordSpec
    with Matchers {
  "called with mathing enum + value" when {
    "return correct childs" should {
      "result must be match" in {
        EnumOps.isChild[Enum1Mock](Enum1Mock._3rdEnum1Mock) shouldBe (right = true)
      }
    }
  }

  "called with non mathing enum + value" when {
    "return correct childs" should {
      "result must be match" in {
        EnumOps.isChild[Enum2Mock.Enum1Mock](Enum1Mock._3rdEnum1Mock) shouldBe (right = false)
      }
    }
  }

  "called with an non-enum'" when {
    "called with a non-enum value" should {
      "should throw an expection" in {
        an[IllegalArgumentException] shouldBe thrownBy(EnumOps.isChild[String](Enum1Mock._3rdEnum1Mock))
      }
    }
  }
}
