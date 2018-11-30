package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots

import de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthOnSubMock
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class InjectTotalLengthOnSubSpec
  extends BaseSpec {
  "testing serializer for InjectTotalLengthOnSubMock type" when {
    checkThis[InjectTotalLengthOnSubMock](
      code = s"""
                |$baseImports
                |
                |class InjectTotalLengthOnSubMockSerializer
                |  extends CompiledSerializer[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthOnSubMock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthOnSubMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthOnSubMock = {
                |    require(iter.len.toByte >= 10, s"[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthOnSubMock] given input has only $${iter.len} left, but we need 10 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[de.pfke.squeeze.serialize.mocks.annots.SubInjectTotalLengthOnSubMock](iter)
                |    val _2ndParam = serializerContainer.read[Short](iter)
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthOnSubMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthOnSubMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthOnSubMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[de.pfke.squeeze.serialize.mocks.annots.SubInjectTotalLengthOnSubMock](data._1stParam, hints = hints:_*)
                |    serializerContainer.write[Short](SizeOf.guesso[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthOnSubMock](data).toByte.toShort, hints = hints:_*)
                |  }
                |}
                |new InjectTotalLengthOnSubMockSerializer()
                |""".stripMargin
    )
  }
}
