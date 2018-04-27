package de.pfke.squeeze.squeezer.mocks

import de.pfke.squeeze.annots.fields.fixedLength

case class WithFixedLengthOnInvalidTypeMock(
  @fixedLength(size = 5) _1stParam: Int
)