package de.pfke.squeeze.core.refl.classOps

import de.pfke.squeeze.core.refl.richClass.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import de.pfke.squeeze.core.refl.{ClassOps, RichRuntimeMirror}
import org.scalatest.{Matchers, WordSpec}

class ClassOps_obj_isClass_1ClassSymbol_Spec
  extends WordSpec
    with Matchers {
  "testing method 'isCaseClass(ClassSymbol)'" when {
    "passing a class" should {
      "should return false" in {
        ClassOps.isClass(RichRuntimeMirror().getClassSymbol(classOf[ClassMock0Args])) shouldBe (right = true)
      }
    }

    "passing a case class" should {
      "should return true" in {
        ClassOps.isClass(RichRuntimeMirror().getClassSymbol(classOf[CaseClassMock3Args_0Defaults])) shouldBe (right = false)
      }
    }

    "passing an object" should {
      "should return true" in {
        ClassOps.isClass(RichRuntimeMirror().getClassSymbol("de.pfke.grind.core.refl.richCaseClass.mocks.ObjectMock")) shouldBe (right = true)
      }
    }
  }
}
