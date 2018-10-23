package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.{alignBitfieldsBy, asBitfield}
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums.StatisticTimeType.StatisticTimeType

@alignBitfieldsBy(32)
case class StatisticConfigData(
  @asBitfield(12)          anzahlWerte: Short,
  @asBitfield(12) anzahlZusammenfassen: Short,
  @asBitfield( 1)           notifyFlag: Boolean,
  @asBitfield( 1)            storeFlag: Boolean,
  @asBitfield( 6)                 res1: Int,
                              timeType: StatisticTimeType
) extends AhaMessageBody
