package de.pfke.squeeze.core.refl.custom

import de.pfke.squeeze.annots.fields._
import de.pfke.squeeze.core._
import de.pfke.squeeze.core.refl._
import de.pfke.squeeze.core.refl.generic.{ClassOps, RichMethod, RichMethodParameter}

import scala.collection.mutable.ArrayBuffer
import scala.reflect.runtime.{universe => ru}

object CustomRichMethodParameterOps {
  def getAsBitfield (
    in: RichMethodParameter
  ) (
    implicit
    classLoader: ClassLoader = ClassOps.defaultClassLoader
  ): Option[asBitfield] = generic.RichMethodParameterOps.getAnnot[asBitfield](in)
  def hasAsBitfield (in: RichMethodParameter): Boolean = generic.RichMethodParameterOps.hasAnnot[asBitfield](in)

  def getInjectLength (
    in: RichMethodParameter
  ) (
    implicit
    classLoader: ClassLoader = ClassOps.defaultClassLoader
  ): Option[injectLength] = generic.RichMethodParameterOps.getAnnot[injectLength](in)
  def hasInjectLength (in: RichMethodParameter): Boolean = generic.RichMethodParameterOps.hasAnnot[injectLength](in)

  def getInjectType (
    in: RichMethodParameter
  ) (
    implicit
    classLoader: ClassLoader = ClassOps.defaultClassLoader
  ): Option[injectFieldType] = generic.RichMethodParameterOps.getAnnot[injectFieldType](in)
  def hasInjectType (in: RichMethodParameter): Boolean = generic.RichMethodParameterOps.hasAnnot[injectFieldType](in)

  def getWithFixedCount (
    in: RichMethodParameter
  ) (
    implicit
    classLoader: ClassLoader = ClassOps.defaultClassLoader
  ): Option[fixedListSize] = generic.RichMethodParameterOps.getAnnot[fixedListSize](in)
  def getWithFixedCountOr (
    in: RichMethodParameter,
    default: Int
  ) (
    implicit
    classLoader: ClassLoader = ClassOps.defaultClassLoader
  ): Int = generic.RichMethodParameterOps.getAnnot[fixedListSize](in).execOrDefault(_.size, default)
  def hasWithFixedCount (in: RichMethodParameter): Boolean = generic.RichMethodParameterOps.hasAnnot[fixedListSize](in)

  def getWithFixedLength (
    in: RichMethodParameter
  ) (
    implicit
    classLoader: ClassLoader = ClassOps.defaultClassLoader
  ): Option[fixedLength] = generic.RichMethodParameterOps.getAnnot[fixedLength](in)
  def getWithFixedLengthOr (
    in: RichMethodParameter,
    default: Int
  ) (
    implicit
    classLoader: ClassLoader = ClassOps.defaultClassLoader
  ): Int = generic.RichMethodParameterOps.getAnnot[fixedLength](in).execOrDefault(_.size, default)
  def hasWithFixedLength (in: RichMethodParameter): Boolean = generic.RichMethodParameterOps.hasAnnot[fixedLength](in)

  def hasOneWithoutAsBitfield (in: List[RichMethodParameter]): Boolean = in.exists { i => !hasAsBitfield(i) }
  def hasAsBitfield (in: List[RichMethodParameter]): Boolean = in.exists(hasAsBitfield)
  def hasInjectType (in: List[RichMethodParameter]): Boolean = in.exists(hasAsBitfield)

  // get injectCount annot for this fields matching the passed name
  def getInjectCountAnnot (
    in: List[RichMethodParameter],
    targetFieldName: String
  ) (
    implicit
    classLoader: ClassLoader = ClassOps.defaultClassLoader
  ): Option[(injectListSize, RichMethodParameter)] = {
    generic.RichMethodParameterOps
      .getAnnot[injectListSize](in)
      .find { _._1.fromField == targetFieldName }
      .execAndLift { i => (i._1, i._2) }
  }

  // get injectLength annot for this fields matching the passed name
  def getInjectLengthAnnot(
    in: List[RichMethodParameter],
    targetFieldName: String
  ) (
    implicit
    classLoader: ClassLoader = ClassOps.defaultClassLoader
  ): Option[(injectLength, RichMethodParameter)] = {
    generic.RichMethodParameterOps
      .getAnnot[injectLength](in)
      .find { _._1.fromField == targetFieldName }
      .execAndLift { i => (i._1, i._2) }
  }

  // get injectType annot for this fields matching the passed name
  def getInjectTypeAnnot(
    in: List[RichMethodParameter],
    targetFieldName: String
  ) (
    implicit
    classLoader: ClassLoader = ClassOps.defaultClassLoader
  ): Option[(injectFieldType, RichMethodParameter)] = {
    generic.RichMethodParameterOps
      .getAnnot[injectFieldType](in)
      .find { _._1.fromField == targetFieldName }
      .execAndLift { i => (i._1, i._2) }
  }

  // get withFixedLength annot for this fields
  def getWithFixedLengthAnnot (
    in: List[RichMethodParameter],
  ) (
    implicit
    classLoader: ClassLoader = ClassOps.defaultClassLoader
  ): Option[(fixedLength, RichMethodParameter)] = {
    generic.RichMethodParameterOps
      .getAnnot[fixedLength](in)
      .headOption
  }

  /**
    * Group all field of the passed class by bitfields and non-bitfields
    */
  def groupByBitfields (
    tpe: ru.Type
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): List[List[RichMethodParameter]] = {
    RichMethod(tpe = tpe, methodName = RichMethod.TERMNAME_CTOR)
      .headOption match {
      case Some(x) => groupByBitfields(parameters = x.parameter)
      case None => throw new IllegalArgumentException(s"no ctor found for type $tpe")
    }
  }

