package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots

import de.pfke.squeeze.serialize.mocks.annots.WithFixedCountOnSubMock
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class WithFixedCountOnSubSpec
  extends BaseSpec {
  "testing serializer for WithFixedCountOnSubMock type" when {
    checkThis[WithFixedCountOnSubMock](
      code = s"""
                |$baseImports
                |
                |class WithFixedCountOnSubMockSerializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.annots.WithFixedCountOnSubMock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.annots.WithFixedCountOnSubMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.annots.WithFixedCountOnSubMock = {
                |    require(iter.len.toByte >= 6, s"[de.pfke.squeeze.serialize.mocks.annots.WithFixedCountOnSubMock] given input has only $${iter.len} bytes left, but we need 6 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[de.pfke.squeeze.serialize.mocks.annots.SubWithFixedCountOnSubMock](iter)
                |    val _2ndParam = serializerContainer.read[Short](iter)
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.annots.WithFixedCountOnSubMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.annots.WithFixedCountOnSubMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.annots.WithFixedCountOnSubMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[de.pfke.squeeze.serialize.mocks.annots.SubWithFixedCountOnSubMock](data._1stParam, hints = hints:_*)
                |    serializerContainer.write[Short](data._2ndParam, hints = hints:_*)
                |  }
                |}
                |new WithFixedCountOnSubMockSerializer()
                |""".stripMargin
    )
  }
}
