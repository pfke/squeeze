package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.device

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.Payload

trait SortIndex
  extends Payload

case class SortIndex_00022008(
  id: Int,
  sortIndex: Int
) extends SortIndex
