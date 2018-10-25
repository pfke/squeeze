package de.pfke.squeeze.zlib.refl.entityRefl.classRefl

import de.pfke.squeeze.zlib.refl.entityRefl.classRefl.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import de.pfke.squeeze.zlib.refl.entityRefl.ClassRefl
import org.scalatest.{Matchers, WordSpec}

class ClassRefl_obj_isClass_1Class_Spec
  extends WordSpec
    with Matchers {
  "testing method 'isCaseClass(Class[_])'" when {
    "passing a class" should {
      "should return false" in {
        ClassRefl.isClass(classOf[ClassMock0Args]) should be (right = true)
      }
    }

    "passing a case class" should {
      "should return true" in {
        ClassRefl.isClass(classOf[CaseClassMock3Args_0Defaults]) should be (right = false)
      }
    }
  }
}
