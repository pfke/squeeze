package de.pfke.squeeze.testing

import scala.util.Random

object RandomHelper {
  val rnd = new Random(new Random().nextInt())

  /**
   * Return a new random array.
   */
  def rndArray(len: Int): Array[Byte] = {
    val ar = new Array[Byte](len)
    rnd.nextBytes(ar)

    ar
  }

  /**
   * Return a new random boolean.
   */
  def rndBool: Boolean = rnd.nextBoolean()

  /**
   * Return a new random byte.
   */
  def rndByte: Byte = rndInt(upper = 255).toByte

  /**
   * Return a new random float.
   */
  def rndDouble: Double = rnd.nextDouble()

  /**
   * Return a new random float.
   */
  def rndFloat: Float = rnd.nextFloat()

  /**
   * Return a new random int with upper and lower bounds.
   */
  def rndInt(lower: Int = 1, upper: Int = Int.MaxValue): Int = rnd.nextInt(upper - lower) + lower

  /**
   * Return a new random long.
   */
  def rndLong: Long = rnd.nextLong()

  /**
   * Return a new random short.
   */
  def rndShort: Short = rndInt().toShort

  /**
   * Return a random string with the given length.
   */
  def rndString(length: Int = 100): String = new String((0 until length).map { i=> rnd.nextPrintableChar() }.toArray)
}
