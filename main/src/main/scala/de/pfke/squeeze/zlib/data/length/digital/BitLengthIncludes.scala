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
    in: Int
    ) {
    def bit = BitLength(in)
    def bits: BitLength = bit
    def Bit: BitLength = bit
    def Bits: BitLength = bit
  }
}
