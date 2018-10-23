package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.powermeter

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

case class Voltage(
  mV: Int
) extends Payload
