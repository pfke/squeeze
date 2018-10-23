package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.relayTimes

object TimeTableUse
  extends Enumeration {
  type TimeTableUse = Value

  val TIME_TABLE_DAILY             = Value(0)
  val TIME_TABLE_WEEKLY            = Value(1)
  val TIME_TABLE_ZUFALL            = Value(2)
  val TIME_TABLE_RYTHMISCH         = Value(3)
  val TIME_TABLE_SINGLE            = Value(4)
  val TIME_TABLE_SUN_CALENDAR      = Value(5)
  val TIME_TABLE_MOON_CALENDAR     = Value(6)
  val TIME_TABLE_CALENDAR          = Value(7)
  val TIME_TABLE_GROUP_TEMP_WEEKLY = Value(8)
}
