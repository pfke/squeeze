package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.asBitfield
import de.pfke.squeeze.annots.classAnnots.typeForIface
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.AhaFunction.AhaFunction

@typeForIface(0xa)
case class StatisticConfigRsp(
                         deviceId: Short,
                             res1: Short,
  @asBitfield( 8)        function: AhaFunction,
  @asBitfield( 3) statisticEbenen: Byte, // StatisticLayer
  @asBitfield( 1)            res2: Byte,
  @asBitfield( 8)   sampleGroesse: Byte,
  @asBitfield(12)   baseTimeInMin: Short,
                             data: List[StatisticConfigData]
) extends AhaMessageBody
