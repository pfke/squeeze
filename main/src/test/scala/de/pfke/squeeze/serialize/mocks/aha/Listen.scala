package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.classAnnots.typeForIface

@typeForIface(0x3)
case class Listen(
  listenMask: Int,
    deviceId: Short,
        res1: Short = 0
) extends AhaMessageBody
