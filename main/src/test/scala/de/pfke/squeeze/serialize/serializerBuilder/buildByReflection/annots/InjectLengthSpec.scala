package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots

import de.pfke.squeeze.serialize.mocks.annots.InjectLengthMock
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class InjectLengthSpec
  extends BaseSpec {
  "testing serializer for InjectLengthMock type" when {
    checkThis[InjectLengthMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, GeneralRefl}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class InjectLengthMockSerializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.InjectLengthMock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.InjectLengthMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.InjectLengthMock = {
                |    require(iter.len.toByte >= 2, s"[de.pfke.squeeze.serialize.mocks.InjectLengthMock] given input has only $${iter.len} bytes left, but we need 2 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[Short](iter)
                |    val _2ndParam = serializerContainer.read[String](iter, hints = SizeInByteHint(value = _1stParam))
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.InjectLengthMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.InjectLengthMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.InjectLengthMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Short](data._2ndParam.length.toShort, hints = hints:_*)
                |    serializerContainer.write[String](data._2ndParam, hints = hints:_*)
                |  }
                |}
                |new InjectLengthMockSerializer()
                |""".stripMargin
    )
  }
}
