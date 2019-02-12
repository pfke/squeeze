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
  ): Option[injectSize] = getAnnot[injectSize](in)

  def hasInjectLength (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[injectSize](in)

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

  def getWithFixedSize (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Option[withFixedSize] = getAnnot[withFixedSize](in)

  def getWithFixedSizeOr (
    in: List[ru.Annotation],
    default: Int
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Int = getAnnot[withFixedSize](in).matchTo(_.size, default)

  def hasWithFixedSize (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[withFixedSize](in)

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
