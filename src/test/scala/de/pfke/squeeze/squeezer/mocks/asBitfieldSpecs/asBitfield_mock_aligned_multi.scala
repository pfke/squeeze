package de.pfke.squeeze.squeezer.mocks.asBitfieldSpecs

import de.pfke.squeeze.annots.classAnnots.alignBitfieldsBy
import de.pfke.squeeze.annots.fields.asBitfield

@alignBitfieldsBy(bits = 32)
case class asBitfield_mock_aligned_multi(
  @asBitfield(bits = 8) field01: Byte,
  @asBitfield(bits = 16) field02: Short,
  @asBitfield(bits = 8) field03: Byte,
  @asBitfield(bits = 32) field04: Int
)
