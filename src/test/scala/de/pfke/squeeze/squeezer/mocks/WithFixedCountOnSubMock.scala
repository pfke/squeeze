package de.pfke.squeeze.squeezer.mocks

import de.pfke.squeeze.annots.fields.fixedListSize

case class WithFixedCountOnSubMock (
  _1stParam: SubWithFixedCountOnSubMock,
  _2ndParam: Short
)
case class SubWithFixedCountOnSubMock (
  @fixedListSize(4) _1stParam: List[Byte]
)
