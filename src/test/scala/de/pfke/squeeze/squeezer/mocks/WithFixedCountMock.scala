package de.pfke.squeeze.squeezer.mocks

import de.pfke.squeeze.annots.fields.fixedListSize

case class WithFixedCountMock (
  @fixedListSize(3) _1stParam: List[Int]
)
