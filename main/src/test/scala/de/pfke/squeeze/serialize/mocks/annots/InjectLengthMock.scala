package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.fieldAnnots.injectSize

case class InjectLengthMock(
  @injectSize(from = "_2ndParam") _1stParam  : Short,
                                         _2ndParam: String
)
