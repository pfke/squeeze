package de.pfke.squeeze.zlib.refl.entityRefl.caseClassRefl

import de.pfke.squeeze.zlib.refl.entityRefl.caseClassRefl.mocks._
import de.pfke.squeeze.zlib.refl.entityRefl.CaseClassRefl
import de.pfke.squeeze.zlib.refl.richMethodRefl.mocks.CaseClassMock2Args_wOverloadedMethods_allDefaults
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.runtime.{universe => ru}

class CaseClassRefl_class_findApplyMatchingTheseNames_1xxxString_Spec
  extends WordSpec
    with Matchers {
  "testing case class: 'CaseClassMock2Args_wMethods'" when {
    lazy val tto = CaseClassRefl[CaseClassMock2Args_wMethods]

    "passing the correct params" should {
      "should return the apply method, when passing all names" in {
        tto.findApplyMethod_matching_paramNames("arg1", "arg2") should not be None
      }

      "should return the apply method, when passing too few names" in {
        tto.findApplyMethod_matching_paramNames("arg1") should not be None
      }
    }

    "passing the wrong params" should {
      "should throw an exception, when passing wrong names" in {
        tto.findApplyMethod_matching_paramNames("arg1", "arg2lkö") should be (None)
      }

      "should throw an exception, when passing too much names" in {
        tto.findApplyMethod_matching_paramNames("arg1", "arg2", "arg3") should be (None)
      }
    }
  }

  "testing case class: 'CaseClassMock2Args_wOverloadedMethods_allDefaults'" when {
    lazy val tto = CaseClassRefl[CaseClassMock2Args_wOverloadedMethods_allDefaults]

    "passing the correct params" should {
      "should return the apply method, when passing all names" in {
        tto.findApplyMethod_matching_paramNames("arg1", "arg2") should not be None
      }

      "should return the apply method, when passing too few names" in {
        tto.findApplyMethod_matching_paramNames("arg1") should not be None
      }
    }

    "passing the wrong params" should {
      "should throw an exception, when passing wrong names" in {
        tto.findApplyMethod_matching_paramNames("arg1", "arg2lkö") should be (None)
      }

      "should throw an exception, when passing too much names" in {
        tto.findApplyMethod_matching_paramNames("arg1", "arg2", "arg3") should be (None)
      }
    }
  }
}
