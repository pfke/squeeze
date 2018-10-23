package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.classAnnots.typeForIface

@typeForIface(0x5)
case class ConfigReq(
  context: Int
) extends AhaMessageBody
