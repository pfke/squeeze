package de.pfke.squeeze.squeezer.mocks

import de.pfke.squeeze.annots.fields.injectListSize

case class InjectCountMock(
  @injectListSize(fromField = "_2ndParam") _1stParam: Short,
                                           _2ndParam: List[Byte],
  @injectListSize(fromField = "_2ndParam") _3rdParam: Int
)
