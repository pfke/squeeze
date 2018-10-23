package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.hkr.tempDates

import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.hkr.tempDates.HkrTempDateType.HkrTempDateType

case class HkrTempDate(
  dateType: HkrTempDateType,
  starthour: Byte,
  startday: Byte,
  startmonth: Byte,
  startyear: Byte,
  endhour: Byte,
  endday: Byte,
  endmonth: Byte,
  endyear: Byte,
  temp: Byte,
  res1: Byte,
  res2: Byte,
  res3: Byte
)
