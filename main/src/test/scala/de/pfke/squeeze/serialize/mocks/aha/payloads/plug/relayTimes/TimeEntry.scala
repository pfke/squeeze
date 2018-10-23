package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.relayTimes

import de.pfke.squeeze.annots.asBitfield
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.relayTimes.TimeEntryAction.TimeEntryAction
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.relayTimes.TimeEntryLoop.TimeEntryLoop
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.relayTimes.TimeEntryStart.TimeEntryStart

case class TimeEntry(
  @asBitfield(24)   time: Int,
  @asBitfield( 3) action: TimeEntryAction,
  @asBitfield( 3)  start: TimeEntryStart,
  @asBitfield( 2)   loop: TimeEntryLoop
)
