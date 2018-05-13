package de.pfke.squeeze.testing.mocks

import de.pfke.squeeze.annots.fields.injectTotalLength

case class InjectTotalLengthMock(
                     _1stParam: Short,
  @injectTotalLength _2ndParam: Short,
                     _3rdParam: Int
)
