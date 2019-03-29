package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.classAnnots.typeForIface
import de.pfke.squeeze.annots.fieldAnnots.withFixedSize

import scala.collection.immutable.HashMap

@typeForIface(HashMap.empty)
case class SubClassC(
                              _1stParam: Byte,
  @withFixedSize(size = 5) _2ndParam : String
) extends Iface
