package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.requests

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

case class GetRelayTime(
  repeat_time: Int
) extends Payload
