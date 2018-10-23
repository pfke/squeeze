package de.pintono.grind.refl.core.entityRefl.caseClassRefl

import de.pintono.grind.refl.core.entityRefl.caseClassRefl.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import de.pintono.grind.refl.core.entityRefl.CaseClassRefl
import org.scalatest.{Matchers, WordSpec}

class CaseClassRefl_obj_isCaseClass_1String_Spec
  extends WordSpec
    with Matchers {
  "testing method 'isCaseClass(String)'" when {
    "passing a class" should {
      "should return false" in {
        CaseClassRefl.isCaseClass(classOf[ClassMock0Args].getCanonicalName) should be (right = false)
      }
    }

    "passing a case class" should {
      "should return true" in {
        CaseClassRefl.isCaseClass(classOf[CaseClassMock3Args_0Defaults].getCanonicalName) should be (right = true)
      }
    }

    "passing an object" should {
      "should return true" in {
        CaseClassRefl.isCaseClass("de.pintono.grind.refl.core.entityRefl.caseClassRefl.mocks.ObjectMock") should be (right = false)
      }
    }
  }
}
