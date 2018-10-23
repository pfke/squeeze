package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads

import scala.language.implicitConversions

object AhaFunction
  extends Enumeration {
  type AhaFunction = Value

  val UNKNOWN        = Value(0)

  val Timer          = Value(1)
  val Connect        = Value(7)
  val Disconnect     = Value(8)
  val DeviceParam    = Value(10)
  val Button         = Value(14)
  val Relay          = Value(15)
  val RelayTimes     = Value(16)
  val Current        = Value(18)
  val Voltage        = Value(19)
  val Power          = Value(20)
  val Energy         = Value(21)
  val PowerFactor    = Value(22)
  val Temp           = Value(23)
  val GetButton      = Value(24)
  val GetRelay       = Value(25)
  val GetCurrent     = Value(26)
  val GetVoltage     = Value(27)
  val GetPower       = Value(28)
  val GetEnergy      = Value(29)
  val GetPowerFactor = Value(30)
  val GetTemp        = Value(31)
  val GetRelayTimes  = Value(32)
  val Options        = Value(35)
  val Control        = Value(37)
  val Group          = Value(49)
  // ab 0x00022006
  val ActionTimes    = Value(55)
  val GetActionTimes = Value(56)
  val HkrTemp        = Value(57)
  val HkrStatus      = Value(58)
  // ab 0x00022007
  val HkrTempPeriod  = Value(59)
  // ab 0x00022008
  val HkrWindowOpen  = Value(60)
  val SortIndex      = Value(61) // not published over net
  // ab 0x00022009
  val HkrTempDates   = Value(62)
}
