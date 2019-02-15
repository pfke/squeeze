package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.fieldAnnots.withFixedSize

case class WithFixedLengthOnInvalidTypeMock(
  @withFixedSize(size = 5) _1stParam: Int
)