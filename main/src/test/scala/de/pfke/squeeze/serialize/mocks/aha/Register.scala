package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.classAnnots.typeForIface

@typeForIface(0x0)
case class Register(
  major: Short,
  minor: Short
) extends AhaMessageBody
