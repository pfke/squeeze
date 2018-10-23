package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.hkr

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

trait GetActionTimes
  extends Payload

case class GetActionTimes_00022006(
  repeat_time: Int
) extends GetActionTimes
