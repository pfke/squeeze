package de.pfke.squeeze.annots

import de.pfke.squeeze.annots.classAnnots.typeForIface

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

    def getInjectLength: Option[injectLength] = AnnotationHelper.getInjectLength(in)
    def hasInjectLength: Boolean = AnnotationHelper.hasInjectLength(in)

    def getInjectType: Option[injectType] = AnnotationHelper.getInjectType(in)
    def hasInjectType: Boolean = AnnotationHelper.hasInjectType(in)

    def getTypeForIface: Option[typeForIface] = AnnotationHelper.getTypeForIface(in)
    def hasTypeForIface: Boolean = AnnotationHelper.hasTypeForIface(in)

    def getWithFixedWidth: Option[withFixedWidth] = AnnotationHelper.getWithFixedWidth(in)
    def getWithFixedWidthOr (default: Int): Int = AnnotationHelper.getWithFixedWidthOr(in, default)
    def hasWithFixedWidth: Boolean = AnnotationHelper.hasWithFixedWidth(in)

    def getWithFixedCount: Option[withFixedCount] = AnnotationHelper.getWithFixedCount(in)
    def getWithFixedCountOr (default: Int): Int = AnnotationHelper.getWithFixedCountOr(in, default)
    def hasWithFixedCount: Boolean = AnnotationHelper.hasWithFixedCount(in)

    def getWithFixedLength: Option[withFixedLength] = AnnotationHelper.getWithFixedLength(in)
    def getWithFixedLengthOr (default: Int): Int = AnnotationHelper.getWithFixedLengthOr(in, default)
    def hasWithFixedLength: Boolean = AnnotationHelper.hasWithFixedLength(in)

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
