package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots

import de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthMock
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class InjectTotalLengthSpec
  extends BaseSpec {
  "testing serializer for InjectTotalLengthMock type" when {
    checkThis[InjectTotalLengthMock](
      code = s"""
                |$baseImports
                |
                |class InjectTotalLengthMockSerializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthMock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthMock = {
                |    require(iter.len.toByte >= 8, s"[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthMock] given input has only $${iter.len} bytes left, but we need 8 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[Short](iter)
                |    val _2ndParam = serializerContainer.read[Short](iter)
                |    val _3rdParam = serializerContainer.read[Int](iter)
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam,
                |      _3rdParam = _3rdParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLengthMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Short](data._1stParam, hints = hints:_*)
                |    serializerContainer.write[Short](8, hints = hints:_*)
                |    serializerContainer.write[Int](data._3rdParam, hints = hints:_*)
                |  }
                |}
                |new InjectTotalLengthMockSerializer()
                |""".stripMargin
    )
  }
}
