package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.classAnnots.typeForIface

import scala.collection.immutable.HashMap

@typeForIface(7)
case class SubClassA(
  _1stParam: Int,
  _2ndParam: Short,
  _3rdParam: Int
) extends Iface
