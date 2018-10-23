package de.pfke.squeeze.zlib.data.length.digital

import de.pfke.squeeze.zlib.data.BitTwiddling
import de.pfke.squeeze.zlib.data.byTypes.simple.DoubleIncludes._
import de.pfke.squeeze.zlib.data.units.prefix.{IECPrefix, Prefix, SIPrefix}

object ByteLength {
  def apply(in: Double): ByteLength = new ByteLength(data = in, Prefix.No).promoteTo
  def apply(in: Double, prefix: Prefix): ByteLength = new ByteLength(data = in, prefix)
  def zero: ByteLength = apply(0)

  /**
    * Return the best matching IEC unit for the given bit length
    */
  def getBestMatchingIECUnit(
    in: ByteLength
  ): Prefix = getBestMatchingIECUnit(in.toByte)

  /**
    * Return the best matching IEC unit for the given bit length
    */
  def getBestMatchingIECUnit(
    in: Double
  ): Prefix = {
    val r1 = in
    val r2 = (r1 % math.pow(2, 64)).toLong // lower long
    val r3 = (r1 / math.pow(2, 64)).toLong // upper long
    val r4 = Array(r2, r3)
    val r5 = BitTwiddling.getMostSignificantBit(r4)

    r5 match {
      case Some(IECPrefix.Yobi.exp) => IECPrefix.Yobi
      case Some(IECPrefix.Zebi.exp) => IECPrefix.Zebi
      case Some(IECPrefix.Exbi.exp) => IECPrefix.Exbi
      case Some(IECPrefix.Pebi.exp) => IECPrefix.Pebi
      case Some(IECPrefix.Tebi.exp) => IECPrefix.Tebi
      case Some(IECPrefix.Gibi.exp) => IECPrefix.Gibi
      case Some(IECPrefix.Mebi.exp) => IECPrefix.Mebi
      case Some(IECPrefix.Kibi.exp) => IECPrefix.Kibi

      case _ => Prefix.No
    }
  }

  /**
    * Return the best matching IEC unit for the given bit length
    */
  def getBestMatchingSIUnit(
    in: ByteLength
  ): Prefix = getBestMatchingSIUnit(in.toByte)

  /**
    * Return the best matching IEC unit for the given bit length
    */
  def getBestMatchingSIUnit(
    in: Double
  ): Prefix = {
    in match {
      case i: Double if 0 == i % SIPrefix.Yotta.toValue => SIPrefix.Yotta
      case i: Double if 0 == i % SIPrefix.Zetta.toValue => SIPrefix.Zetta
      case i: Double if 0 == i % SIPrefix.Exa.toValue   => SIPrefix.Exa
      case i: Double if 0 == i % SIPrefix.Peta.toValue  => SIPrefix.Peta
      case i: Double if 0 == i % SIPrefix.Tera.toValue  => SIPrefix.Tera
      case i: Double if 0 == i % SIPrefix.Giga.toValue  => SIPrefix.Giga
      case i: Double if 0 == i % SIPrefix.Mega.toValue  => SIPrefix.Mega
      case i: Double if 0 == i % SIPrefix.Kilo.toValue  => SIPrefix.Kilo

      case _ => Prefix.No
    }
  }
}

class ByteLength(
  private[ByteLength] val data: Double,
  private[ByteLength] val prefix: Prefix
)
  extends DigitalLength
    with Ordered[ByteLength] {

  def +(that: ByteLength): ByteLength = ByteLength(data.toByte + that.toByte)
  def -(that: ByteLength): ByteLength = ByteLength(data.toByte - that.toByte)
  def *(that: ByteLength): ByteLength = ByteLength(data.toByte * that.toByte)
  def /(that: ByteLength): ByteLength = ByteLength(data.toByte / that.toByte)

  def +(op: Double): ByteLength = ByteLength(data.toByte + op)
  def -(op: Double): ByteLength = ByteLength(data.toByte - op)
  def *(op: Double): ByteLength = ByteLength(data.toByte * op)
  def /(op: Double): ByteLength = ByteLength(data.toByte / op)

  /**
    * Result of comparing `this` with operand `that`.
    *
    * Implement this method to determine how instances of A will be sorted.
    *
    * Returns `x` where:
    *   - `x < 0` when `this < that`
    *   - `x == 0` when `this == that`
    *   - `x > 0` when  `this > that`
    */
  override def compare(that: ByteLength) = data.compareTo(that.data)

  /**
    * Equals implementation.
    */
  override def equals(obj: scala.Any) = obj.isInstanceOf[ByteLength] && compare(obj.asInstanceOf[ByteLength]) == 0

  /**
    * Promote to best matching prefix.
    */
  def promoteTo: ByteLength = {
    prefix match {
      case t: IECPrefix => promoteTo(ByteLength.getBestMatchingIECUnit(this))
      case t: SIPrefix  => promoteTo(ByteLength.getBestMatchingSIUnit(this))

      case _ => promoteTo(ByteLength.getBestMatchingSIUnit(this))
    }
  }

  /**
    * Promote to other prefix.
    */
  def promoteTo(pref: Prefix): ByteLength = ByteLength(toByte / pref.toValue, pref)

  /**
    * Return the value as bits.
    */
  override def toBits = toByte * 8

  /**
    * Return the value as byte.
    */
  override def toByte = data * prefix.toValue

  /**
    * Return the value as byte.
    */
  def asBits: Double = data * prefix.toValue

  def asKiB = promoteTo(IECPrefix.Kibi)
  def asMiB = promoteTo(IECPrefix.Mebi)
  def asGiB = promoteTo(IECPrefix.Gibi)
  def asTiB = promoteTo(IECPrefix.Tebi)
  def asPiB = promoteTo(IECPrefix.Pebi)
  def asEiB = promoteTo(IECPrefix.Exbi)
  def asZiB = promoteTo(IECPrefix.Zebi)
  def asYiB = promoteTo(IECPrefix.Yobi)

  def asKB = promoteTo(SIPrefix.Kilo)
  def asMB = promoteTo(SIPrefix.Mega)
  def asGB = promoteTo(SIPrefix.Giga)
  def asTB = promoteTo(SIPrefix.Tera)
  def asPB = promoteTo(SIPrefix.Peta)
  def asEB = promoteTo(SIPrefix.Exa)
  def asZB = promoteTo(SIPrefix.Zetta)
  def asYB = promoteTo(SIPrefix.Yotta)

  /**
    * Creates a string with best-matching prefix
    */
  override def toString = toByte.asHumanReadableSiByte
}
