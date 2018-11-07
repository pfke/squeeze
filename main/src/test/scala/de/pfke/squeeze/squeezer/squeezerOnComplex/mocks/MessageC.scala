package de.pfke.squeeze.squeezer.squeezerOnComplex.mocks

import de.pfke.squeeze.annots.classAnnots.typeForIface
import de.pfke.squeeze.annots.{injectCount, width}

@typeForIface(value = MessageType.MessageTypeC.id)
case class MessageC (
  @injectCount(fromField = "list")
  @width(size = 2)
  listCount: Int,

  list: List[Int]
)
