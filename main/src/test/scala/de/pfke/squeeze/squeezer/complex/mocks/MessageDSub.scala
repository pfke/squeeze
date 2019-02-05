package de.pfke.squeeze.squeezer.complex.mocks

import de.pfke.squeeze.squeezer.complex.mocks
import de.pfke.squeeze.squeezer.complex.mocks.MessageDSubType.MessageDSubType

trait MessageDSub {
  def subType: MessageDSubType
}

object MessageDSubType
  extends Enumeration {
  type MessageDSubType = Value

  val MessageDSubTypeA: mocks.MessageDSubType.Value = Value(0)
  val MessageDSubTypeB: mocks.MessageDSubType.Value = Value(1)
}

case class MessageDSubA (
  subType: MessageDSubType = MessageDSubType.MessageDSubTypeA
) extends MessageDSub

case class MessageDSubB (
  subType: MessageDSubType = MessageDSubType.MessageDSubTypeB
) extends MessageDSub
