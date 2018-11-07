package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex

import de.pfke.squeeze.serialize.mocks.WithComplexSubTypesMock
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class WithComplexSubTypesBuilderSpec
  extends BaseSpec {
  "testing serializer for WithComplexSubTypes type" when {
    checkThis[WithComplexSubTypesMock](
      code = s"""
                |$baseImports
                |
                |class WithComplexSubTypesMockSerializer
                |  extends CompiledSerializer[de.pfke.squeeze.serialize.mocks.WithComplexSubTypesMock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.WithComplexSubTypesMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.WithComplexSubTypesMock = {
                |    require(iter.len.toByte >= 37, s"[de.pfke.squeeze.serialize.mocks.WithComplexSubTypesMock] given input has only $${iter.len} bytes left, but we need 37 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[Byte](iter)
                |    val _2ndParam = serializerContainer.read[de.pfke.squeeze.serialize.mocks.SubWithComplexSubTypesMock](iter)
                |    val _3rdParam = serializerContainer.read[Double](iter)
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.WithComplexSubTypesMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam,
                |      _3rdParam = _3rdParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.WithComplexSubTypesMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.WithComplexSubTypesMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Byte](data._1stParam, hints = hints:_*)
                |    serializerContainer.write[de.pfke.squeeze.serialize.mocks.SubWithComplexSubTypesMock](data._2ndParam, hints = hints:_*)
                |    serializerContainer.write[Double](data._3rdParam, hints = hints:_*)
                |  }
                |}
                |new WithComplexSubTypesMockSerializer()
                |""".stripMargin
    )
  }
}
