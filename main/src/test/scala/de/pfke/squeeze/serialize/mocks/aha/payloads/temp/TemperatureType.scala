package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.temp

object TemperatureType
  extends Enumeration {
  type TemperatureType = Value

  val current = Value(0) // aktuelle Temperatur
  val currentOffset = Value(1) // Korrekturoffset der aktuellen Temperatur
}
