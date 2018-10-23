package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.asBitfield
import de.pfke.squeeze.annots.classAnnots.typeForIface
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.AhaFunction.AhaFunction

@typeForIface(0xb)
case class StatisticDataRsp(
                        deviceId: Short,
                            res1: Short,
  @asBitfield( 8)       function: AhaFunction,
  @asBitfield( 3) statisticEbene: Byte,
  @asBitfield( 1)           res2: Byte,
  @asBitfield(12)   sampleAnzahl: Short,
  @asBitfield( 8)  sampleGroesse: Byte,

                            data: List[Byte]
) extends AhaMessageBody
