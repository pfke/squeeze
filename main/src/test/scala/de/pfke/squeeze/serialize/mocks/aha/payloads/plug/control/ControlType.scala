package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.control

object ControlType
  extends Enumeration {
  type ControlType = Value

  val UNDEF       = Value(0)
  val POWER_DOWN  = Value(1)
  val COUNT_DOWN  = Value(2) // eigentlich enum mit neuer version
  val TEMP_MASTER = Value(3) // eigentlich enum mit neuer version
}
