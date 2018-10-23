package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums

object DeviceState
  extends Enumeration {
  type DeviceState = Value

  val UNKNOWN  = Value(0xff)
  val RELEASED = Value(0)
  val INACTIVE = Value(1)
  val ACTIVE   = Value(2)
}
