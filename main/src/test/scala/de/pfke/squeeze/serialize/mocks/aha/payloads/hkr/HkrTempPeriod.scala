package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.hkr

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

trait HkrTempPeriod
  extends Payload

case class HkrTempPeriod_00022007(
  startPeriod: Int,
  endPeriod: Int
) extends HkrTempPeriod

