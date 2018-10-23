package de.pintono.grind.refl.core.entityRefl.caseClassRefl

import de.pintono.grind.refl.core.entityRefl.caseClassRefl.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import de.pintono.grind.refl.core.entityRefl.CaseClassRefl
import org.scalatest.{Matchers, WordSpec}

class CaseClassRefl_obj_applyA_Spec
  extends WordSpec
    with Matchers {
  "testing method 'apply[A]'" when {
    "passing a class" should {
      "should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(CaseClassRefl[ClassMock0Args]())
      }
    }

    "passing a case class" should {
      val tto = CaseClassRefl[CaseClassMock3Args_0Defaults]()

      "should return an object" in {
        tto should not be null
      }

      "should return correct name" in {
        tto.className should be (classOf[CaseClassMock3Args_0Defaults].getCanonicalName)
      }
    }
  }
}
