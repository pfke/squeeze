package de.pfke.squeeze.core.refl.generic.richCaseClass

import de.pfke.squeeze.core.refl.generic.RichCaseClass
import de.pfke.squeeze.core.refl.generic.richCaseClass.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import org.scalatest.{Matchers, WordSpec}

class CaseClassRefl_obj_applyA_Spec
  extends WordSpec
    with Matchers {
  "testing method 'apply[A]'" when {
    "passing a class" should {
      "should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(RichCaseClass[ClassMock0Args]())
      }
    }

    "passing a case class" should {
      val tto = RichCaseClass[CaseClassMock3Args_0Defaults]()

      "should return an object" in {
        tto should not be null
      }

      "should return correct name" in {
        tto.className should be (classOf[CaseClassMock3Args_0Defaults].getCanonicalName)
      }
    }
  }
}
