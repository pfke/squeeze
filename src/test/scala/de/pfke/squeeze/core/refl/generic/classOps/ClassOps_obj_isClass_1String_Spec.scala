package de.pfke.squeeze.core.refl.generic.classOps

import de.pfke.squeeze.core.refl.generic.ClassOps
import de.pfke.squeeze.core.refl.generic.richClass.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import org.scalatest.{Matchers, WordSpec}

class ClassOps_obj_isClass_1String_Spec
  extends WordSpec
    with Matchers {
  "testing method 'isCaseClass(String)'" when {
    "passing a class" should {
      "should return false" in {
        ClassOps.isClass(classOf[ClassMock0Args].getCanonicalName) shouldBe (right = true)
      }
    }

    "passing a case class" should {
      "should return true" in {
        ClassOps.isClass(classOf[CaseClassMock3Args_0Defaults].getCanonicalName) shouldBe (right = false)
      }
    }

    "passing an object" should {
      "should return true" in {
        ClassOps.isClass("de.pfke.squeeze.core.refl.generic.richCaseClass.mocks.ObjectMock") shouldBe (right = true)
      }
    }
  }
}
