package de.pfke.squeeze.squeezer.mocks

import de.pfke.squeeze.annots.fields.injectLength

case class InjectLengthOnSubMock(
  _1stParam: SubInjectLengthOnSubMock
)
case class SubInjectLengthOnSubMock(
  @injectLength(fromField = "_2ndParam") _1stParam: Short,
                                         _2ndParam: String
)
