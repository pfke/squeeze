package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.powermeter

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

case class Current(
  _100uA: Int
) extends Payload
