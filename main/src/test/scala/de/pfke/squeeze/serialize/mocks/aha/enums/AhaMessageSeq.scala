package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums

object AhaMessageSeq
  extends Enumeration {
  type AhaMessageSeq = Value

  val MIDDLE_OF_INFORMATION = Value(0)
  val START_OF_INFORMATION  = Value(1)
  val END_OF_INFORMATION    = Value(2)
  val WHOLE_INFORMATION     = Value(3)
}
