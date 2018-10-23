package de.pintono.grind.refl.core.entityRefl.caseClassRefl

import de.pintono.grind.refl.core.entityRefl.caseClassRefl.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import de.pintono.grind.refl.core.entityRefl.CaseClassRefl
import org.scalatest.{Matchers, WordSpec}

class CaseClassRefl_obj_isCaseClass_1Class_Spec
  extends WordSpec
    with Matchers {
  "testing method 'isCaseClass(Class[_])'" when {
    "passing a class" should {
      "should return false" in {
        CaseClassRefl.isCaseClass(classOf[ClassMock0Args]) should be (right = false)
      }
    }

    "passing a case class" should {
      "should return true" in {
        CaseClassRefl.isCaseClass(classOf[CaseClassMock3Args_0Defaults]) should be (right = true)
      }
    }
  }
}
