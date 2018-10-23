package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.injectLength

case class InjectLengthOnSubMock(
  _1stParam: SubInjectLengthOnSubMock
)
case class SubInjectLengthOnSubMock(
  @injectLength(fromField = "_2ndParam") _1stParam: Short,
                                         _2ndParam: String
)
