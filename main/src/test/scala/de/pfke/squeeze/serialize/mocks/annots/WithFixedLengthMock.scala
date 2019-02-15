package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.fieldAnnots.withFixedSize

case class WithFixedLengthMock(
  @withFixedSize(size = 5) _1stParam: String
)