package de.pfke.squeeze.zlib.data.length.digital

object BitLengthIncludes
  extends BitLengthIncludes

trait BitLengthIncludes {
  implicit class fromBitLength(
    in: BitLength
    ) {
    def asOption: Option[BitLength] = if(in.compare(BitLength.zero) == 0) None else Some(in)
  }

  implicit class toBitLength(
    in: Double
    ) {
    def bit = BitLength(in)
    def bits = bit
    def Bit = bit
    def Bits = bit
  }
}