  /**
    * Group all field of the passed class by bitfields and non-bitfields
    */
  def groupByBitfields (
    parameters: List[RichMethodParameter]
  ): List[List[RichMethodParameter]] = {
    groupByBitfields_start(parameters)
      .groupBy(_._1).toList.sortBy(_._1)
      .map(_._2.map(_._2))
  }

  private def groupByBitfields_start (
    parameters: List[RichMethodParameter]
  ): List[(Int, RichMethodParameter)] = {
    parameters match {
      case i :: rest if CustomAnnotationOps.hasAsBitfield(i.annotations) => List((0, i)) ++ groupByBitfields_bitfield(0, rest)
      case i :: rest                                                     => List((0, i)) ++ groupByBitfields_nonBitfield(0, rest)
      case Nil => List.empty
    }
  }

  private def groupByBitfields_bitfield (
    idx: Int,
    parameters: List[RichMethodParameter]
  ): List[(Int, RichMethodParameter)] = {
    parameters match {
      case i :: rest if CustomAnnotationOps.hasAsBitfield(i.annotations) => List((idx, i)) ++ groupByBitfields_bitfield(idx, rest)
      case i :: rest                                                     => List((idx + 1, i)) ++ groupByBitfields_nonBitfield(idx + 1, rest)
      case Nil => List.empty
    }
  }

  private def groupByBitfields_nonBitfield (
    idx: Int,
    parameters: List[RichMethodParameter]
  ): List[(Int, RichMethodParameter)] = {
    parameters match {
      case i :: rest if CustomAnnotationOps.hasAsBitfield(i.annotations) => List((idx + 1, i)) ++ groupByBitfields_bitfield(idx + 1, rest)
      case i :: rest                                                     => List((idx, i)) ++ groupByBitfields_nonBitfield(idx, rest)
      case Nil => List.empty
    }
  }

  private def add (
    in: ArrayBuffer[ArrayBuffer[RichMethodParameter]],
    toAdd: RichMethodParameter
  ): ArrayBuffer[ArrayBuffer[RichMethodParameter]] = {
    in.last += toAdd

    in
  }

  private def add_n_new (
    in: ArrayBuffer[ArrayBuffer[RichMethodParameter]],
    toAdd: RichMethodParameter
  ): ArrayBuffer[ArrayBuffer[RichMethodParameter]] = {
    in.last += toAdd
    in += new ArrayBuffer[RichMethodParameter]()

    in
  }
}

object CustomRichMethodParameterOpsIncludes
  extends CustomRichMethodParameterOpsIncludes

trait CustomRichMethodParameterOpsIncludes {
  implicit class CustomRichMethodParameterOpsIncludes_from_singleParam (
    in: RichMethodParameter
  ) {
    def getAsBitfield: Option[asBitfield] = CustomRichMethodParameterOps.getAsBitfield(in)
    def hasAsBitfield: Boolean = CustomRichMethodParameterOps.hasAsBitfield(in)

    def getInjectLength: Option[injectLength] = CustomRichMethodParameterOps.getInjectLength(in)
    def hasInjectLength: Boolean = CustomRichMethodParameterOps.hasInjectLength(in)

    def getInjectType: Option[injectFieldType] = CustomRichMethodParameterOps.getInjectType(in)
    def hasInjectType: Boolean = CustomRichMethodParameterOps.hasInjectType(in)

    def getWithFixedCount: Option[fixedListSize] = CustomRichMethodParameterOps.getWithFixedCount(in)
    def getWithFixedCountOr (default: Int): Int = CustomRichMethodParameterOps.getWithFixedCountOr(in, default)
    def hasWithFixedCount: Boolean = CustomRichMethodParameterOps.hasWithFixedCount(in)

    def getWithFixedLength: Option[fixedLength] = CustomRichMethodParameterOps.getWithFixedLength(in)
    def getWithFixedLengthOr (default: Int): Int = CustomRichMethodParameterOps.getWithFixedLengthOr(in, default)
    def hasWithFixedLength: Boolean = CustomRichMethodParameterOps.hasWithFixedLength(in)
  }

  implicit class CustomRichMethodParameterOpsIncludes_from_paramList (
    in: List[RichMethodParameter]
  ) {
    def hasOneWithoutAsBitfield: Boolean = CustomRichMethodParameterOps.hasOneWithoutAsBitfield(in)
    def hasAsBitfield: Boolean = CustomRichMethodParameterOps.hasAsBitfield(in)
    def hasInjectType: Boolean = CustomRichMethodParameterOps.hasInjectType(in)

    def getInjectCountAnnot (targetFieldName: String): Option[(injectListSize, RichMethodParameter)] = CustomRichMethodParameterOps.getInjectCountAnnot(in, targetFieldName)
    def getInjectLengthAnnot (targetFieldName: String): Option[(injectLength, RichMethodParameter)] = CustomRichMethodParameterOps.getInjectLengthAnnot(in, targetFieldName)
    def getInjectTypeAnnot (targetFieldName: String): Option[(injectFieldType, RichMethodParameter)] = CustomRichMethodParameterOps.getInjectTypeAnnot(in, targetFieldName)

    def getWithFixedLengthAnnot: Option[(fixedLength, RichMethodParameter)] = CustomRichMethodParameterOps.getWithFixedLengthAnnot(in)
    def groupByBitfields: List[List[RichMethodParameter]] = CustomRichMethodParameterOps.groupByBitfields(in)
  }
}
