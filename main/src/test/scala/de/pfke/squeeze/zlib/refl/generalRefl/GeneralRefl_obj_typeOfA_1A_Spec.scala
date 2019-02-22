package de.pfke.squeeze.zlib.refl.generalRefl

import java.io.File

import de.pfke.squeeze.zlib.refl.{GeneralRefl, RichInstanceMirror}
import de.pfke.squeeze.zlib.refl.generalRefl.mocks.Enum1Mock
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.runtime.{universe => ru}

class GeneralRefl_obj_typeOfA_1A_Spec
  extends WordSpec
    with Matchers{
  "testing the method" when {
    "passing a primitive type" should {
      "return correct for Int" in {
        GeneralRefl.typeOf(13) should be (ru.typeOf[java.lang.Integer])
      }
    }

    "passing a complex type" should {
      "return correct for String" in {
        GeneralRefl.typeOf("klöklöklö") should be (ru.typeOf[java.lang.String])
      }

      "return correct for Path" in {
        GeneralRefl.typeOf(new File(".")) should be (ru.typeOf[File])
      }
    }

    "passing an enum type (no generic type given)" should {
      "return the correct type" in {
        val r1 = GeneralRefl.typeOf(Enum1Mock)
        val r2 = ru.typeOf[Enum1Mock.type]

        withClue(s"GeneralRefl.typeOf(Enum1Mock)='$r1', ru.typeOf[Enum1Mock.type]='$r2'") {
          (r1 =:= r2) should be (right = true)
        }
      }
    }
  }
}
