package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.powermeter

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

case class PowerFactor(
  value: Int
) extends Payload
