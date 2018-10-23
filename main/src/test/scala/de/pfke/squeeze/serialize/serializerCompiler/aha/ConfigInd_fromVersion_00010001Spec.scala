package de.pfke.squeeze.serialize.serializerCompiler.aha

import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class ConfigInd_fromVersion_00010001Spec
  extends BaseSpec {
//  "testing serializer for ConfigInd_fromVersion_00010001 type" when {
//    checkThis[ConfigInd_fromVersion_00010001](
//      code = s"""
//                |import akka.util.{ByteIterator, ByteString}
//                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer, SerializerHints, SerializerRunException}
//                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
//                |import de.pintono.tools.squeeze.zlib.bitString.{BitIterator, BitStringAlignment, BitStringBuilder}
//                |import java.nio.ByteOrder
//                |
//                |class ConfigInd_fromVersion_00010001Serializer
//                |  extends Serializer[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.ConfigInd_fromVersion_00010001] {
//                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.ConfigInd_fromVersion_00010001]
//                |
//                |  override def read(
//                |    serializerContainer: SerializerContainer,
//                |    iter: ByteIterator,
//                |    hints: SerializerHints
//                |  )(implicit byteOrder: ByteOrder, version: Option[PatchLevelVersion]): de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.ConfigInd_fromVersion_00010001 = {
//                |    require(iter.len >= 8, s"[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.ConfigInd_fromVersion_00010001] given input has only $${iter.len} bytes left, but we need 8 byte")
//                |    // read iter
//                |    val deviceId = serializerContainer.read[Short](iter, hints = SerializerHints.none)
//                |    val state = serializerContainer.read[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.enums.DeviceState.DeviceState](iter, hints = SerializerHints(sizeInByte = Some(1)))
//                |    val res1 = serializerContainer.read[Byte](iter, hints = SerializerHints.none)
//                |    val deviceType = serializerContainer.read[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.enums.DeviceType.DeviceType](iter, hints = SerializerHints.none)
//                |    val listenFlags = serializerContainer.read[Int](iter, hints = SerializerHints.none)
//                |    val name = serializerContainer.read[String](iter, hints = SerializerHints(sizeInByte = Some(1)))
//                |    val emc = serializerContainer.read[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.enums.DeviceEMC.DeviceEMC](iter, hints = SerializerHints.none)
//                |    val identifier = serializerContainer.read[String](iter, hints = SerializerHints(sizeInByte = Some(1)))
//                |    val fwVersion = serializerContainer.read[String](iter, hints = SerializerHints(sizeInByte = Some(1)))
//                |    val data = serializerContainer.read[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.Data](iter, hints = SerializerHints.none)
//                |    // create object
//                |    de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.ConfigInd_fromVersion_00010001(
//                |      deviceId = deviceId,
//                |      state = state,
//                |      res1 = res1,
//                |      deviceType = deviceType,
//                |      listenFlags = listenFlags,
//                |      name = name,
//                |      emc = emc,
//                |      identifier = identifier,
//                |      fwVersion = fwVersion,
//                |      data = data
//                |    )
//                |  }
//                |
//                |  override def write(
//                |    serializerContainer: SerializerContainer,
//                |    data: de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.ConfigInd_fromVersion_00010001,
//                |    hints: SerializerHints
//                |  )(implicit byteOrder: ByteOrder, version: Option[PatchLevelVersion]): ByteString = {
//                |    val result = ByteString.newBuilder
//                |
//                |    val _1stBitBuilder = BitStringBuilder.newBuilder(alignment = BitStringAlignment._32Bit)
//                |    serializerContainer.writeBits[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.enums.AhaMessageType.AhaMessageType](_1stBitBuilder, data.mType, hints = SerializerHints(sizeInBits = Some(8)))
//                |    serializerContainer.writeBits[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.enums.AhaMessageSeq.AhaMessageSeq](_1stBitBuilder, data.mSeq, hints = SerializerHints(sizeInBits = Some(8)))
//                |    serializerContainer.writeBits[Short](_1stBitBuilder, data.length, hints = SerializerHints(sizeInBits = Some(16)))
//                |    result.append(_1stBitBuilder.result())
//                |    result.append(serializerContainer.write[Int](data.handle, hints = SerializerHints.none))
//                |    result.append(serializerContainer.write[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.AhaMessageBody](data.body, hints = SerializerHints.none))
//                |
//                |    result.result
//                |  }
//                |}
//                |new AhaMessageSerializer()
//                |""".stripMargin
//    )
//  }
}
