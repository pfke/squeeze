package de.pfke.squeeze.squeezer.squeezerOnComplex.mocks

import de.pfke.squeeze.annots.classAnnots.typeForIface

@typeForIface(value = MessageType.MessageTypeA.id)
case class MessagePayloadA (
  content: Int
) extends MessagePayload
