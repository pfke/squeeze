package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.hkr

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

trait HkrTemp
  extends Payload

case class HkrTemp_00022006(
  tsoll: Byte,
  absenktemp: Byte,
  heiztemp: Byte,
  offset: Byte,
  tist: Byte
) extends HkrTemp
