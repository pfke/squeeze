package de.pfke.squeeze.testing.mocks.asType

import de.pfke.squeeze.annots.classAnnots.{fromIfaceToType, fromVersion}

@fromIfaceToType(value = 15)
@fromVersion(major = 1, minor = 5, patch = 124)
case class SubClassB_fromVersion_1_5_124(
  _1stParam: Short,
  _2ndParam: Short,
  _3rdParam: Short
) extends Iface
