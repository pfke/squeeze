package de.pfke.squeeze.zlib.refl.entityRefl.classRefl

import de.pfke.squeeze.zlib.refl.entityRefl.classRefl.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import de.pfke.squeeze.zlib.refl.RichRuntimeMirror
import de.pfke.squeeze.zlib.refl.entityRefl.ClassRefl
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
        ClassRefl.isClass(RichRuntimeMirror().getClassSymbol("de.pfke.squeeze.zlib.refl.entityRefl.caseClassRefl.mocks.ObjectMock")) should be (right = true)
      }
    }
  }
}
