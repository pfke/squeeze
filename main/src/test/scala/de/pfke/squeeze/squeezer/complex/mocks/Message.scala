package de.pfke.squeeze.squeezer.complex.mocks

import de.pfke.squeeze.annots.{injectTotalLength, injectType, withFixedWidth}
import de.pfke.squeeze.squeezer.complex.mocks.MessageType.MessageType

case class Message (
  @injectType(fromField = "payload")
  @withFixedWidth(size = 2)
  messageType: MessageType,

  @injectTotalLength
  @withFixedWidth(size = 2)
  messageLength: Int,

  payload: MessagePayload
)