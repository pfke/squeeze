package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums

object AhaMessageType
  extends Enumeration {
  type AhaMessageType = Value

  val UNKNOWN                = Value(0xff)
  val REGISTER               = Value(0x0)
  val REGISTER_CONF          = Value(0x1)
  val RELEASE                = Value(0x2)
  val LISTEN                 = Value(0x3)
  val CONFIG_IND             = Value(0x4)
  val CONFIG_REQ             = Value(0x5)
  val CONFIG_RSP             = Value(0x6)
  val DATA                   = Value(0x7)
  val POLL                   = Value(0x8)
  val STATISTIC_REQ          = Value(0x9)
  val STATISTIC_CONFIG_RES   = Value(0xa)
  val STATISTIC_DATA_RES     = Value(0xb)
}
