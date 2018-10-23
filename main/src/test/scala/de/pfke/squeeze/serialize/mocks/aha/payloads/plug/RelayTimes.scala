package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug

import de.pfke.squeeze.annots.injectCount
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.relayTimes.TimeEntry
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.relayTimes.TimeTableUse.TimeTableUse

case class RelayTimes(
                                    table: TimeTableUse,
                                   enable: Short,
  @injectCount(fromField = "time") anzahl: Short,
                                     time: List[TimeEntry]
) extends Payload
