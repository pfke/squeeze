package de.pintono.grind.refl.core.entityRefl.classRefl

import de.pintono.grind.refl.core.entityRefl.classRefl.mocks._
import de.pintono.grind.refl.core.entityRefl.ClassRefl
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
