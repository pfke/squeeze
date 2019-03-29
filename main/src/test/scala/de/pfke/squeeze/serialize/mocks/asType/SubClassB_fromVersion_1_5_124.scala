package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.classAnnots.{fromVersion, typeForIface}

import scala.collection.immutable.HashMap

@typeForIface(HashMap.empty)
@fromVersion(major = 1, minor = 5, level = 124)
case class SubClassB_fromVersion_1_5_124(
  _1stParam: Short,
  _2ndParam: Short,
  _3rdParam: Short
) extends Iface
