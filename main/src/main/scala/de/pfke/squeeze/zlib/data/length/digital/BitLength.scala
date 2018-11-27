package de.pfke.squeeze.zlib.data.length.digital

import de.pfke.squeeze.zlib.data.units.prefix.Prefix

object BitLength {
  def apply(in: Int): BitLength = new BitLength(data = in)
  def zero: BitLength = apply(0)
}

class BitLength(
  private[BitLength] val data: Int
)
  extends DigitalLength {
  def +(that: BitLength): BitLength = BitLength(data + that.data)
  def -(that: BitLength): BitLength = BitLength(data - that.data)
  def *(that: BitLength): BitLength = BitLength(data * that.data)
  def /(that: BitLength): BitLength = BitLength(data / that.data)

  override def +(that: Int): BitLength = BitLength(data + that)
  override def -(that: Int): BitLength = BitLength(data - that)
  override def *(that: Int): BitLength = BitLength(data * that)
  override def /(that: Int): BitLength = BitLength(data / that)

  /**
    * Equals implementation.
    */
  override def equals(obj: scala.Any): Boolean = obj.isInstanceOf[BitLength] && compare(obj.asInstanceOf[BitLength]) == 0

  /**
    * Return the value as byte.
    */
  def toBits: Int = data

  /**
    * Return the value as byte.
    */
  def toByte: Int = asByte.toByte

  /**
    * Return the value as byte.
    */
  def asByte: ByteLength = ByteLength(data / 8, Prefix.No)

  override def toString: String = s"${toBits.toLong} bit"
}
