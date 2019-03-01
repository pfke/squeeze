package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.classAnnots.typeForIface

@typeForIface(Map.empty)
case class SubClassB(
  _1stParam: Short
) extends Iface
