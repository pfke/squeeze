package de.pfke.squeeze.zlib.refl.annotationRefl

import de.pfke.squeeze.zlib.refl.AnnotationRefl
import de.pfke.squeeze.zlib.refl.annotationRefl.AnnotationRefl_classAnnot_spec.{AnnotationRefl_classAnnot_cc1, AnnotationRefl_classAnnot_cc2, AnnotationRefl_classAnnot_simpleAnnot_wInt, AnnotationRefl_classAnnot_simpleAnnot_wString}
import org.scalatest.{Matchers, WordSpec}

import scala.annotation.StaticAnnotation
import scala.reflect.runtime.{universe => ru}

object AnnotationRefl_classAnnot_spec {
  case class AnnotationRefl_classAnnot_simpleAnnot_wInt (
    param1: Int
  ) extends StaticAnnotation
  case class AnnotationRefl_classAnnot_simpleAnnot_wString (
    param1: String
  ) extends StaticAnnotation

  @AnnotationRefl_classAnnot_simpleAnnot_wString(
    param1 = "lököl"
  )
  case class AnnotationRefl_classAnnot_cc1()

  @AnnotationRefl_classAnnot_simpleAnnot_wInt(
    param1 = 13
  )
  @AnnotationRefl_classAnnot_simpleAnnot_wString(
    param1 = "lököl"
  )
  case class AnnotationRefl_classAnnot_cc2()
}

class AnnotationRefl_classAnnot_spec
  extends WordSpec
    with Matchers {
  implicit val classLoader: ClassLoader = this.getClass.getClassLoader

  "testing method 'create[A]'" when {
    "verify 'AnnotationRefl_classAnnot_simpleAnnot_wInt'" should {
      "should not find on cc1 class" in {
        AnnotationRefl
          .create[AnnotationRefl_classAnnot_simpleAnnot_wInt](
          ru
            .typeOf[AnnotationRefl_classAnnot_cc1]
            .typeSymbol
            .annotations
        ) should be(
          None
        )
      }

      "should find on cc2 class" in {
        AnnotationRefl
          .create[AnnotationRefl_classAnnot_simpleAnnot_wInt](
          ru
            .typeOf[AnnotationRefl_classAnnot_cc2]
            .typeSymbol
            .annotations
        ) should be(
          Some(
            AnnotationRefl_classAnnot_simpleAnnot_wInt(
              param1 = 13
          )
        ))
      }
    }

    "verify 'AnnotationRefl_classAnnot_simpleAnnot_wString'" should {
      "should find on cc1 class" in {
        AnnotationRefl
          .create[AnnotationRefl_classAnnot_simpleAnnot_wString](
          ru
            .typeOf[AnnotationRefl_classAnnot_cc1]
            .typeSymbol
            .annotations
        ) should be (
          Some(
            AnnotationRefl_classAnnot_simpleAnnot_wString(
              param1 = "lököl"
            )))
      }

      "should find on cc2 class" in {
        AnnotationRefl
          .create[AnnotationRefl_classAnnot_simpleAnnot_wString](
          ru
            .typeOf[AnnotationRefl_classAnnot_cc2]
            .typeSymbol
            .annotations
        ) should be (
          Some(
            AnnotationRefl_classAnnot_simpleAnnot_wString(
              param1 = "lököl"
            )))
      }
    }
  }
}
