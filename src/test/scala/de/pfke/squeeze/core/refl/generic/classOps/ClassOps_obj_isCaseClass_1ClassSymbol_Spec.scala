package de.pfke.squeeze.core.refl.generic.classOps

import de.pfke.squeeze.core.refl.generic.richClass.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import de.pfke.squeeze.core.refl.generic.{ClassOps, RichRuntimeMirror}
import org.scalatest.{Matchers, WordSpec}

class ClassOps_obj_isCaseClass_1ClassSymbol_Spec
  extends WordSpec
    with Matchers {
  "testing method 'isCaseClass(ClassSymbol)'" when {
    "passing a class" should {
      "should return false" in {
        ClassOps.isCaseClass(RichRuntimeMirror().getClassSymbol(classOf[ClassMock0Args])) shouldBe (right = false)
      }
    }

    "passing a case class" should {
      "should return true" in {
        ClassOps.isCaseClass(RichRuntimeMirror().getClassSymbol(classOf[CaseClassMock3Args_0Defaults])) shouldBe (right = true)
      }
    }

    "passing an object" should {
      "should return true" in {
        ClassOps.isCaseClass(RichRuntimeMirror().getClassSymbol("de.pfke.squeeze.core.refl.generic.richCaseClass.mocks.ObjectMock")) shouldBe (right = false)
      }
    }
  }
}
