package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.complex

import de.pfke.squeeze.squeezer.mocks.WithComplexSubTypeMockWithOneWriteOp
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class WithComplexSubTypeWithOneWriteOpBuilderSpec
  extends BaseSpec {
  "testing serializer for WithComplexSubTypeWithOneWriteOp type" when {
    checkThis[WithComplexSubTypeMockWithOneWriteOp](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class WithComplexSubTypeMockWithOneWriteOpSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.WithComplexSubTypeMockWithOneWriteOp] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[de.pintono.tools.squeeze.core.mocks.WithComplexSubTypeMockWithOneWriteOp]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.WithComplexSubTypeMockWithOneWriteOp = {
                |    require(iter.len.toByte >= 1, s"[de.pintono.tools.squeeze.core.mocks.WithComplexSubTypeMockWithOneWriteOp] given input has only $${iter.len} bytes left, but we need 1 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[de.pintono.tools.squeeze.core.mocks.SubWithComplexSubTypeMockWithOneWriteOp](iter)
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.WithComplexSubTypeMockWithOneWriteOp(
                |      _1stParam = _1stParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.WithComplexSubTypeMockWithOneWriteOp,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.WithComplexSubTypeMockWithOneWriteOp] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[de.pintono.tools.squeeze.core.mocks.SubWithComplexSubTypeMockWithOneWriteOp](data._1stParam, hints = hints:_*)
                |  }
                |}
                |new WithComplexSubTypeMockWithOneWriteOpSerializer()
                |""".stripMargin
    )
  }
}
