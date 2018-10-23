package de.pfke.squeeze.serialize.mocks.annots.classAnnots

import de.pfke.squeeze.annots.classAnnots.fromVersion

trait FromVersion_SubClass_noLevelPassed

case class FromVersion_SubClass_noLevelPassed_v00000000 (
  _1stArg: Short
) extends FromVersion_SubClass_noLevelPassed

@fromVersion(0x0002, 0x0001)
case class FromVersion_SubClass_noLevelPassed_v00020001 (
  _1stArg: Short
) extends FromVersion_SubClass_noLevelPassed
