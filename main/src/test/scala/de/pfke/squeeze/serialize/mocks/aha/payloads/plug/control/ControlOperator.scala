package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.control

object ControlOperator
  extends Enumeration {
  type ControlOperator = Value

  val NOP     = Value(0)
  val AND     = Value(1)
  val OR      = Value(2)
  val GE      = Value(3)
  val GT      = Value(4)
  val LT      = Value(5)
  val LE      = Value(6)
  val NOT     = Value(7)
  val SET     = Value(8)
  val CLEAR   = Value(9)
}
