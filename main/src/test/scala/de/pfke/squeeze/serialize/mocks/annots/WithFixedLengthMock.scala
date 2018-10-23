package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.withFixedLength

case class WithFixedLengthMock(
  @withFixedLength(bytes = 5) _1stParam: String
)