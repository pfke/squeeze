package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.classAnnots.{fromVersion, typeForIface}

@typeForIface(Map.empty)
@fromVersion(major = 2, minor = 5, level = 123)
case class SubClassB_fromVersion_2_5_123(
  _1stParam: Short,
  _2ndParam: Short,
  _3rdParam: Short,
  _4thParam: Short,
  _5thParam: Short
) extends Iface
