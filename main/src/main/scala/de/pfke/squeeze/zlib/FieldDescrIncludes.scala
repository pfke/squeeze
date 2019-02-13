package de.pfke.squeeze.zlib

import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.refl.{FieldDescr, FieldHelper}
import de.pfke.squeeze.zlib.refl.GeneralReflIncludes
import de.pfke.squeeze.annots.{asBitfield, injectSize, injectType, withFixedSize}
import de.pfke.squeeze.annots.AnnotationHelperIncludes._

import scala.annotation.StaticAnnotation
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object FieldDescrIncludes
  extends FieldDescrIncludes

trait FieldDescrIncludes {
  implicit class fromFieldDescr(
    in: FieldDescr
  ) extends GeneralReflIncludes.OpsFromRUType(in.tpe) {
    def getAsBitfield: Option[asBitfield] = getAnnot[asBitfield]
    def hasAsBitfield: Boolean = hasAnnot[asBitfield]

    def getInjectSize: Option[injectSize] = getAnnot[injectSize]
    def hasInjectLength: Boolean = hasAnnot[injectSize]

    def getInjectType: Option[injectType] = getAnnot[injectType]
    def hasInjectType: Boolean = hasAnnot[injectType]

    def getWithFixedSize: Option[withFixedSize] = getAnnot[withFixedSize]
    def getWithFixedSizeOr(default: Int): Int = getAnnot[withFixedSize].matchTo(_.size, default)
    def hasWithFixedSize: Boolean = hasAnnot[withFixedSize]

    /**
      * Returns the wanted annotation
      */
    def getAnnot[A <: StaticAnnotation](
      implicit
      classTag: ClassTag[A],
      typeTag: ru.TypeTag[A]
    ): Option[A] = in.annos.getAnnot[A]

    /**
      * Group all field of the passed class by bitfields and non-bitfields
      */
    def groupByBitfields: List[List[FieldDescr]] = {
      val r1 = FieldHelper.getFields(tpe = in.tpe)

      val sss = new ArrayBuffer[ArrayBuffer[FieldDescr]]()
      var isBitfieldRun = false
      r1.foreach { i =>
        if (i.annos.hasAnnot[asBitfield]) {
          if (!isBitfieldRun) {
            sss += new ArrayBuffer[FieldDescr]()
            isBitfieldRun = true
          }

          sss.last += i
        } else {
          if (isBitfieldRun) {
            sss += new ArrayBuffer[FieldDescr]()
          }

          sss.last += i
          isBitfieldRun = false
        }
      }

      sss.map(_.toList).toList
    }

    /**
      * Returns true, if the given field descr has the wanted annot
      */
    def hasAnnot[A <: StaticAnnotation](
      implicit
      typeTag: ru.TypeTag[A]
    ): Boolean = in.annos.hasAnnot[A]
  }

  implicit class fromFieldDescrList(
    in: List[FieldDescr]
  ) {
    def getAnnot[A <: StaticAnnotation](
      implicit
      classTag: ClassTag[A],
      typeTag: ru.TypeTag[A]
    ): List[(A, FieldDescr)] = {
      in
        .map { i => i.getAnnot[A].matchToOption( ii => (ii, i)) } // wir wollen Some(injectLength, FieldDescr)
        .filter { _.nonEmpty }
        .map { _.get }
    }

    def hasOneWithoutAsBitfield: Boolean = in.exists(!_.hasAsBitfield)
    def hasAsBitfield: Boolean = in.exists(_.hasAsBitfield)
    def hasInjectType: Boolean = in.exists(_.hasInjectType)

    // get injectCount annot for this fields matching the passed name
    def getInjectCountAnnot(
      targetFieldName: String
    ): Option[(injectSize, FieldDescr)] = {
      getAnnot[injectSize]
        .find { _._1.from == targetFieldName }
        .matchToOption { i => (i._1, i._2) }
    }

    // get injectLength annot for this fields matching the passed name
    def getInjectSizeAnnot(
      targetFieldName: String
    ): Option[(injectSize, FieldDescr)] = {
      getAnnot[injectSize]
        .find { _._1.from == targetFieldName }
        .matchToOption { i => (i._1, i._2) }
    }

    // get injectType annot for this fields matching the passed name
    def getInjectTypeAnnot(
      targetFieldName: String
    ): Option[(injectType, FieldDescr)] = {
      getAnnot[injectType]
        .find { _._1.fromField == targetFieldName }
        .matchToOption { i => (i._1, i._2) }
    }

    // get withFixedLength annot for this fields
    def getWithFixedSizeAnnot: Option[(withFixedSize, FieldDescr)] = {
      getAnnot[withFixedSize]
        .headOption
    }
  }
}
