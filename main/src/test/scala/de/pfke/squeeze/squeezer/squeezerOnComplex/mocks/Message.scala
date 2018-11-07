package de.pfke.squeeze.squeezer.squeezerOnComplex.mocks

import de.pfke.squeeze.annots.{injectTotalLength, injectType, width}
import de.pfke.squeeze.squeezer.squeezerOnComplex.mocks.MessageType.MessageType

case class Message (
  @injectType(fromField = "payload")
  @width(size = 2)
  messageType: MessageType,

  @injectTotalLength
  @width(size = 2)
  messageLength: Int,

  payload: MessagePayload
)