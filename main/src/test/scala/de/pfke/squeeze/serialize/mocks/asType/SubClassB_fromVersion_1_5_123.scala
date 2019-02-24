package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.classAnnots.{fromVersion, typeForIface}

@typeForIface(ident = 15)
@fromVersion(major = 1, minor = 5, level = 123)
case class SubClassB_fromVersion_1_5_123(
  _1stParam: Short,
  _2ndParam: Short
) extends Iface
