package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.classAnnots.{fromVersion, typeForIface}
import de.pfke.squeeze.annots.withFixedLength
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums.DeviceEMC.DeviceEMC
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums.DeviceState.DeviceState
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums.DeviceType.DeviceType
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums.GroupAction
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.enums.GroupAction.GroupAction

trait ConfigInd
  extends AhaMessageBody

@typeForIface(0x4)
case class ConfigInd_fromVersion_00010001(
                         deviceId: Short,
  @withFixedLength(1)       state: DeviceState,
                             res1: Byte,
                       deviceType: DeviceType,
                      listenFlags: Int,
  @withFixedLength(80)       name: String,
                              emc: DeviceEMC,
  @withFixedLength(20) identifier: String,
  @withFixedLength(20)  fwVersion: String,
                             data: Data
) extends ConfigInd

@fromVersion(0x0002, 0x1000)
@typeForIface(0x4)
case class ConfigInd_fromVersion_00021000(
                          deviceId: Short,
  @withFixedLength(1)        state: DeviceState,
                              res1: Byte,
                        deviceType: DeviceType,
                       listenFlags: Int,
  @withFixedLength(80)        name: String,
                               emc: DeviceEMC,
  @withFixedLength(20)  identifier: String,
  @withFixedLength(20)   fwVersion: String,
                         groupHash: Int = 0,
  @withFixedLength(4) group_action: GroupAction = GroupAction.NOP,
                              data: Data
) extends ConfigInd
