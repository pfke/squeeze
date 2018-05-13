package de.pfke.squeeze.testing.mocks

import de.pfke.squeeze.annots.fields.injectListSize

case class InjectCountOnSubMock(
  _1stParam: SubInjectCountOnSubMock
)
case class SubInjectCountOnSubMock(
  @injectListSize(fromField = "_2ndParam") _1stParam: Short,
                                           _2ndParam: List[Byte],
  @injectListSize(fromField = "_2ndParam") _3rdParam: Int
)
