package de.pfke.squeeze.serialize.serializerCompiler.aha

import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class AhaMessageSpec
  extends BaseSpec {
//  "testing serializer for AhaMessage type" when {
//    checkThis[AhaMessage](
//      code = s"""
//                |import akka.util.{ByteIterator, ByteString}
//                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer, SerializerHints, SerializerRunException}
//                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
//                |import de.pintono.tools.squeeze.zlib.bitString.{BitIterator, BitStringAlignment, BitStringBuilder}
//                |import java.nio.ByteOrder
//                |
//                |class AhaMessageSerializer
//                |  extends Serializer[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.AhaMessage] {
//                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.AhaMessage]
//                |
//                |  override def read(
//                |    serializerContainer: SerializerContainer,
//                |    iter: ByteIterator,
//                |    hints: SerializerHints
//                |  )(implicit byteOrder: ByteOrder, version: Option[PatchLevelVersion]): de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.AhaMessage = {
//                |    require(iter.len >= 8, s"[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.AhaMessage] given input has only $${iter.len} bytes left, but we need 8 byte")
//                |    // read iter
//                |    val _1stBitIter = BitIterator(iter, BitStringAlignment._32Bit)
//                |    val mType = serializerContainer.readBits[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.enums.AhaMessageType.AhaMessageType](_1stBitIter, hints = SerializerHints(sizeInBits = Some(8)))
//                |    val mSeq = serializerContainer.readBits[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.enums.AhaMessageSeq.AhaMessageSeq](_1stBitIter, hints = SerializerHints(sizeInBits = Some(8)))
//                |    val length = serializerContainer.readBits[Short](_1stBitIter, hints = SerializerHints(sizeInBits = Some(16)))
//                |    val handle = serializerContainer.read[Int](iter, hints = SerializerHints.none)
//                |    val body = serializerContainer.read[de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.AhaMessageBody](iter, hints = SerializerHints(ifaceType = Some(mType)))
//                |    // create object
//                |    de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.AhaMessage(
//                |      mType = mType,
//                |      mSeq = mSeq,
//                |      length = length,
//                |      handle = handle,
//                |      body = body
//                |    )
//                |  }
//                |
//                |  override def write(
//                |    serializerContainer: SerializerContainer,
//                |    data: de.pintono.tools.squeeze.core.serializerCompiler.aha.messages.AhaMessage,
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
