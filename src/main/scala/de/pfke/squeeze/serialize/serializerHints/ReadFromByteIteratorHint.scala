package de.pfke.squeeze.serialize.serializerHints

import java.nio.ByteOrder

import akka.util.ByteIterator
import de.pfke.squeeze.core.data.length.digital.DigitalLength
import de.pfke.squeeze.serialize.SerializerRunException

case class ReadFromByteIteratorHint(
  iter: ByteIterator,
  sizeToRead: Option[DigitalLength] = None
) extends ReadFrom {
  /**
    * Bitte den Wert lesen aus dem Iterator lesen, wenn keine Größe gegeben ist, die mitgegebene Default-Anzahl an Byte lesen.
    */
  def read (
    defaultSize: DigitalLength,
    hints: SerializerHint*
  )(implicit
    byteOrder: ByteOrder
  ): Long = {
    val readThis = findSizeHint(hints = hints).orElse(sizeToRead).getOrElse(defaultSize).toByte
    require(iter.len >= readThis.toByte, s"given input has only ${iter.len} byte left, but we need $readThis byte (sizeToRead = $sizeToRead, default = $defaultSize)")

    readThis match {
      case 1 => iter.getByte
      case 2 => iter.getShort
      case 3 => (iter.getShort << 8) | iter.getByte
      case 4 => iter.getInt
      case 5 => (iter.getInt << 8) | iter.getByte
      case 6 => (iter.getInt << 16) | iter.getShort
      case 7 => (iter.getInt << 24) | (iter.getShort << 8) | iter.getByte
      case 8 => iter.getLong

      case t => throw new SerializerRunException(s"u wanted me to read $t bytes, but i (this function) can only read 1 to 8")
    }
  }
}
