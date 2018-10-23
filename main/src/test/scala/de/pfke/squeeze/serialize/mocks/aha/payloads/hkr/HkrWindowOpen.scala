package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.hkr

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

trait HkrWindowOpen
  extends Payload

case class HkrWindowOpen_00022008(
  trigger: Byte,
  timer: Byte
) extends HkrWindowOpen
