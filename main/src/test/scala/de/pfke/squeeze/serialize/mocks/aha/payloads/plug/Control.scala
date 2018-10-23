package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug

import de.pfke.squeeze.annots.injectCount
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.control.ControlWrapper

case class Control(
                                      deviceId: Int,
  @injectCount(fromField = "items") item_count: Int,
                                         items: List[ControlWrapper]
) extends Payload
