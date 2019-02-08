package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.classAnnots.typeForIface
import de.pfke.squeeze.annots.withFixedSize

@typeForIface(value = 17)
case class SubClassC(
                              _1stParam: Byte,
  @withFixedSize(size = 5) _2ndParam : String
) extends Iface
