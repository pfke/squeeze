package de.pfke.squeeze.zlib.data.collection.bitString

import de.pfke.squeeze.zlib.data.collection.bitString

object BitStringAlignment
  extends Enumeration {
  type BitStringAlignment = Value

  val _8Bit: bitString.BitStringAlignment.Value = Value(8)
  val _16Bit: bitString.BitStringAlignment.Value = Value(16)
  val _32Bit: bitString.BitStringAlignment.Value = Value(32)

  def bitWidth(bitStringAlignment: BitStringAlignment): Int = {
    bitStringAlignment match {
      case BitStringAlignment._8Bit => 8
      case BitStringAlignment._16Bit => 16
      case BitStringAlignment._32Bit => 32
    }
  }

  def enumFromWidth (bits: Int): bitString.BitStringAlignment.Value = {
    bits match {
      case 8 => BitStringAlignment._8Bit
      case 16 => BitStringAlignment._16Bit
      case 32 => BitStringAlignment._32Bit

      case _ => throw new IllegalArgumentException(s"no enum defined for bit width $bits")
    }
  }
}
