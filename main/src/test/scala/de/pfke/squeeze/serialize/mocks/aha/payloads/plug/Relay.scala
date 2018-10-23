package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug

import de.pfke.squeeze.annots.withFixedLength
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

case class Relay(
  @withFixedLength(4) status: Boolean
) extends Payload
