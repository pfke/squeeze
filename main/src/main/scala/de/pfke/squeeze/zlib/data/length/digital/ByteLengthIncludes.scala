package de.pfke.squeeze.zlib.data.length.digital

import de.pfke.squeeze.zlib.data.byTypes.simple.DoubleIncludes
import de.pfke.squeeze.zlib.data.units.prefix.{IECPrefix, Prefix, SIPrefix}

object ByteLengthIncludes
  extends ByteLengthIncludes

trait ByteLengthIncludes {
  implicit class fromByteLength (
    in: ByteLength
  ) {
    def asOption: Option[ByteLength] = if(in.compare(ByteLength.zero) == 0) None else Some(in)
  }

  implicit class toByteLength (
    in: Double
  ) {
    def byte = ByteLength(in, Prefix.No)
    def bytes = byte
    def B = byte
    def Byte = byte
    def Bytes = byte
  }

  implicit class toByteLengthFromIEC (
    in: Double
  ) {
    def Kibibyte = create(IECPrefix.Kibi)
    def KiB = Kibibyte

    def Mebibyte = create(IECPrefix.Mebi)
    def MiB = Mebibyte

    def Gibibyte = create(IECPrefix.Gibi)
    def GiB = Gibibyte

    def Tebibyte = create(IECPrefix.Tebi)
    def TiB = Tebibyte

    def Pebibyte = create(IECPrefix.Pebi)
    def PiB = Pebibyte

    def Exbibyte = create(IECPrefix.Exbi)
    def EiB = Exbibyte

    def Zebibyte = create(IECPrefix.Zebi)
    def ZiB = Zebibyte

    def Yobibyte = create(IECPrefix.Yobi)
    def YiB = Yobibyte

    private def create(prefix: Prefix) = ByteLength(in, prefix)
  }

  implicit class toByteLengthFromSI (
    in: Double
  ) {
    def Kilobyte = create(SIPrefix.Kilo)
    def KB = Kilobyte

    def Megabyte = create(SIPrefix.Mega)
    def MB = Megabyte

    def Gigabyte = create(SIPrefix.Giga)
    def GB = Gigabyte

    def Terabyte = create(SIPrefix.Tera)
    def TB = Terabyte

    def Petabyte = create(SIPrefix.Peta)
    def PB = Petabyte

    def Exabyte = create(SIPrefix.Exa)
    def EB = Exabyte

    def Zettabyte = create(SIPrefix.Zetta)
    def ZB = Zettabyte

    def Yottabyte = create(SIPrefix.Yotta)
    def YB = Yottabyte

    private def create(prefix: Prefix) = ByteLength(in, prefix)
  }

  /**
   * Format byte value into a human readable string.
   */
  implicit class SpeedFormatterFromByteLength (
    bytes: ByteLength
  )
    extends DoubleIncludes.SpeedFormatterFromDouble(bytes = bytes.toByte)
}
