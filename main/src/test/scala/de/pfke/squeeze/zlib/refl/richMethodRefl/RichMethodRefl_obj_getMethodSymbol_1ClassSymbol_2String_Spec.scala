package de.pfke.squeeze.zlib.refl.richMethodRefl

import de.pfke.squeeze.zlib.refl.richMethodRefl.mocks.{CaseClassMock0Args, CaseClassMock2Args_wMethods, CaseClassMock2Args_wOverloadedMethods}
import de.pfke.squeeze.zlib.refl.{RichMethodRefl, RichRuntimeMirror}
import org.scalatest.{Matchers, WordSpec}

class RichMethodRefl_obj_getMethodSymbol_1ClassSymbol_2String_Spec
  extends WordSpec
    with Matchers {
  // TODO

//  "testing case class with 0 args" when {
//    "reflecting method 'apply'" should {
//      "should return 0 methods" in {
//        RichMethodRefl.getMethodSymbol(RichRuntimeMirror().getClassSymbol(classOf[CaseClassMock0Args]), "apply").isEmpty should be (right = true)
//      }
//    }
//  }
//
//  "testing case class with 2 args + w/o methods" when {
//    "reflecting method 'apply'" should {
//      "should return 0 methods" in {
//        RichMethodRefl.getMethodSymbol(RichRuntimeMirror().getClassSymbol(classOf[CaseClassMock2Args_wMethods]), "apply").isEmpty should be (right = true)
//      }
//    }
//  }
//
//  "testing case class with 2 args + w/ methods" when {
//    "reflecting method 'apply'" should {
//      "should return 0 methods" in {
//        RichMethodRefl.getMethodSymbol(RichRuntimeMirror().getClassSymbol(classOf[CaseClassMock2Args_wMethods]), "apply").isEmpty should be (right = true)
//      }
//    }
//
//    "reflecting method 'method1'" should {
//      val tto = RichMethodRefl.getMethodSymbol(RichRuntimeMirror().getClassSymbol(classOf[CaseClassMock2Args_wMethods]), "method1")
//
//      "should return 1 method" in {
//        tto.size should be (1)
//      }
//
//      "should return an instance w/ correct name" in {
//        tto.head
//          .fullName should be ("de.pfke.squeeze.zlib.refl.richMethodRefl.mocks.CaseClassMock2Args_wMethods.method1")
//      }
//
//      "should return 1 found method w/ 2 args" in {
//        tto.head
//          .paramLists
//          .head.size should be (0)
//      }
//    }
//
//    "reflecting method 'method2'" should {
//      val tto = RichMethodRefl.getMethodSymbol(RichRuntimeMirror().getClassSymbol(classOf[CaseClassMock2Args_wMethods]), "method2")
//
//      "should return 1 method" in {
//        tto.size should be (1)
//      }
//
//      "should return an instance w/ correct name" in {
//        tto.head
//          .fullName should be ("de.pfke.squeeze.zlib.refl.richMethodRefl.mocks.CaseClassMock2Args_wMethods.method2")
//      }
//
//      "should return 1 found method w/ 2 args" in {
//        tto.head
//          .paramLists
//          .head.size should be (2)
//      }
//    }
//  }
//
//  "testing case class with 2 args + w/ overloaded methods" when {
//    "reflecting method 'apply'" should {
//      "should return 0 methods" in {
//        RichMethodRefl.getMethodSymbol(RichRuntimeMirror().getClassSymbol(classOf[CaseClassMock2Args_wOverloadedMethods]), "apply").isEmpty should be (right = true)
//      }
//    }
//
//    "reflecting method 'method1'" should {
//      val tto = RichMethodRefl.getMethodSymbol(RichRuntimeMirror().getClassSymbol(classOf[CaseClassMock2Args_wOverloadedMethods]), "method1")
//
//      "should return 1 method" in {
//        tto.size should be (1)
//      }
//
//      "should return an instance w/ correct name" in {
//        tto.head
//          .fullName should be ("de.pfke.squeeze.zlib.refl.richMethodRefl.mocks.CaseClassMock2Args_wOverloadedMethods.method1")
//      }
//
//      "should return 1st found method w/ 1 args" in {
//        tto.head
//          .paramLists
//          .head.size should be (0)
//      }
//    }
//
//    "reflecting method 'method2'" should {
//      val tto = RichMethodRefl.getMethodSymbol(RichRuntimeMirror().getClassSymbol(classOf[CaseClassMock2Args_wOverloadedMethods]), "method2")
//
//      "should return 3 methods" in {
//        tto.size should be (3)
//      }
//
//      "should return 1st w/ correct name" in {
//        tto.head
//          .fullName should be ("de.pfke.squeeze.zlib.refl.richMethodRefl.mocks.CaseClassMock2Args_wOverloadedMethods.method2")
//      }
//
//      "should return 1st found method w/ 1 args" in {
//        tto.find(_.paramLists.head.size == 1) should not be None
//      }
//
//      "should return 2nd w/ correct name" in {
//        tto(1)
//          .fullName should be ("de.pfke.squeeze.zlib.refl.richMethodRefl.mocks.CaseClassMock2Args_wOverloadedMethods.method2")
//      }
//
//      "should return 2nd found method w/ 2 args" in {
//        tto.find(_.paramLists.head.size == 2) should not be None
//      }
//
//      "should return 3rd w/ correct name" in {
//        tto(2)
//          .fullName should be ("de.pfke.squeeze.zlib.refl.richMethodRefl.mocks.CaseClassMock2Args_wOverloadedMethods.method2")
//      }
//
//      "should return 3rd found method w/ 3 args" in {
//        tto.find(_.paramLists.head.size == 3) should not be None
//      }
//    }
//  }
}
