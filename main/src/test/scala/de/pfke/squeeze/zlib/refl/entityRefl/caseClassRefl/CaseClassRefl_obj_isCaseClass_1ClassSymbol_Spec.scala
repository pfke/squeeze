package de.pfke.squeeze.zlib.refl.entityRefl.caseClassRefl

import de.pfke.squeeze.zlib.refl.entityRefl.caseClassRefl.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import de.pfke.squeeze.zlib.refl.RichRuntimeMirror
import de.pfke.squeeze.zlib.refl.entityRefl.CaseClassRefl
import org.scalatest.{Matchers, WordSpec}

class CaseClassRefl_obj_isCaseClass_1ClassSymbol_Spec
  extends WordSpec
    with Matchers {
  "testing method 'isCaseClass(ClassSymbol)'" when {
    "passing a class" should {
      "should return false" in {
        CaseClassRefl.isCaseClass(RichRuntimeMirror().getClassSymbol(classOf[ClassMock0Args])) should be (right = false)
      }
    }

    "passing a case class" should {
      "should return true" in {
        CaseClassRefl.isCaseClass(RichRuntimeMirror().getClassSymbol(classOf[CaseClassMock3Args_0Defaults])) should be (right = true)
      }
    }

    "passing an object" should {
      "should return true" in {
        CaseClassRefl.isCaseClass(RichRuntimeMirror().getClassSymbol("de.pfke.squeeze.zlib.refl.entityRefl.caseClassRefl.mocks.ObjectMock")) should be (right = false)
      }
    }
  }
}
