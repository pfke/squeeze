package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.relayTimes

object TimeEntryAction
  extends Enumeration {
  type TimeEntryAction = Value

  val ACTION_OFF            = Value(0)
  val ACTION_ON             = Value(1)
  val ACTION_ZUFALL_BEGIN   = Value(2)
  val ACTION_ZUFALL_ENDE    = Value(3)
  val ACTION_ZUFALL_AKTIV   = Value(4)
  val ACTION_ZUFALL_INAKTIV = Value(5)
  val ACTION_INVAL          = Value(7)
}
