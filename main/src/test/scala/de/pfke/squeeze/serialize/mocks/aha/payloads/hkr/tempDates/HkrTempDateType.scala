package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.hkr.tempDates

object HkrTempDateType
  extends Enumeration {
  type HkrTempDateType = Value

  val sommer  = Value(0)
  val urlaub1 = Value(1)
  val urlaub2 = Value(2)
  val urlaub3 = Value(3)
  val urlaub4 = Value(4)
  val unused1 = Value(5)
  val unused2 = Value(6)
  val party   = Value(7)
}
