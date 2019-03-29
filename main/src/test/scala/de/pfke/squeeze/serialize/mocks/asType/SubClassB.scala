package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.classAnnots.typeForIface

import scala.collection.immutable.HashMap

@typeForIface(HashMap.empty)
case class SubClassB(
  _1stParam: Short
) extends Iface
