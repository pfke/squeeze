package de.pfke.squeeze.zlib.refl.enumRefl

import de.pfke.squeeze.zlib.refl.EnumRefl
import de.pfke.squeeze.zlib.refl.enumRefl.mocks.Enum1Mock.Enum1Mock
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.runtime.{universe => ru}

class EnumRefl_class_ctor_1Type_Spec
  extends WordSpec
    with Matchers {
  "" when {
    "passing an enum" should {
      "not throw an exception" in {
        new EnumRefl(ru.typeOf[Enum1Mock]) should not be null
      }
    }
  }
}
