package de.pfke.squeeze.zlib.refl

import de.pfke.squeeze.annots._
import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.data.length.digital.DigitalLength

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
    val staticSize = guesso(tpe = GeneralRefl.getType(obj), annots = List.empty)

    // is there any dynamic size component (e.g a string w/o withFixedLength-annot, dyn. list, ...)
    val dynamicSize = guessoDynamicSize(obj)

    staticSize + dynamicSize
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

    val summedListSize = fields
      .filter(_.tpe <:< ru.typeOf[List[_]])
      .filterNot(_.annos.hasWithFixedCount)
      .foldLeft(DigitalLength.zero)((sum,i) => sum + calcListSize(i))

    summedStringLen.byte + summedListSize
  }

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
    val isWidth = annots.getWidth.matchToOption(_.size byte)
    val isWithFixedLength = annots.getWithFixedLength.matchToOption(_.size byte)

    isComplex
      .orElse(isBitfield)
      .orElse(isWidth)
      .orElse(isWithFixedLength)
      .orElse(_typeToSize.get(PrimitiveRefl.toScalaType(tpe)))
      .orElse(Some(0 byte))
      .get
  }

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

    FieldHelper
      .getFields(tpe)
      .foldLeft(DigitalLength.zero)((sum,i) => sum + guesso(tpe = i.tpe, annots = i.annos))
  }

  /**
    * Method to guess object size.
    * Returns 0 if the size is unknown
    */
  def guess[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Int = guess(tpe = typeTag.tpe)

  /**
    * Method to guess object size.
    * Returns 0 if the size is unknown
    *
    * @param thisFieldAnnots these are field annotations (given as hint by the upper class)
    */
  def guess (
    tpe: ru.Type,
    thisFieldAnnots: List[ru.Annotation] = List.empty,
    upperClassAnnots: List[ru.Annotation] = List.empty
  ): Int = {
    // collect class annotations, such as 'alignBitfieldBy'
//    val thisClassAnnots = tpe.typeSymbol.annotations

//    val alignBitfieldsBy = math.max(1, upperClassAnnots.getAlignBitfieldsBy.matchTo(_.bits, default = 0))
//    val bitFieldAnnotSizeOpt = thisFieldAnnots.getAsBitfield.matchToOption(i => ((i.bits + alignBitfieldsBy - 1) / alignBitfieldsBy) * alignBitfieldsBy / 8)
//    val stringAnnotSizeOpt = thisFieldAnnots.getWithFixedLength.matchToOption(_.bytes)

    PrimitiveRefl.toScalaType(tpe) match {
//      case t if t <:< ru.typeOf[Boolean] => SizeOf.Boolean
//      case t if t <:< ru.typeOf[Byte] => SizeOf.Byte
//      case t if t <:< ru.typeOf[Char] => SizeOf.Char
//      case t if t <:< ru.typeOf[Double] => SizeOf.Double
//      case t if t <:< ru.typeOf[Float] => SizeOf.Float
//      case t if t <:< ru.typeOf[Int] => SizeOf.Int
//      case t if t <:< ru.typeOf[Long] => SizeOf.Long
//      case t if t <:< ru.typeOf[Short] => SizeOf.Short
//
      case t if t <:< ru.typeOf[String] => thisFieldAnnots.getWithFixedLengthOr(default = 0) // evtl. gibts ne fixed length annot

      case t if t <:< ru.typeOf[List[_]] => thisFieldAnnots.getWithFixedCountOr(default = 0) * t.typeArgs.headOption.matchTo(guess(_), default = 0)

      case t if t.typeSymbol.isAbstract => 0
      case _ => guess(FieldHelper.getFields(tpe), upperClassAnnots = tpe.typeSymbol.annotations)
    }
  }

  /**
    * Guess and sum size of the given fields.
    * Can calc and align bitfields too.
    *
    * @param upperClassAnnots if no 'alignBitfieldsBy' is found the pure bit size is returned
    */
  def guess (
    fields: List[FieldDescr],
    upperClassAnnots: List[ru.Annotation]
  ): Int = {
    val alignBitfieldsBy = math.max(1, upperClassAnnots.getAlignBitfieldsBy.matchTo(_.bits, default = 0))

    def summarize(
      in: List[FieldDescr]
    ): Int = {
      if (in.flatMap(_.annos).hasAsBitfield) {
        val bits = in
          .map(_.annos.getAsBitfield.matchTo(_.bits, default = 0))
          .sum


        val res = (bits + alignBitfieldsBy - 1) / alignBitfieldsBy * alignBitfieldsBy / 8

        res
      } else {
        in.foldLeft(0)((sum,i) => sum + guess(i.tpe, upperClassAnnots = upperClassAnnots, thisFieldAnnots = i.annos))
      }
    }

    FieldHelper
      .groupByBitfields(fields, upperClassAnnots = upperClassAnnots)
      .foldLeft(0)((sum,i) => sum + summarize(i))
  }

  /**
    * Guess and sum size of the given fields.
    * Can calc and align bitfields too.
    *
    * @param upperClassAnnots if no 'alignBitfieldsBy' is found the pure bit size is returned
    */
  def guessBitsize (
    fields: List[FieldDescr],
    upperClassAnnots: List[ru.Annotation]
  ): Int = {
    // TODO: mit squeeze entkoppeln
    //    require(
    //      (fields.hasAsBitfield && !fields.hasOneWithoutAsBitfield) || (!fields.hasAsBitfield),
    //      s"got a mixed list decorated with 'asBitfield' and w/o (${fields.mkString(", ")})"
    //    )
    //
    //    if (fields.hasAsBitfield) {
    //      val bits = fields.foldLeft(0)((i, c) =>
    //        i + c.getAsBitfield.matchToException(
    //          _.bits,
    //          new SerializerBuildException(s"given field: ${c.name} does not have 'asBitfield' annot")
    //        )
    //      )
    //
    //      val alignTo = upperClassAnnots
    //        .getAlignBitfieldsBy
    //        .matchTo(_.bits, 1)
    //
    //      val alignedBits = if ((bits % alignTo) == 0) bits else bits + (alignTo - (bits % alignTo))
    //
    //      alignedBits
    //    } else {
    fields.foldLeft(0)((i, c) => i + guess(c.tpe, thisFieldAnnots = c.annos)) * 8
    //    }
  }
}
