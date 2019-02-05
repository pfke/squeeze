package de.pfke.squeeze.squeezer.complex.mocks

import de.pfke.squeeze.annots.classAnnots.typeForIface
import de.pfke.squeeze.annots.{injectLength, withFixedWidth}

@typeForIface(value = MessageType.MessageTypeB.id)
case class MessageB (
  @injectLength(fromField = "content")
  @withFixedWidth(size = 2)
  stringLen: Int,
  content: String
)
