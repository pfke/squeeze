package de.pfke.squeeze.core.refl.generic.richEnum

import de.pfke.squeeze.core.refl.generic.RichEnum
import de.pfke.squeeze.core.refl.generic.richEnum.mocks.Enum1Mock.Enum1Mock
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.runtime.{universe => ru}

class RichEnum_ctor_1Type_Spec
  extends WordSpec
    with Matchers {
  "" when {
    "passing an enum" should {
      "not throw an exception" in {
        new RichEnum(ru.typeOf[Enum1Mock]) should not be null
      }
    }
  }
}
