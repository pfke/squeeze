package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.control

import de.pfke.squeeze.annots.withFixedLength

trait TempMaster
  extends BaseControl

case class TempMaster_00022005(
        enabled: Boolean,
        isKlima: Boolean,
  upperSollwert: Short,
  lowerSollwert: Short,
      threshold: Byte,
           res1: Byte,
   tempSensorId: Int
) extends TempMaster

case class TempMaster_00022013(
                             enabled: Boolean,
                             isKlima: Boolean,
                       upperSollwert: Short,
                       lowerSollwert: Short,
                           threshold: Byte,
                                res1: Byte,
                        tempSensorId: Int,
  @withFixedLength(20) tempSensorAIN: String
) extends TempMaster

