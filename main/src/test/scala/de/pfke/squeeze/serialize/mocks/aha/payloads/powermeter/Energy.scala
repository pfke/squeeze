package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.powermeter

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

case class Energy(
  Wh: Int
) extends Payload
