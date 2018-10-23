package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums

object DeviceType
  extends Enumeration {
  type DeviceType = Value

  val UNKNOWN        = Value(0x0)
  val REPEATER_230   = Value(0x1)
  val PLC_546        = Value(0x2)
  val HKR            = Value(0x3)
  val DECT_MTD       = Value(0x4)
  val DECT_MTF       = Value(0x5)
  val DECT_M2        = Value(0x6)
  val DECT_C3        = Value(0x7)
  val DECT_SPEEDFONE = Value(0x8)
  val FDECT_200      = Value(0x9)
  val REPEATER_100   = Value(0xa)
  val GROUP          = Value(0xb)
  val SML            = Value(0xc)
}
