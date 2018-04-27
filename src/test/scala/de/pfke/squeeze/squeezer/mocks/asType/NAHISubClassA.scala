package de.pfke.squeeze.squeezer.mocks.asType

import de.pfke.squeeze.annots.classAnnots.fromIfaceToType

@fromIfaceToType(value = 10)
case class NAHISubClassA(
  _1stParam: Int,
  _2ndParam: Short,
  _3rdParam: Int
) extends NotAllHaveIface
