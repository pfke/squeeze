package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.annots

import de.pfke.squeeze.squeezer.mocks.InjectLengthMock
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class InjectLengthSpec
  extends BaseSpec {
  "testing serializer for InjectLengthMock type" when {
    checkThis[InjectLengthMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class InjectLengthMockSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.InjectLengthMock] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[de.pintono.tools.squeeze.core.mocks.InjectLengthMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.InjectLengthMock = {
                |    require(iter.len.toByte >= 2, s"[de.pintono.tools.squeeze.core.mocks.InjectLengthMock] given input has only $${iter.len} bytes left, but we need 2 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[Short](iter)
                |    val _2ndParam = serializerContainer.read[String](iter, hints = SizeInByteHint(value = _1stParam))
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.InjectLengthMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.InjectLengthMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.InjectLengthMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Short](data._2ndParam.length.toShort, hints = hints:_*)
                |    serializerContainer.write[String](data._2ndParam, hints = hints:_*)
                |  }
                |}
                |new InjectLengthMockSerializer()
                |""".stripMargin
    )
  }
}
