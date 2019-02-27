package de.pfke.squeeze.zlib.refl.entityRefl.caseClassRefl

import de.pfke.squeeze.zlib.refl.entityRefl.caseClassRefl.mocks.{CaseClassMock3Args_0Defaults, ClassMock0Args}
import de.pfke.squeeze.zlib.refl.entityRefl.CaseClassRefl
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
      "should return an object" in {
        CaseClassRefl[CaseClassMock3Args_0Defaults]() should not be null
      }

      "should return correct name" in {
        CaseClassRefl[CaseClassMock3Args_0Defaults]().className should be (classOf[CaseClassMock3Args_0Defaults].getCanonicalName)
      }
    }
  }
}
