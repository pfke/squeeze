package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.classAnnots.typeForIface
import de.pfke.squeeze.annots.injectLength

@typeForIface(0x7)
case class Data(
                                         deviceId: Short,
                                             res1: Short = 0,
  @injectLength(fromField = "data") payloadLength: Int = 0,
                                             data: List[DataBody]
) extends AhaMessageBody
