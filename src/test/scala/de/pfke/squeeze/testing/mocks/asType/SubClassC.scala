package de.pfke.squeeze.testing.mocks.asType

import de.pfke.squeeze.annots.classAnnots.fromIfaceToType
import de.pfke.squeeze.annots.fields.fixedLength

@fromIfaceToType(value = 17)
case class SubClassC(
                              _1stParam: Byte,
  @fixedLength(size = 5) _2ndParam: String
) extends Iface
