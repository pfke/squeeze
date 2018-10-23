package de.pfke.squeeze.serialize.mocks.annots.classAnnots

import de.pfke.squeeze.annots.classAnnots.fromVersion

trait FromVersion_SubClass_levelPassed

case class FromVersion_SubClass_levelPassed_v00000000 (
  _1stArg: Short
) extends FromVersion_SubClass_levelPassed

@fromVersion(0x0002, 0x0001)
case class FromVersion_SubClass_levelPassed_v00020001 (
  _1stArg: Short
) extends FromVersion_SubClass_levelPassed
