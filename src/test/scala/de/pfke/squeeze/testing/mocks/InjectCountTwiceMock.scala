package de.pfke.squeeze.testing.mocks

import de.pfke.squeeze.annots.fields.injectListSize

case class InjectCountTwiceMock(
  @injectListSize(fromField = "_4thParam") _1stParam: Short,
  @injectListSize(fromField = "_3rdParam") _2ndParam: Short,
                                           _3rdParam: List[Byte],
                                           _4thParam: List[Byte]
)
