package de.pfke.squeeze.serialize.serializerHints

import java.nio.ByteOrder

import de.pfke.grind.data.collection.bitString.BitIterator
import de.pfke.grind.data.length.digital.DigitalLength

case class ReadFromBitIteratorHint(
  iter: BitIterator,
  sizeToRead: Option[DigitalLength] = None
) extends ReadFrom {
  require(iter != null, "passed iter is null")

  /**
    * Bitte den Wert lesen aus dem Iterator lesen, wenn keine Größe gegeben ist, die mitgegebene Default-Anzahl an Bits lesen.
    */
  def read (
    defaultSize: DigitalLength,
    hints: SerializerHint*
  )(implicit
    byteOrder: ByteOrder
  ): Long = {
    val readThis = sizeToRead.getOrElse(defaultSize)
    require(iter.len >= readThis.toBits, s"given input has only ${iter.len} bits left, but we need ${readThis.toBits} bit (sizeToRead = $sizeToRead, default = $defaultSize)")
    iter.read(readThis.toBits.toInt)
  }
}
