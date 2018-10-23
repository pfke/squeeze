package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.temp

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.temp.TemperatureType.TemperatureType

trait Temperature
  extends Payload

case class Temperature_00022003(
  _0_1Celcius: Int,
     tempType: TemperatureType
) extends Temperature
