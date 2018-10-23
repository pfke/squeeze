package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.group

object GroupActionType
  extends Enumeration {
  type GroupActionType = Value

  val NOP = Value(0)
  val ADD = Value(1)
  val DEL = Value(2)
  val SET_MASTER_DEVICE = Value(3)
}
