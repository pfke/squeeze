package de.pfke.squeeze.zlib.data.length.digital

import de.pfke.squeeze.zlib.data.length.Length

object DigitalLength {
  def fromBits(in: Int): DigitalLength = if (in % 8 == 0) ByteLength(in / 8) else BitLength(in)
  def zero: DigitalLength = ByteLength.zero
}

abstract class DigitalLength
  extends Length
  with Ordered[DigitalLength] {
  def +(that: DigitalLength): DigitalLength = DigitalLength.fromBits(this.toBits + that.toBits)
  def -(that: DigitalLength): DigitalLength = DigitalLength.fromBits(this.toBits - that.toBits)
  def *(that: DigitalLength): DigitalLength = DigitalLength.fromBits(this.toBits * that.toBits)
  def /(that: DigitalLength): DigitalLength = DigitalLength.fromBits(this.toBits / that.toBits)

  def +(that: Int): DigitalLength
  def -(that: Int): DigitalLength
  def *(that: Int): DigitalLength
  def /(that: Int): DigitalLength

  /**
    * Return the value as bits.
    */
  def toBits: Int

  /**
    * Return the value as byte.
    */
  def toByte: Int

  /**
    * Result of comparing `this` with operand `that`.
    *
    * Implement this method to determine how instances of A will be sorted.
    *
    * Returns `x` where:
    *
    *   - `x < 0` when `this < that`
    *   - `x == 0` when `this == that`
    *   - `x > 0` when  `this > that`
    */
  override def compare (that: DigitalLength): Int = {
    val thisBits = this.toBits
    val thatBits = that.toBits

    if (thisBits < thatBits) {
      -1
    } else if (thisBits == thatBits) {
      0
    } else {
      1
    }
  }
}
