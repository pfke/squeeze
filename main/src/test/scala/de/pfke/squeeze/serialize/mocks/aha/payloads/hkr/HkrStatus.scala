package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.hkr

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

trait HkrStatus
  extends Payload

case class HkrStatus_00022006(
  flags0: Byte,
  flags1: Byte,
  error: Byte,
  batterie: Byte
) extends HkrStatus
