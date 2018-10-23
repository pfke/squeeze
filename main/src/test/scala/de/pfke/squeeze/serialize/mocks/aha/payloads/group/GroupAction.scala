package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.group

import de.pfke.squeeze.annots.withFixedLength
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.group.GroupActionType.GroupActionType

case class GroupAction(
                       actionType: GroupActionType,
                         deviceId: Int,
  @withFixedLength(20) identifier: String
) extends Payload
