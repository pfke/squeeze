package de.pintono.grind.refl.core.richInstanceMirror

import de.pintono.grind.refl.core.RichInstanceMirror
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.runtime.{universe => ru}

class RichInstanceMirrorSpec
  extends WordSpec
    with Matchers {
  "Build a RichInstanceMirror from a case class" when {
    val tto = RichInstanceMirror(classOf[CaseClassMock])

    "testing method 'fullName'" should {
      "should return correct class name" in {
        tto.fullName should be (ru.typeOf[CaseClassMock].typeSymbol.asClass.fullName)
      }
    }

    "testing method 'call(String, Any*)'" should {
      "should return Some(object), when called with correct params" in {
        tto.call("apply", "heinze", 145) should be (Some(CaseClassMock("heinze", 145)))
      }

      "should return None, when called with wrong params" in {
        tto.call("apply", 156, "heinze") should be (None)
      }
    }
  }

  "Build a RichInstanceMirror from a class" when {
//    val tto = RichInstanceMirror(classOf[ClassMock])

    "testing result" should {
      "should have the same type" in {
//        tto.typeSignature should be (ru.typeOf[ClassMock])
      }
    }
  }
}
