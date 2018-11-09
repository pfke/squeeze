package de.pfke.squeeze.annots

import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.refl.AnnotationRefl
import de.pfke.squeeze.annots.classAnnots.typeForIface

import scala.annotation.StaticAnnotation
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object AnnotationHelper {
  def getAlignBitfieldsBy (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Option[alignBitfieldsBy] = getAnnot[alignBitfieldsBy](in)

  def hasAlignBitfieldsBy (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[alignBitfieldsBy](in)

  def getAsBitfield (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Option[asBitfield] = getAnnot[asBitfield](in)

  def hasAsBitfield (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[asBitfield](in)

  def getInjectLength (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Option[injectLength] = getAnnot[injectLength](in)

  def hasInjectLength (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[injectLength](in)

  def getInjectType (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Option[injectType] = getAnnot[injectType](in)

  def hasInjectType (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[injectType](in)

  def getTypeForIface (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Option[typeForIface] = getAnnot[typeForIface](in)

  def hasTypeForIface (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[typeForIface](in)

  def getWithFixedWidth (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Option[withFixedWidth] = getAnnot[withFixedWidth](in)

  def getWithFixedWidthOr (
    in: List[ru.Annotation],
    default: Int
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Int = getAnnot[withFixedWidth](in).matchTo(_.size, default)

  def hasWithFixedWidth (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[withFixedWidth](in)

  def getWithFixedCount (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Option[withFixedCount] = getAnnot[withFixedCount](in)

  def getWithFixedCountOr (
    in: List[ru.Annotation],
    default: Int
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Int = getAnnot[withFixedCount](in).matchTo(_.count, default)

  def hasWithFixedCount (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[withFixedCount](in)

  def getWithFixedLength (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Option[withFixedLength] = getAnnot[withFixedLength](in)

  def getWithFixedLengthOr (
    in: List[ru.Annotation],
    default: Int
  ): Int = getAnnot[withFixedLength](in).matchTo(_.size, default)

  def hasWithFixedLength (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[withFixedLength](in)

  /**
    * Returns the wanted annotation
    */
  def getAnnot[A <: StaticAnnotation] (
    in: List[ru.Annotation]
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A],
    classLoader: ClassLoader = getClass.getClassLoader
  ): Option[A] = AnnotationRefl.create[A](in)

  /**
    * Returns true, if the given field descr has the wanted annot
    */
  def hasAnnot[A <: StaticAnnotation](
    in: List[ru.Annotation]
  ) (
    implicit
    typeTag: ru.TypeTag[A]
  ): Boolean = AnnotationRefl.contains[A](in)
}
