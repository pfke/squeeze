package de.pfke.squeeze.core.refl.generic.classOps

import de.pfke.squeeze.core.refl.generic.ClassOps
import de.pfke.squeeze.core.refl.generic.richClass.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import org.scalatest.{Matchers, WordSpec}

class ClassOps_obj_isCaseClass_1Class_Spec
  extends WordSpec
    with Matchers {
  "testing method 'isCaseClass(Class[_])'" when {
    "passing a class" should {
      "should return false" in {
        ClassOps.isCaseClass(classOf[ClassMock0Args]) shouldBe (right = false)
      }
    }

    "passing a case class" should {
      "should return true" in {
        ClassOps.isCaseClass(classOf[CaseClassMock3Args_0Defaults]) shouldBe (right = true)
      }
    }
  }
}
