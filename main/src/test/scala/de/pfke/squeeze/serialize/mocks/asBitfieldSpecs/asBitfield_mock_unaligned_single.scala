package de.pfke.squeeze.serialize.mocks.asBitfieldSpecs

import de.pfke.squeeze.annots.classAnnots.alignBitfieldsBy
import de.pfke.squeeze.annots.fieldAnnots.asBitfield

@alignBitfieldsBy(bits = 16)
case class asBitfield_mock_unaligned_single(
  @asBitfield(bits = 2) field01: Int
)
