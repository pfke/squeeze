package de.pfke.squeeze.serialize.serializerAssembler.byReflection.annots

import de.pfke.squeeze.testing.mocks.InjectTotalLengthOnSubMock
import de.pfke.squeeze.serialize.serializerAssembler.byReflection.BaseSpec

class InjectTotalLengthOnSubSpec
  extends BaseSpec {
  "testing serializer for InjectTotalLengthOnSubMock type" when {
    checkThis[InjectTotalLengthOnSubMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class InjectTotalLengthOnSubMockSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.InjectTotalLengthOnSubMock] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[de.pintono.tools.squeeze.core.mocks.InjectTotalLengthOnSubMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.InjectTotalLengthOnSubMock = {
                |    require(iter.len.toByte >= 10, s"[de.pintono.tools.squeeze.core.mocks.InjectTotalLengthOnSubMock] given input has only $${iter.len} bytes left, but we need 10 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[de.pintono.tools.squeeze.core.mocks.SubInjectTotalLengthOnSubMock](iter)
                |    val _2ndParam = serializerContainer.read[Short](iter)
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.InjectTotalLengthOnSubMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.InjectTotalLengthOnSubMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.InjectTotalLengthOnSubMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[de.pintono.tools.squeeze.core.mocks.SubInjectTotalLengthOnSubMock](data._1stParam, hints = hints:_*)
                |    serializerContainer.write[Short](10, hints = hints:_*)
                |  }
                |}
                |new InjectTotalLengthOnSubMockSerializer()
                |""".stripMargin
    )
  }
}
