package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.withFixedCount

case class WithFixedCountMock (
  @withFixedCount(3) _1stParam: List[Int]
)
