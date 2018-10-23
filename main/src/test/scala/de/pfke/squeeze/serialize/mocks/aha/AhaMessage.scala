package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.{alignBitfieldsBy, asBitfield, injectTotalLength, injectType}
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums.AhaMessageSeq.AhaMessageSeq
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums.AhaMessageType
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums.AhaMessageType.AhaMessageType

@alignBitfieldsBy(32)
case class AhaMessage(
  @asBitfield(bits = 8)
  @injectType(fromField = "body")
  mType: AhaMessageType = AhaMessageType.UNKNOWN,

  @asBitfield(bits = 8)
  mSeq: AhaMessageSeq,

  @asBitfield(bits = 16)
  @injectTotalLength()
  length: Short = 0,

  handle: Int,

  body: AhaMessageBody
)
