package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.control

import de.pfke.squeeze.annots.injectType
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.control.ControlType.ControlType

case class ControlWrapper(
  @injectType(fromField = "data") controlType: ControlType,
                                         data: BaseControl
)
