package de.pfke.squeeze.zlib.refl.entityRefl.classRefl

import de.pfke.squeeze.zlib.refl.entityRefl.classRefl.mocks._
import de.pfke.squeeze.zlib.refl.entityRefl.ClassRefl
import org.scalatest.{Matchers, WordSpec}

class ClassRefl_class_ctorA_1xxxAny_Spec
  extends WordSpec
    with Matchers {
  "testing class: 'ClassMock2Args_wOverloadedMethods'" when {
    lazy val tto = ClassRefl[ClassMock2Args_wOverloadedMethods]

    "call method 'apply'" should {
      "should instantiate, when passing all args: test 'arg1'" in {
        tto.ctor[ClassMock2Args_wOverloadedMethods]("sdvdvds", 123).arg1 should be ("sdvdvds")
      }

      "should instantiate, when passing all args: test 'arg2'" in {
        tto.ctor[ClassMock2Args_wOverloadedMethods]("sdvdvds", 123).arg2 should be (123)
      }

      "should throw an exception, when passing incompatible generic" in {
        an[IllegalArgumentException] shouldBe thrownBy(tto.ctor[String]("54656"))
      }

      "should throw an exception, when to few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(tto.ctor[CaseClassMock2Args_wMethods]("54656"))
      }

      "should throw an exception, when passing wrong arg types" in {
        an[IllegalArgumentException] shouldBe thrownBy(tto.ctor[CaseClassMock2Args_wMethods]("54656", "sdvdvds"))
      }

      "should throw an exception, when to much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(tto.ctor[CaseClassMock2Args_wMethods]("54656", 456, false))
      }
    }
  }
}
