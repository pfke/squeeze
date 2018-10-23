package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.options.PowerOnType.PowerOnType
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.options.SwitchLock.SwitchLock

trait Options
  extends Payload

case class Options_00010001(
  power_on_type: PowerOnType
) extends Options

case class Options_00020000(
  power_on_type: PowerOnType,
  lock: SwitchLock
) extends Options
