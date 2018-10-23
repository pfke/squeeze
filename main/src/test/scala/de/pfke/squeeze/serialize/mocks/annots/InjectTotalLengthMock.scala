package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.injectTotalLength

case class InjectTotalLengthMock(
                     _1stParam: Short,
  @injectTotalLength _2ndParam: Short,
                     _3rdParam: Int
)
