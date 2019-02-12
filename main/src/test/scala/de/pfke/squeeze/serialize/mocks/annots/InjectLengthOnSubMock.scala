package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.injectSize

case class InjectLengthOnSubMock(
  _1stParam: SubInjectLengthOnSubMock
)
case class SubInjectLengthOnSubMock(
  @injectSize(from = "_2ndParam") _1stParam  : Short,
                                         _2ndParam: String
)
