package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.withFixedLength

case class WithFixedLengthOnInvalidTypeMock(
  @withFixedLength(size = 5) _1stParam: Int
)