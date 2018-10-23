package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.hkr

import de.pfke.squeeze.annots.injectCount
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.relayTimes.TimeEntry
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.relayTimes.TimeTableUse.TimeTableUse

trait ActionTimes
  extends Payload

case class ActionTimes_00022006(
                                    table: TimeTableUse,
                                   enable: Short,
  @injectCount(fromField = "time") anzahl: Short,
                                     time: List[TimeEntry]
) extends ActionTimes
