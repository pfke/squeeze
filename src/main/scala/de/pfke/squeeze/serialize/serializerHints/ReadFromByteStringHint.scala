package de.pfke.squeeze.serialize.serializerHints

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.grind.data.length.digital.DigitalLength

case class ReadFromByteStringHint(
  in: ByteString
) extends ReadFrom {
  /**
    * Bitte den Wert lesen aus dem Iterator lesen, wenn keine Größe gegeben ist, die mitgegebene Default-Anzahl an Byte lesen.
    */
  def read (
    defaultSize: DigitalLength,
    hints: SerializerHint*
  )(implicit
    byteOrder: ByteOrder
  ): Long = ReadFromByteIteratorHint(iter = in.iterator).read(defaultSize, hints = hints:_*)
}
