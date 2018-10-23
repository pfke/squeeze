package de.pintono.grind.refl.core.entityRefl.classRefl

import de.pintono.grind.refl.core.entityRefl.classRefl.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import de.pintono.grind.refl.core.entityRefl.ClassRefl
import org.scalatest.{Matchers, WordSpec}

class ClassRefl_obj_isClass_1String_Spec
  extends WordSpec
    with Matchers {
  "testing method 'isCaseClass(String)'" when {
    "passing a class" should {
      "should return false" in {
        ClassRefl.isClass(classOf[ClassMock0Args].getCanonicalName) should be (right = true)
      }
    }

    "passing a case class" should {
      "should return true" in {
        ClassRefl.isClass(classOf[CaseClassMock3Args_0Defaults].getCanonicalName) should be (right = false)
      }
    }

    "passing an object" should {
      "should return true" in {
        ClassRefl.isClass("de.pintono.grind.refl.core.entityRefl.caseClassRefl.mocks.ObjectMock") should be (right = true)
      }
    }
  }
}
