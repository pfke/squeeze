package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.relayTimes

object TimeEntryStart
  extends Enumeration {
  type TimeEntryStart = Value

  val START_DAY     = Value(0)
  val START_WEEK    = Value(1)
  val START_MONTH   = Value(2)
  val START_YEAR    = Value(3)
  val START_ABSOLUT = Value(4)
  val START_RELATIV = Value(5)
  val START_ZUFALL  = Value(6)
  val START_INVAL   = Value(7)
}
