package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums

object GroupAction
  extends Enumeration {
  type GroupAction = Value

  val NOP        = Value(0)
  val AddElement = Value(1)
  val DelElement = Value(2)
}
