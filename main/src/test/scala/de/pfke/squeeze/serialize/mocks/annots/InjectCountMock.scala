package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.injectCount

case class InjectCountMock(
  @injectCount(fromField = "_2ndParam") _1stParam: Short,
                                        _2ndParam: List[Byte],
  @injectCount(fromField = "_2ndParam") _3rdParam: Int
)
