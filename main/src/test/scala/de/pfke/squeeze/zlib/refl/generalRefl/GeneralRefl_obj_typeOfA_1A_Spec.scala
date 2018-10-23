package de.pintono.grind.refl.core.generalRefl

import java.io.File

import de.pintono.grind.refl.core.{GeneralRefl, RichInstanceMirror, RichRuntimeMirror}
import de.pintono.grind.refl.core.generalRefl.mocks.Enum1Mock
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

    "passing an enum#value type" should {
      "return the correct type" in {
        val t1 = RichInstanceMirror(Enum1Mock._2ndValue)
        val t2 = t1.typeSignature
        val t3 = t1.asTpe
        val t4 = t1.asType
        val t5 = t2.companion

        val v1 = Enum1Mock._2ndValue.getClass




        val r1 = GeneralRefl.typeOf(Enum1Mock._2ndValue)
        val r2 = ru.typeOf[Enum1Mock.Enum1Mock]

        withClue(s"GeneralRefl.typeOf(Enum1Mock._2ndValue)='$r1', ru.typeOf[Enumeration#Value]='$r2'") {
          (r1 =:= r2) should be (right = true)
        }





//        val f1 = RichRuntimeMirror()
//        val f2 = f1.getInstanceMirror(Enum1Mock._2ndValue)
//        val f3 = f2.symbol
//        val f4 = f3.typeSignature
//
//        val a2 = f1.getInstanceMirror(Enum1Mock)
//        val a3 = a2.symbol
//        val a4 = a3.typeSignature
//
//        val r1 = GeneralRefl.typeOf(Enum1Mock._2ndValue)
//        val r2 = ru.typeOf[Enum1Mock.Value]

        (GeneralRefl.typeOf(Enum1Mock._2ndValue) =:= ru.typeOf[Enum1Mock.Value]) should be (right = true)
//        GeneralRefl.typeOf(Enum1Mock._2ndValue) should be (ru.typeOf[Enum1Mock.Value])
      }
    }
  }
}
