package de.pfke.squeeze.zlib.data

import scala.collection.BitSet

object BitTwiddling {

  /**
   * Get the most significant bit from an enumeration
   */
  def getMostSignificantBit[T <: Enumeration](
    enum: T
    ): Option[Int] = getMostSignificantBit(enum.values.toBitMask)

  /**
   * Get the most significant bit from a bit set
   */

  /**
   * Get the most significant bit from a bit set
   */
  def getMostSignificantBit(
    bitSet: BitSet
    ): Option[Int] = getMostSignificantBit(bitSet.toBitMask)
  
  /**
   * Get the most significant bit from an array of bit masks
   */
  def getMostSignificantBit(
    bitSet: Array[Long]
    ): Option[Int] = {
    val ar = bitSet // drop most sign longs with 0
      .reverse
      .dropWhile(_ == 0)
      .reverse

    if(ar.length == 0) return None

    val offset = ar.tail.length * 64
    getMostSignificantBit(ar.reverse.head) match {
      case Some(x) => Some(x + offset)
      case None => None
    }
  }
  
  /**
   * Get the most significant bit from a bit mask (long)
   */
  def getMostSignificantBit(
    bitMask: Long
    ): Option[Int] = {
    val offset = if((bitMask >> 32) == 0) 0 else 32

    getMostSignificantBit((bitMask >> offset).toInt) match {
      case Some(x) => Some(x + offset)
      case None => None
    }
  }
  
  /**
   * Get the most significant bit from a bit mask (int)
   */
  def getMostSignificantBit(
    bitMask: Int
    ): Option[Int] = {
    bitMask match {
      case 0 => None
      case t if (t & (1 << 31)) != 0 => Some(31)
      case t => Some((math.log(t) / math.log(2)).toInt)
    }
  }
}
