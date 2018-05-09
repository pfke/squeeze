package de.pfke.squeeze.core.refl.custom

import de.pfke.squeeze.annots.classAnnots.{alignBitfieldsBy, fromIfaceToType, fromVersion, toVersion}
import de.pfke.squeeze.annots.fields._
import de.pfke.squeeze.core._
import de.pfke.squeeze.core.refl.generic.AnnotationOps

import scala.reflect.runtime.{universe => ru}

object CustomAnnotationOps {
  /**
    * Methods for annot alignBitfieldsBy
    */
  def getAlignBitfieldsBy (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[alignBitfieldsBy] = AnnotationOps.instantiate[alignBitfieldsBy](in)

  def hasAlignBitfieldsBy (
    in: List[ru.Annotation]
  ): Boolean = AnnotationOps.has[alignBitfieldsBy](in)

  /**
    * Methods for annot injectLength
    */
  def getFromIfaceToType (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[fromIfaceToType] = AnnotationOps.instantiate[fromIfaceToType](in)

  def hasFromIfaceToType (
    in: List[ru.Annotation]
  ): Boolean = AnnotationOps.has[fromIfaceToType](in)

  /**
    * Methods for annot fromVersion
    */
  def getFromVersion (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[fromVersion] = AnnotationOps.instantiate[fromVersion](in)

  def hasFromVersion (
    in: List[ru.Annotation]
  ): Boolean = AnnotationOps.has[fromVersion](in)

  /**
    * Methods for annot toVersion
    */
  def getToVersion (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[toVersion] = AnnotationOps.instantiate[toVersion](in)

  def hasToVersion (
    in: List[ru.Annotation]
  ): Boolean = AnnotationOps.has[toVersion](in)


  /**
    * Methods for annot asBitfield
    */
  def getAsBitfield (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[asBitfield] = AnnotationOps.instantiate[asBitfield](in)

  def hasAsBitfield (
    in: List[ru.Annotation]
  ): Boolean = AnnotationOps.has[asBitfield](in)

  /**
    * Methods for annot fixedLength
    */
  def getFixedLength (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[fixedLength] = AnnotationOps.instantiate[fixedLength](in)

  def getFixedLengthOr (
    in: List[ru.Annotation],
    default: Int
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Int = AnnotationOps.instantiate[fixedLength](in).execOrDefault(_.size, default)

  def hasFixedLength (
    in: List[ru.Annotation]
  ): Boolean = AnnotationOps.has[fixedLength](in)

  /**
    * Methods for annot fixedListSize
    */
  def getFixedListSize (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[fixedListSize] = AnnotationOps.instantiate[fixedListSize](in)

  def getFixedListSizeOr (
    in: List[ru.Annotation],
    default: Int
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Int = AnnotationOps.instantiate[fixedListSize](in).execOrDefault(_.size, default)

  def hasFixedListSize (
    in: List[ru.Annotation]
  ): Boolean = AnnotationOps.has[fixedListSize](in)

  /**
    * Methods for annot injectFieldType
    */
  def getInjectFieldType (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[injectFieldType] = AnnotationOps.instantiate[injectFieldType](in)

  def hasInjectFieldType (
    in: List[ru.Annotation]
  ): Boolean = AnnotationOps.has[injectFieldType](in)

  /**
    * Methods for annot injectLength
    */
  def getInjectLength (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[injectLength] = AnnotationOps.instantiate[injectLength](in)

  def hasInjectLength (
    in: List[ru.Annotation]
  ): Boolean = AnnotationOps.has[injectLength](in)

  /**
    * Methods for annot injectListSize
    */
  def getInjectListSize (
    in: List[ru.Annotation]
  ) (
    implicit
    classLoader: ClassLoader = in.getClass.getClassLoader
  ): Option[injectListSize] = AnnotationOps.instantiate[injectListSize](in)

  def hasInjectListSize (
    in: List[ru.Annotation]
  ): Boolean = AnnotationOps.has[injectListSize](in)
}

object CustomAnnotationOpsIncludes
  extends CustomAnnotationOpsIncludes

trait CustomAnnotationOpsIncludes {
  implicit class CustomAnnotationOpsIncludes_from_annotationList (
    in: List[ru.Annotation]
  ) {
    implicit val classLoader: ClassLoader = getClass.getClassLoader

    def getAlignBitfieldsBy: Option[alignBitfieldsBy] = CustomAnnotationOps.getAlignBitfieldsBy(in)
    def hasAlignBitfieldsBy: Boolean = CustomAnnotationOps.hasAlignBitfieldsBy(in)

    def getFromIfaceToType: Option[fromIfaceToType] = CustomAnnotationOps.getFromIfaceToType(in)
    def hasFromIfaceToType: Boolean = CustomAnnotationOps.hasFromIfaceToType(in)

    def getFromVersion: Option[fromVersion] = CustomAnnotationOps.getFromVersion(in)
    def hasFromVersion: Boolean = CustomAnnotationOps.hasFromVersion(in)

    def getToVersion: Option[toVersion] = CustomAnnotationOps.getToVersion(in)
    def hasToVersion: Boolean = CustomAnnotationOps.hasToVersion(in)

    def getAsBitfield: Option[asBitfield] = CustomAnnotationOps.getAsBitfield(in)
    def hasAsBitfield: Boolean = CustomAnnotationOps.hasAsBitfield(in)

    def getFixedLength: Option[fixedLength] = CustomAnnotationOps.getFixedLength(in)
    def getFixedLengthOr (default: Int): Int = CustomAnnotationOps.getFixedLengthOr(in, default)
    def hasFixedLength: Boolean = CustomAnnotationOps.hasFixedLength(in)

    def getFixedListSize: Option[fixedListSize] = CustomAnnotationOps.getFixedListSize(in)
    def getFixedListSizeOr (default: Int): Int = CustomAnnotationOps.getFixedListSizeOr(in, default)
    def hasFixedListSize: Boolean = CustomAnnotationOps.hasFixedListSize(in)

    def getInjectFieldType: Option[injectFieldType] = CustomAnnotationOps.getInjectFieldType(in)
    def hasInjectFieldType: Boolean = CustomAnnotationOps.hasInjectFieldType(in)

    def getInjectLength: Option[injectLength] = CustomAnnotationOps.getInjectLength(in)
    def hasInjectLength: Boolean = CustomAnnotationOps.hasInjectLength(in)

    def getInjectListSize: Option[injectListSize] = CustomAnnotationOps.getInjectListSize(in)
    def hasInjectListSize: Boolean = CustomAnnotationOps.hasInjectListSize(in)
  }
}
