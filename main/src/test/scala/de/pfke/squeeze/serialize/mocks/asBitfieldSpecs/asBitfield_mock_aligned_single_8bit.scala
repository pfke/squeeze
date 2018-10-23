package de.pfke.squeeze.serialize.mocks.asBitfieldSpecs

import de.pfke.squeeze.annots.{alignBitfieldsBy, asBitfield}

@alignBitfieldsBy(bits = 8)
case class asBitfield_mock_aligned_single_8bit(
  @asBitfield(bits = 8) field01: Int
)
