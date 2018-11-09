package de.pfke.squeeze.zlib.data.length.digital

import de.pfke.squeeze.zlib.data.units.prefix.Prefix

object BitLength {
  def apply(in: Double): BitLength = new BitLength(data = in)
  def zero: BitLength = apply(0)
}

class BitLength(
  private[BitLength] val data: Double
)
  extends DigitalLength {
  def +(that: BitLength): BitLength = BitLength(data + that.data)
  def -(that: BitLength): BitLength = BitLength(data - that.data)
  def *(that: BitLength): BitLength = BitLength(data * that.data)
  def /(that: BitLength): BitLength = BitLength(data / that.data)

  def +(op: Double): BitLength = BitLength(data + op)
  def -(op: Double): BitLength = BitLength(data - op)
  def *(op: Double): BitLength = BitLength(data * op)
  def /(op: Double): BitLength = BitLength(data / op)

  /**
    * Equals implementation.
    */
  override def equals(obj: scala.Any): Boolean = obj.isInstanceOf[BitLength] && compare(obj.asInstanceOf[BitLength]) == 0

  /**
    * Return the value as byte.
    */
  def toBits: Double = data

  /**
    * Return the value as byte.
    */
  def toByte: Double = asByte.toByte

  /**
    * Return the value as byte.
    */
  def asByte: ByteLength = ByteLength(data / 8, Prefix.No)

  override def toString: String = s"${toBits.toLong} bit"
}
