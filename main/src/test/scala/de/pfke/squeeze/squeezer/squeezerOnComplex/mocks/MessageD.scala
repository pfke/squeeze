package de.pfke.squeeze.squeezer.squeezerOnComplex.mocks

import de.pfke.squeeze.annots.classAnnots.typeForIface
import de.pfke.squeeze.annots.{injectCount, width}

@typeForIface(value = MessageType.MessageTypeD.id)
case class MessageD (
  @injectCount(fromField = "list")
  @width(size = 2)
  listCount: Int,

  list: List[MessageDSub]
)
