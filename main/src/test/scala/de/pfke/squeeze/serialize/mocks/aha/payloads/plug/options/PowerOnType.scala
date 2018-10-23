package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.options

object PowerOnType
  extends Enumeration {
  type PowerOnType = Value

  val UNKNOWN                 = Value(0xffff)
  val OFF_AFTER_REBOOT        = Value(0)
  val ON_AFTER_REBOOT         = Value(1)
  val LAST_STATE_AFTER_REBOOT = Value(2)
}
