package de.pfke.squeeze.core.refl.custom

import de.pfke.squeeze.annots.fields._
import de.pfke.squeeze.core._
import de.pfke.squeeze.core.refl.generic.RichMethodParameter

import scala.annotation.StaticAnnotation
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

//case class FieldDescr(
//  methodParameter: MethodParameter,
//  annos: List[ru.Annotation]
//) {
//  def name: String = methodParameter.name
//  def tpe: ru.Type = methodParameter.typeSignature
//}

object FieldDescrIncludes
  extends FieldDescrIncludes

trait FieldDescrIncludes {
  implicit class fromFieldDescr(
    in: RichMethodParameter
  ) {
    def getAsBitfield: Option[asBitfield] = getAnnot[asBitfield]
    def hasAsBitfield: Boolean = hasAnnot[asBitfield]

    def getInjectLength: Option[injectLength] = getAnnot[injectLength]
    def hasInjectLength: Boolean = hasAnnot[injectLength]

    def getInjectType: Option[injectFieldType] = getAnnot[injectFieldType]
    def hasInjectType: Boolean = hasAnnot[injectFieldType]

    def getWithFixedCount: Option[fixedListSize] = getAnnot[fixedListSize]
    def getWithFixedCountOr(default: Int): Int = getAnnot[fixedListSize].execOrDefault(_.size, default)
    def hasWithFixedCount: Boolean = hasAnnot[fixedListSize]

    def getWithFixedLength: Option[fixedLength] = getAnnot[fixedLength]
    def getWithFixedLengthOr(default: Int): Int = getAnnot[fixedLength].execOrDefault(_.size, default)
    def hasWithFixedLength: Boolean = hasAnnot[fixedLength]

    /**
      * Returns the wanted annotation
      */
    def getAnnot[A <: StaticAnnotation](
      implicit
      classTag: ClassTag[A],
      typeTag: ru.TypeTag[A]
    ): Option[A] = in.annotations.getAnnot[A]

    /**
      * Returns true, if the given field descr has the wanted annot
      */
    def hasAnnot[A <: StaticAnnotation](
      implicit
      typeTag: ru.TypeTag[A]
    ): Boolean = in.annotations.hasAnnot[A]
  }

  implicit class fromFieldDescrList(
    in: List[RichMethodParameter]
  ) {
    def getAnnot[A <: StaticAnnotation](
      implicit
      classTag: ClassTag[A],
      typeTag: ru.TypeTag[A]
    ): List[(A, RichMethodParameter)] = {
      in
        .map { i => i.getAnnot[A].execAndLift( ii => (ii, i)) } // wir wollen Some(injectLength, FieldDescr)
        .filter { _.nonEmpty }
        .map { _.get }
    }

    def hasOneWithoutAsBitfield: Boolean = in.exists(!_.hasAsBitfield)
    def hasAsBitfield: Boolean = in.exists(_.hasAsBitfield)
    def hasInjectType: Boolean = in.exists(_.hasInjectType)

    // get injectCount annot for this fields matching the passed name
    def getInjectCountAnnot(
      targetFieldName: String
    ): Option[(injectListSize, RichMethodParameter)] = {
      getAnnot[injectListSize]
        .find { _._1.fromField == targetFieldName }
        .execAndLift { i => (i._1, i._2) }
    }

    // get injectLength annot for this fields matching the passed name
    def getInjectLengthAnnot(
      targetFieldName: String
    ): Option[(injectLength, RichMethodParameter)] = {
      getAnnot[injectLength]
        .find { _._1.fromField == targetFieldName }
        .execAndLift { i => (i._1, i._2) }
    }

    // get injectType annot for this fields matching the passed name
    def getInjectTypeAnnot(
      targetFieldName: String
    ): Option[(injectFieldType, RichMethodParameter)] = {
      getAnnot[injectFieldType]
        .find { _._1.fromField == targetFieldName }
        .execAndLift { i => (i._1, i._2) }
    }

    // get withFixedLength annot for this fields
    def getWithFixedLengthAnnot: Option[(fixedLength, RichMethodParameter)] = {
      getAnnot[fixedLength]
        .headOption
    }
  }
}
