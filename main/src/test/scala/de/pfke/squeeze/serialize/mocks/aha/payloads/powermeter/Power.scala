package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.powermeter

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

case class Power(
  _10mW: Int
) extends Payload
