package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.relayTimes

object TimeEntryLoop
  extends Enumeration {
  type TimeEntryLoop = Value

  val LOOP_LAST    = Value(0)
  val LOOP_NEXT    = Value(1)
  val LOOP_RESTART = Value(2)
  val LOOP_INVAL   = Value(3)
}
