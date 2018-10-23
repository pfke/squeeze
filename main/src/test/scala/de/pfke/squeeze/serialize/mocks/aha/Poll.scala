package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.classAnnots.typeForIface

@typeForIface(0x8)
case class Poll(
  number: Int
) extends AhaMessageBody
