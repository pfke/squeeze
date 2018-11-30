package de.pfke.squeeze.zlib.refl

import de.pfke.squeeze.annots._
import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.data.length.digital.{BitLength, ByteLength, DigitalLength}

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

/**
  * Return the byte sizes of the primitives.
  *
  * Data Type     Definition
  *
  * Boolean       true or false
  *
  * Byte          8-bit signed two's complement integer (-2^7 to 2^7-1, inclusive)
  * Short         16-bit signed two's complement integer (-2^15 to 2^15-1, inclusive)
  * Int           32-bit two's complement integer (-2^31 to 2^31-1, inclusive)
  * Long          64-bit two's complement integer (-2^63 to 2^63-1, inclusive)
  *
  * Float         32-bit IEEE 754 single-precision float
  * Double        64-bit IEEE 754 double-precision float
  *
  * Char          16-bit unsigned Unicode character (0 to 2 pot 16-1, inclusive)
  * String        a sequence of Chars
  **/
object SizeOf {
  private val _typeToSize = Map[ru.Type, DigitalLength](
    ru.typeOf[Boolean] -> 1.byte,
    ru.typeOf[Byte] -> 1.byte,
    ru.typeOf[Short] -> 2.byte,
    ru.typeOf[Int] -> 4.byte,
    ru.typeOf[Long] -> 8.byte,
    ru.typeOf[Float] -> 4.byte,
    ru.typeOf[Double] -> 8.byte,
    ru.typeOf[Char] -> 2.byte,
  )

  def guesso[A] (
    obj: A
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): DigitalLength = {
    val staticSize = guesso(tpe = GeneralRefl.typeOf(obj), annots = List.empty)

    // is there any dynamic size component (e.g a string w/o withFixedLength-annot, dyn. list, ...)
    val dynamicSize = guessoDynamicSize(obj)

    staticSize + dynamicSize
  }

  def guesso[A] () (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): DigitalLength = guesso[A](annots = if (GeneralRefl.isComplexType(typeTag.tpe)) RichClassMirror[A]().annotations else List.empty)

  def guesso[A] (
    annots: List[ru.Annotation] = List.empty
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): DigitalLength = guesso(tpe = typeTag.tpe, annots = annots)

  def guesso (
    tpe: ru.Type,
    annots: List[ru.Annotation]
  ): DigitalLength = {
    val isComplex = tpe match {
      case t if GeneralRefl.isComplexType(t) => Some(guessoComplex(tpe = t, annots = annots))

      case _ => None
    }
    val isBitfield = annots.getAsBitfield.matchToOption(_.bits bit)
    val isWithFixedWidth = annots.getWithFixedWidth.matchToOption(_.size byte)
    val isWithFixedLength = annots.getWithFixedLength.matchToOption(_.size byte)
    val isWithFixedCount = annots.getWithFixedCount.matchToOption(i => tpe.typeArgs.foldLeft(DigitalLength.zero)((sum,i) => sum + guesso(tpe = i, List.empty)) * i.count)

    isComplex
      .orElse(isBitfield)
      .orElse(isWithFixedWidth)
      .orElse(isWithFixedLength)
      .orElse(isWithFixedCount)
      .orElse(_typeToSize.get(PrimitiveRefl.toScalaType(tpe)))
      .orElse(Some(0 byte))
      .get
  }

  def guesso (
    fields: List[FieldDescr]
  ): DigitalLength = fields.foldLeft(DigitalLength.zero)((sum,i) => sum + guesso(tpe = i.tpe, annots = i.annos))

  def guessoComplex[A] (
    annots: List[ru.Annotation] = List.empty
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): DigitalLength = guessoComplex(tpe = typeTag.tpe, annots = annots)

