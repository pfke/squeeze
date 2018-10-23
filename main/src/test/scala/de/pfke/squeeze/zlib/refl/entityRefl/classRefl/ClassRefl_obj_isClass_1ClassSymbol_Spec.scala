package de.pintono.grind.refl.core.entityRefl.classRefl

import de.pintono.grind.refl.core.entityRefl.classRefl.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import de.pintono.grind.refl.core.RichRuntimeMirror
import de.pintono.grind.refl.core.entityRefl.ClassRefl
import org.scalatest.{Matchers, WordSpec}

class ClassRefl_obj_isClass_1ClassSymbol_Spec
  extends WordSpec
    with Matchers {
  "testing method 'isCaseClass(ClassSymbol)'" when {
    "passing a class" should {
      "should return false" in {
        ClassRefl.isClass(RichRuntimeMirror().getClassSymbol(classOf[ClassMock0Args])) should be (right = true)
      }
    }

    "passing a case class" should {
      "should return true" in {
        ClassRefl.isClass(RichRuntimeMirror().getClassSymbol(classOf[CaseClassMock3Args_0Defaults])) should be (right = false)
      }
    }

    "passing an object" should {
      "should return true" in {
        ClassRefl.isClass(RichRuntimeMirror().getClassSymbol("de.pintono.grind.refl.core.entityRefl.caseClassRefl.mocks.ObjectMock")) should be (right = true)
      }
    }
  }
}
