package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.injectCount

case class InjectCountTwiceMock(
  @injectCount(fromField = "_4thParam") _1stParam: Short,
  @injectCount(fromField = "_3rdParam") _2ndParam: Short,
                                        _3rdParam: List[Byte],
                                        _4thParam: List[Byte]
)
