package de.pfke.squeeze.squeezer.serializerBuilder.byReflection.annots

import de.pfke.squeeze.squeezer.mocks.InjectCountOnSubMock
import de.pfke.squeeze.squeezer.serializerBuilder.byReflection.BaseSpec

class InjectCountOnSubSpec
  extends BaseSpec {
  "testing serializer for InjectCountOnSubMock type" when {
    checkThis[InjectCountOnSubMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class InjectCountOnSubMockSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.InjectCountOnSubMock] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[de.pintono.tools.squeeze.core.mocks.InjectCountOnSubMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.InjectCountOnSubMock = {
                |    require(iter.len.toByte >= 6, s"[de.pintono.tools.squeeze.core.mocks.InjectCountOnSubMock] given input has only $${iter.len} bytes left, but we need 6 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[de.pintono.tools.squeeze.core.mocks.SubInjectCountOnSubMock](iter)
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.InjectCountOnSubMock(
                |      _1stParam = _1stParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.InjectCountOnSubMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.InjectCountOnSubMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[de.pintono.tools.squeeze.core.mocks.SubInjectCountOnSubMock](data._1stParam, hints = hints:_*)
                |  }
                |}
                |new InjectCountOnSubMockSerializer()
                |""".stripMargin
    )
  }
}