  def guessoComplex (
    tpe: ru.Type,
    annots: List[ru.Annotation]
  ): DigitalLength = {
    require(GeneralRefl.isComplexType(tpe), s"pass type is no complex. Got $tpe")

    val bitAlignment = annots.getAlignBitfieldsBy.matchTo(_.bits, 32).bits

    case class DigiLenSum(bytes: DigitalLength = DigitalLength.zero, bits: BitLength = BitLength.zero)
    def sumThis(sum: DigiLenSum, i: DigitalLength): DigiLenSum = {
      i match {
        case t: BitLength if sum.bits == BitLength.zero  => DigiLenSum(sum.bytes + bitAlignment, t)
        case t: BitLength if sum.bits + t > bitAlignment => DigiLenSum(sum.bytes + bitAlignment, t)
        case t: BitLength                                => DigiLenSum(sum.bytes, sum.bits + t)

        case t: ByteLength if sum.bits > bitAlignment    => DigiLenSum(sum.bytes + bitAlignment + t)
        case t                                           => DigiLenSum(sum.bytes + t)
      }
    }

    FieldHelper
      .getFields(tpe)
      .map(i => guesso(tpe = i.tpe, annots = i.annos))
      .foldLeft(DigiLenSum()) (sumThis)
      .map(i => i.bytes + (if (i.bits > bitAlignment) bitAlignment else DigitalLength.zero))
      .get
  }

  def guessoDynamicSize[A](
    obj: A
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): DigitalLength = {
    val richInstanceMirror = RichInstanceMirror(obj)

    val fields = FieldHelper.getFields[A]()

    // filter for strings w/o withFixedLength annot
    val summedStringLen = fields
      .filter(_.tpe =:= ru.typeOf[String])
      .filterNot(_.annos.hasWithFixedLength)
      .map(i => richInstanceMirror.getFieldValue[String](fieldName = i.name))
      .foldLeft(0)((sum,i) => sum + i.length)

    // filter for lists w/o withFixedCount annot
    def calcListSize(in: FieldDescr): DigitalLength = {
      require(in.tpe.typeArgs.nonEmpty, s"want me to calc a list size, but given type does not have type args: $in")

      val sizeOfOneListElement = in
        .tpe
        .typeArgs
        .foldLeft(DigitalLength.zero)((sum,i) => sum + guesso(tpe = i, List.empty))

      val listSize = richInstanceMirror
        .getFieldValue[List[_]](fieldName = in.name)
        .size

      sizeOfOneListElement * listSize
    }
    def calcStaticListSize(in: FieldDescr): DigitalLength = {
      require(in.tpe.typeArgs.nonEmpty, s"want me to calc a list size, but given type does not have type args: $in")

      val sizeOfOneListElement = in
        .tpe
        .typeArgs
        .foldLeft(DigitalLength.zero)((sum,i) => sum + guesso(tpe = i, List.empty))

      val listSize = in.annos.getWithFixedCount.matchTo(_.count, default = 0)

      sizeOfOneListElement * listSize
    }

    val summedListSize = fields
      .filter(_.tpe <:< ru.typeOf[List[_]])
      .filterNot(_.annos.hasWithFixedCount)
      .foldLeft(DigitalLength.zero)((sum,i) => sum + calcListSize(i))

    // check that withFixedCount annotated lists have count elements
    case class Dingens(field: FieldDescr, staticSize: DigitalLength, dynamicSize: DigitalLength)
    val summedStaticFixedCountListSize = fields
      .filter(_.tpe <:< ru.typeOf[List[_]])
      .filter(_.annos.hasWithFixedCount)
      .map(i => Dingens(i, calcStaticListSize(i), calcListSize(i)))
    require(
      summedStaticFixedCountListSize.foldLeft(DigitalLength.zero)((sum,i) => sum + i.dynamicSize) == summedStaticFixedCountListSize.foldLeft(DigitalLength.zero)((sum,i) => sum + i.staticSize),
      s"found a list annotated w/ withFixedCount but actual list size is different (${summedStaticFixedCountListSize.map(i => s"${i.field.name}: withFixedCount = ${i.staticSize} -> actually = ${i.dynamicSize}").mkString(", ")})"
    )

    summedStringLen.byte + summedListSize
  }
}
