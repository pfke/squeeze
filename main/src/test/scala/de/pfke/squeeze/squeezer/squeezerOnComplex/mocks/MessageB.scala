package de.pfke.squeeze.squeezer.squeezerOnComplex.mocks

import de.pfke.squeeze.annots.classAnnots.typeForIface
import de.pfke.squeeze.annots.{injectLength, width}

@typeForIface(value = MessageType.MessageTypeB.id)
case class MessageB (
  @injectLength(fromField = "content")
  @width(size = 2)
  stringLen: Int,
  content: String
)
