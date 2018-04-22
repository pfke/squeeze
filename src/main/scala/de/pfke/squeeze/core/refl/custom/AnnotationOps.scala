package de.pfke.squeeze.core.refl.custom

import de.pfke.squeeze.annots.classAnnots.{alignBitfieldsBy, fromIfaceToType, fromVersion, toVersion}
import de.pfke.squeeze.annots.fields._
import de.pfke.squeeze.core.data._
import de.pfke.squeeze.core.refl.{generic => prg}

import scala.annotation.StaticAnnotation
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object AnnotationOps {
  /**
    * Methods for annot alignBitfieldsBy
    */
  def getAlignBitfieldsBy (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[alignBitfieldsBy] = instantiateAnnot[alignBitfieldsBy](in)

  def hasAlignBitfieldsBy (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[alignBitfieldsBy](in)

  /**
    * Methods for annot injectLength
    */
  def getFromIfaceToType (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[fromIfaceToType] = instantiateAnnot[fromIfaceToType](in)

  def hasFromIfaceToType (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[fromIfaceToType](in)

  /**
    * Methods for annot fromVersion
    */
  def getFromVersion (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[fromVersion] = instantiateAnnot[fromVersion](in)

  def hasFromVersion (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[fromVersion](in)

  /**
    * Methods for annot toVersion
    */
  def getToVersion (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[toVersion] = instantiateAnnot[toVersion](in)

  def hasToVersion (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[toVersion](in)


  /**
    * Methods for annot asBitfield
    */
  def getAsBitfield (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[asBitfield] = instantiateAnnot[asBitfield](in)

  def hasAsBitfield (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[asBitfield](in)

  /**
    * Methods for annot fixedLength
    */
  def getFixedLength (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[fixedLength] = instantiateAnnot[fixedLength](in)

  def getFixedLengthOr (
    in: List[ru.Annotation],
    default: Int
  ): Int = instantiateAnnot[fixedLength](in).execOrDefault(_.size, default)

  def hasFixedLength (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[fixedLength](in)

  /**
    * Methods for annot fixedListSize
    */
  def getFixedListSize (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[fixedListSize] = instantiateAnnot[fixedListSize](in)

  def getFixedListSizeOr (
    in: List[ru.Annotation],
    default: Int
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Int = instantiateAnnot[fixedListSize](in).execOrDefault(_.size, default)

  def hasFixedListSize (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[fixedListSize](in)

  /**
    * Methods for annot injectFieldType
    */
  def getInjectFieldType (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[injectFieldType] = instantiateAnnot[injectFieldType](in)

  def hasInjectFieldType (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[injectFieldType](in)

  /**
    * Methods for annot injectLength
    */
  def getInjectLength (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[injectLength] = instantiateAnnot[injectLength](in)

  def hasInjectLength (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[injectLength](in)

  /**
    * Methods for annot injectListSize
    */
  def getInjectListSize (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[injectListSize] = instantiateAnnot[injectListSize](in)

  def hasInjectListSize (
    in: List[ru.Annotation]
  ): Boolean = hasAnnot[injectListSize](in)


  /**
    * Returns the wanted annotation
    */
  def instantiateAnnot[A <: StaticAnnotation] (
    in: List[ru.Annotation]
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A],
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[A] = prg.AnnotationOps.instantiate[A](in)

  /**
    * Returns true, if the given field descr has the wanted annot
    */
  def hasAnnot[A <: StaticAnnotation](
    in: List[ru.Annotation]
  ) (
    implicit
    typeTag: ru.TypeTag[A]
  ): Boolean = prg.AnnotationOps.contains[A](in)
}

object AnnotationOpsIncludes
  extends AnnotationOpsIncludes

trait AnnotationOpsIncludes {
  implicit class AnnotationOpsIncludes_from_annotationList (
    in: List[ru.Annotation]
  ) {
    implicit val classLoader: ClassLoader = getClass.getClassLoader

    def getAlignBitfieldsBy: Option[alignBitfieldsBy] = AnnotationOps.getAlignBitfieldsBy(in)
    def hasAlignBitfieldsBy: Boolean = AnnotationOps.hasAlignBitfieldsBy(in)

    def getFromIfaceToType: Option[fromIfaceToType] = AnnotationOps.getFromIfaceToType(in)
    def hasFromIfaceToType: Boolean = AnnotationOps.hasFromIfaceToType(in)

    def getFromVersion: Option[fromVersion] = AnnotationOps.getFromVersion(in)
    def hasFromVersion: Boolean = AnnotationOps.hasFromVersion(in)

    def getToVersion: Option[toVersion] = AnnotationOps.getToVersion(in)
    def hasToVersion: Boolean = AnnotationOps.hasToVersion(in)

    def getAsBitfield: Option[asBitfield] = AnnotationOps.getAsBitfield(in)
    def hasAsBitfield: Boolean = AnnotationOps.hasAsBitfield(in)

    def getFixedLength: Option[fixedLength] = AnnotationOps.getFixedLength(in)
    def getFixedLengthOr (default: Int): Int = AnnotationOps.getFixedLengthOr(in, default)
    def hasFixedLength: Boolean = AnnotationOps.hasFixedLength(in)

    def getFixedListSize: Option[fixedListSize] = AnnotationOps.getFixedListSize(in)
    def getFixedListSizeOr (default: Int): Int = AnnotationOps.getFixedListSizeOr(in, default)
    def hasFixedListSize: Boolean = AnnotationOps.hasFixedListSize(in)

    def getInjectFieldType: Option[injectFieldType] = AnnotationOps.getInjectFieldType(in)
    def hasInjectFieldType: Boolean = AnnotationOps.hasInjectFieldType(in)

    def getInjectLength: Option[injectLength] = AnnotationOps.getInjectLength(in)
    def hasInjectLength: Boolean = AnnotationOps.hasInjectLength(in)

    def getInjectListSize: Option[injectListSize] = AnnotationOps.getInjectListSize(in)
    def hasInjectListSize: Boolean = AnnotationOps.hasInjectListSize(in)

    /**
      * Returns the wanted annotation
      */
    def instantiateAnnot[A <: StaticAnnotation] (
      implicit
      classTag: ClassTag[A],
      typeTag: ru.TypeTag[A]
    ): Option[A] = AnnotationOps.instantiateAnnot[A](in)

    /**
      * Returns true, if the given field descr has the wanted annot
      */
    def hasAnnot[A <: StaticAnnotation] (
      implicit
      typeTag: ru.TypeTag[A]
    ): Boolean = AnnotationOps.hasAnnot[A](in)
  }
}
