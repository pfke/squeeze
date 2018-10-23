package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.injectLength

case class InjectLengthMock(
  @injectLength(fromField = "_2ndParam") _1stParam: Short,
                                         _2ndParam: String
)
