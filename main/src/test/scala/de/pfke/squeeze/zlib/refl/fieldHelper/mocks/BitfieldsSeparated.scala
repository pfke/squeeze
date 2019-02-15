package de.pfke.squeeze.zlib.refl.fieldHelper.mocks

import de.pfke.squeeze.annots.classAnnots.alignBitfieldsBy
import de.pfke.squeeze.annots.fieldAnnots.asBitfield

@alignBitfieldsBy(bits = 32)
case class BitfieldsSeparated(
  @asBitfield(bits = 8) field01: Byte,
  @asBitfield(bits = 16) field02: Short,
  @asBitfield(bits = 8) field03: Byte,
  @asBitfield(bits = 32) field04: Int,
  @asBitfield(bits = 8) field05: Int,
  @asBitfield(bits = 8) field06: Int,
  field07: Short,
  @asBitfield(bits = 8) field08: Byte,
  field09: Int
)
