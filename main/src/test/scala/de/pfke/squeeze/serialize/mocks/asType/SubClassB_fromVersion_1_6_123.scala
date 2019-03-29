package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.classAnnots.{fromVersion, typeForIface}

import scala.collection.immutable.HashMap

@typeForIface(HashMap.empty)
@fromVersion(major = 1, minor = 6, level = 123)
case class SubClassB_fromVersion_1_6_123(
  _1stParam: Short,
  _2ndParam: Short,
  _3rdParam: Short,
  _4thParam: Short
) extends Iface
