package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.withFixedCount

case class WithFixedCountOnSubMock (
  _1stParam: SubWithFixedCountOnSubMock,
  _2ndParam: Short
)
case class SubWithFixedCountOnSubMock (
  @withFixedCount(4) _1stParam: List[Byte]
)
