package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.group

import de.pfke.squeeze.annots.{injectCount, withFixedLength}
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

case class Group(
  @withFixedLength(80)                       name: String,
                                         deviceId: Int,
  @withFixedLength(20)                 identifier: String,
  @injectCount(fromField = "actions") actionCount: Int,
                                          actions: List[GroupAction]
) extends Payload
