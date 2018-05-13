package de.pfke.squeeze.testing.mocks

import de.pfke.squeeze.annots.fields.injectTotalLength

case class InjectTotalLengthOnSubMock(
                     _1stParam: SubInjectTotalLengthOnSubMock,
  @injectTotalLength _2ndParam: Short
)

case class SubInjectTotalLengthOnSubMock(
                     _1stParam: Short,
  @injectTotalLength _2ndParam: Short,
                     _3rdParam: Int
)
