package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.device

import de.pfke.squeeze.annots.withFixedLength
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

case class DeviceParam (
  res1: Int,
  @withFixedLength(80) name: String
) extends Payload
