package de.pfke.squeeze.squeezer.mocks.asType

import de.pfke.squeeze.annots.classAnnots.{fromIfaceToType, fromVersion}

@fromIfaceToType(value = 15)
@fromVersion(major = 1, minor = 5, patch = 123)
case class SubClassB_fromVersion_1_5_123(
  _1stParam: Short,
  _2ndParam: Short
) extends Iface
