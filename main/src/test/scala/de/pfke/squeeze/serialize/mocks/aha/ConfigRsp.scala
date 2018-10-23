package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.classAnnots.typeForIface

@typeForIface(0x6)
case class ConfigRsp(
  context: Int,
  res1: Int,
  configInd: ConfigInd
) extends AhaMessageBody
