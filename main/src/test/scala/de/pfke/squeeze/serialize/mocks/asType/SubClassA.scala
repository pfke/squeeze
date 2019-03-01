package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.classAnnots.typeForIface

@typeForIface(Map.empty)
case class SubClassA(
  _1stParam: Int,
  _2ndParam: Short,
  _3rdParam: Int
) extends Iface
