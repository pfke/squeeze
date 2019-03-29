package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.classAnnots.typeForIface

import scala.collection.immutable.HashMap

@typeForIface(HashMap.empty)
case class NAHISubClassA(
  _1stParam: Int,
  _2ndParam: Short,
  _3rdParam: Int
) extends NotAllHaveIface
