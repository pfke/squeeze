package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.classAnnots.typeForIface
import de.pfke.squeeze.annots.{alignBitfieldsBy, asBitfield}

@alignBitfieldsBy(32)
@typeForIface(0x9)
case class StatisticReq(
                      deviceId: Short,
                          res1: Short = 0,
  @asBitfield(24) functionMask: Int,
  @asBitfield( 8)   ebenenMask: Byte
) extends AhaMessageBody
