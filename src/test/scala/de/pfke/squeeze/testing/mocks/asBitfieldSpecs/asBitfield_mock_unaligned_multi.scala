package de.pfke.squeeze.testing.mocks.asBitfieldSpecs

import de.pfke.squeeze.annots.classAnnots.alignBitfieldsBy
import de.pfke.squeeze.annots.fields.asBitfield

@alignBitfieldsBy(bits = 16)
case class asBitfield_mock_unaligned_multi(
  @asBitfield(bits = 2) field01: Int,
  @asBitfield(bits = 3) field02: Int
)
