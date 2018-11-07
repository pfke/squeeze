package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex

import de.pfke.squeeze.serialize.mocks.WithComplexSubTypeMockWithOneWriteOp
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class WithComplexSubTypeWithOneWriteOpBuilderSpec
  extends BaseSpec {
  "testing serializer for WithComplexSubTypeWithOneWriteOp type" when {
    checkThis[WithComplexSubTypeMockWithOneWriteOp](
      code = s"""
                |$baseImports
                |
                |class WithComplexSubTypeMockWithOneWriteOpSerializer
                |  extends CompiledSerializer[de.pfke.squeeze.serialize.mocks.WithComplexSubTypeMockWithOneWriteOp] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.WithComplexSubTypeMockWithOneWriteOp]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.WithComplexSubTypeMockWithOneWriteOp = {
                |    require(iter.len.toByte >= 1, s"[de.pfke.squeeze.serialize.mocks.WithComplexSubTypeMockWithOneWriteOp] given input has only $${iter.len} bytes left, but we need 1 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[de.pfke.squeeze.serialize.mocks.SubWithComplexSubTypeMockWithOneWriteOp](iter)
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.WithComplexSubTypeMockWithOneWriteOp(
                |      _1stParam = _1stParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.WithComplexSubTypeMockWithOneWriteOp,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.WithComplexSubTypeMockWithOneWriteOp] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[de.pfke.squeeze.serialize.mocks.SubWithComplexSubTypeMockWithOneWriteOp](data._1stParam, hints = hints:_*)
                |  }
                |}
                |new WithComplexSubTypeMockWithOneWriteOpSerializer()
                |""".stripMargin
    )
  }
}
