package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.classAnnots.typeForIface

@typeForIface(0x1)
case class RegisterConf(
  handle: Int,
  major: Short,
  minor: Short
) extends AhaMessageBody
