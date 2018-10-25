package de.pfke.squeeze.zlib.refl.entityRefl.classRefl

import de.pfke.squeeze.zlib.refl.entityRefl.classRefl.mocks._
import de.pfke.squeeze.zlib.refl.entityRefl.ClassRefl
import org.scalatest.{Matchers, WordSpec}

class ClassRefl_obj_isClassA_Spec
  extends WordSpec
    with Matchers {
  "testing method 'isCaseClass[A]'" when {
    "passing a class" should {
      "should return false" in {
        ClassRefl.isClass[ClassMock0Args] should be (right = true)
      }
    }

    "passing a case class" should {
      "should return true" in {
        ClassRefl.isClass[CaseClassMock3Args_0Defaults] should be (right = false)
      }
    }
  }
}
