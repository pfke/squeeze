package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots

import de.pfke.squeeze.serialize.mocks.annots.InjectCountOnSubMock
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class InjectCountOnSubSpec
  extends BaseSpec {
  "testing serializer for InjectCountOnSubMock type" when {
    checkThis[InjectCountOnSubMock](
      code = s"""
                |$baseImports
                |
                |class InjectCountOnSubMockSerializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.annots.InjectCountOnSubMock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.annots.InjectCountOnSubMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.annots.InjectCountOnSubMock = {
                |    require(iter.len.toByte >= 6, s"[de.pfke.squeeze.serialize.mocks.annots.InjectCountOnSubMock] given input has only $${iter.len} bytes left, but we need 6 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[de.pfke.squeeze.serialize.mocks.annots.SubInjectCountOnSubMock](iter)
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.annots.InjectCountOnSubMock(
                |      _1stParam = _1stParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.annots.InjectCountOnSubMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.annots.InjectCountOnSubMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[de.pfke.squeeze.serialize.mocks.annots.SubInjectCountOnSubMock](data._1stParam, hints = hints:_*)
                |  }
                |}
                |new InjectCountOnSubMockSerializer()
                |""".stripMargin
    )
  }
}
