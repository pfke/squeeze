package de.pfke.squeeze.squeezer.mocks.asType

import de.pfke.squeeze.annots.classAnnots.fromIfaceToType

@fromIfaceToType(value = 15)
case class SubClassB(
  _1stParam: Short
) extends Iface
