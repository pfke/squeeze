package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums

object DeviceEMC
  extends Enumeration {
  type DeviceEMC = Value

  val UNKNOWN     = Value(0x0000)
  val SIEMENS     = Value(0x0002)
  val SV          = Value(0x00D5)
  val PLANTRONICS = Value(0x01AC)
  val CCT         = Value(0x0351)
  val HAGENUK     = Value(0x0361)
  val AVM         = Value(0x0B74)
  val PHILIPS     = Value(0x1001)
  val AEG         = Value(0x1234)
  val PHILIPS2    = Value(0x8101)
  val GN_NETCOM   = Value(0xb400)
}
