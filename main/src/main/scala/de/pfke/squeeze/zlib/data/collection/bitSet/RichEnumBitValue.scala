package de.pfke.squeeze.zlib.data.collection.bitSet

import de.pfke.squeeze.zlib.data.BitTwiddling

import scala.collection.immutable
import scala.collection.immutable.BitSet

object RichEnumBitValue {
  /**
   * Create new instance with this enum as underlying type
   *
   * @param enum this is the one
   * @tparam E type info
 *
   * @return new instance
   */
  def apply[E <: Enumeration](
    enum: E
    ) = applyWithLen(enum, 0, BitTwiddling.getMostSignificantBit(enum.maxId).getOrElse(0) + 1)

  /**
   * Create new instance with this enum as underlying type (with len info as mask)
   *
   * @param enum this is the one
   * @param shift shift the result
   * @tparam E type info
 *
   * @return new instance
   */
  def applyWithLen[E <: Enumeration](
    enum: E,
    shift: Int,
    len: Int
    ) = applyWithMask(enum, shift, (1 << len) - 1)

  /**
   * Create new instance with this enum as underlying type (with given mask as mask)
   *
   * @param enum this is the one
   * @param shift shift the result
   * @param mask mask out these bits
   * @tparam E type info
 *
   * @return new instance
   */
  def applyWithMask[E <: Enumeration](
    enum: E,
    shift: Int,
    mask: Int
    ) = new RichEnumBitValue[E](enum, _shift = shift, _mask = mask)
}

class RichEnumBitValue[E <: Enumeration](
  _enum: E,
  _shift: Int,
  _mask: Int
  ) {
  // fields
  private var _value: Option[E#Value] = None

  def mask = _mask
  def shift = _shift

  /**
   * This method is used to clear out the all values.
   */
  def clear() = _value = None

  /**
   * Returns true if the value is active
   */
  def contains(value: E#Value): Boolean = _value.contains(value)

  /**
   * This method is used to clear out the given value.
   */
  def -=(value: E#Value): this.type = {
    if(contains(value))
      _value = None
    this
  }

  /**
   * This method takes the enum and sets its value.
   */
  def +=(value: E#Value): this.type = {
    if(!maskOut(value))
      _value = Some(value)
    this
  }

  /**
   * Add this value
   */
  def add(value: E#Value) = this += value

  /**
   * Remove this value
   */
  def remove(value: E#Value) = this -= value

  /**
   * Return our bit mask.
   */
  def toBitMask: Array[Long] = toBitSet.toBitMask

  /**
   * Return our bit set.
   */
  def toBitSet: immutable.BitSet = BitSet.fromBitMask(Array(shifted()))

  /**
   * Returns true if value should masked out
   */
  private def maskOut(value: E#Value) = (value.id & _mask) == 0

  /**
   * Shift enum to usable var
   */
  private def shifted(): Long = _value match {
    case Some(x) => x.id << _shift
    case None => 0
  }
}
