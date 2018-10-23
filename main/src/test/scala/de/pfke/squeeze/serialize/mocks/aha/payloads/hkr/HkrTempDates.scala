package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.hkr

import de.pfke.squeeze.annots.injectCount
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.hkr.tempDates.HkrTempDate

trait HkrTempDates
  extends Payload

case class HkrTempDates_00022010(
  @injectCount(fromField = "dates") count: Int,
  dates: List[HkrTempDate]
) extends HkrTempDates
