package de.pfke.squeeze.annots

import de.pfke.squeeze.annots.classAnnots.{alignBitfieldsBy, typeForIface}
import de.pfke.squeeze.annots.fieldAnnots.{asBitfield, injectSize, injectType, withFixedSize}

import scala.annotation.StaticAnnotation
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object AnnotationHelperIncludes
  extends AnnotationHelperIncludes

trait AnnotationHelperIncludes {
  implicit class FromAnnotationList (
    in: List[ru.Annotation]
  ) {
    implicit val classLoader: ClassLoader = getClass.getClassLoader

    def getAlignBitfieldsBy: Option[alignBitfieldsBy] = AnnotationHelper.getAlignBitfieldsBy(in)
    def hasAlignBitfieldsBy: Boolean = AnnotationHelper.hasAlignBitfieldsBy(in)

    def getAsBitfield: Option[asBitfield] = AnnotationHelper.getAsBitfield(in)
    def hasAsBitfield: Boolean = AnnotationHelper.hasAsBitfield(in)

    def getInjectLength: Option[injectSize] = AnnotationHelper.getInjectLength(in)
    def hasInjectLength: Boolean = AnnotationHelper.hasInjectLength(in)

    def getInjectType: Option[injectType] = AnnotationHelper.getInjectType(in)
    def hasInjectType: Boolean = AnnotationHelper.hasInjectType(in)

    def getTypeForIface: Option[typeForIface] = AnnotationHelper.getTypeForIface(in)
    def hasTypeForIface: Boolean = AnnotationHelper.hasTypeForIface(in)

    def getWithFixedSize: Option[withFixedSize] = AnnotationHelper.getWithFixedSize(in)
    def getWithFixedSizeOr (default: Int): Int = AnnotationHelper.getWithFixedSizeOr(in, default)
    def hasWithFixedSize: Boolean = AnnotationHelper.hasWithFixedSize(in)

    /**
      * Returns the wanted annotation
      */
    def getAnnot[A <: StaticAnnotation] (
      implicit
      classTag: ClassTag[A],
      typeTag: ru.TypeTag[A]
    ): Option[A] = AnnotationHelper.getAnnot[A](in)

    /**
      * Returns true, if the given field descr has the wanted annot
      */
    def hasAnnot[A <: StaticAnnotation] (
      implicit
      typeTag: ru.TypeTag[A]
    ): Boolean = AnnotationHelper.hasAnnot[A](in)
  }
}
