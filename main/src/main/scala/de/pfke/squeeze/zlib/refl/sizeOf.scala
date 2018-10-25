package de.pfke.squeeze.zlib.refl

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
object sizeOf {
  val Boolean = 1

  val Byte = 1
  val Short = 2
  val Int = 4
  val Long = 8

  val Float = 4
  val Double = 8

  val Char = 2

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
    thisFieldAnnots: List[ru.Annotation] = List.empty
  ): Int = {
    // collect class annotations, such as 'alignBitfieldBy'
    val thisClassAnnots = tpe.typeSymbol.annotations

    PrimitiveRefl.toScalaType(tpe) match {
      case t if t <:< ru.typeOf[Boolean] => sizeOf.Boolean
      case t if t <:< ru.typeOf[Byte] => sizeOf.Byte
      case t if t <:< ru.typeOf[Char] => sizeOf.Char
      case t if t <:< ru.typeOf[Double] => sizeOf.Double
      case t if t <:< ru.typeOf[Float] => sizeOf.Float
      case t if t <:< ru.typeOf[Int] => sizeOf.Int
      case t if t <:< ru.typeOf[Long] => sizeOf.Long
      case t if t <:< ru.typeOf[Short] => sizeOf.Short

      // TODO: mit squeeze entkoppeln      case t if t <:< ru.typeOf[String] && thisFieldAnnots.hasWithFixedLength => thisFieldAnnots.getWithFixedLengthOr(default = 0)
      case t if t <:< ru.typeOf[String] => 0

      case t if t <:< ru.typeOf[List[_]] =>
        // TODO: mit squeeze entkoppeln        val sizeOfOne = t.typeArgs.headOption.matchTo(guess(_), default = 0)
        // TODO: mit squeeze entkoppeln        val multi = thisFieldAnnots.getWithFixedCountOr(default = 0)
        // TODO: mit squeeze entkoppeln        multi * sizeOfOne
        0

      case t if t.typeSymbol.isAbstract => 0
      case t => FieldHelper.groupByBitfields(tpe = t).foldLeft(0)((i, c) => i + guess(c, upperClassAnnots = thisClassAnnots))
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
    //      alignedBits / 8
    //    } else {
    fields.foldLeft(0)((i, c) => i + guess(c.tpe, thisFieldAnnots = c.annos))
    //    }
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
