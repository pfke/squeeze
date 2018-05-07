package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.complex

import de.pfke.squeeze.squeezer.mocks.WithComplexSubTypesMock
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class WithComplexSubTypesBuilderSpec
  extends BaseSpec {
  "testing serializer for WithComplexSubTypes type" when {
    checkThis[WithComplexSubTypesMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class WithComplexSubTypesMockSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.WithComplexSubTypesMock] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[de.pintono.tools.squeeze.core.mocks.WithComplexSubTypesMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.WithComplexSubTypesMock = {
                |    require(iter.len.toByte >= 37, s"[de.pintono.tools.squeeze.core.mocks.WithComplexSubTypesMock] given input has only $${iter.len} bytes left, but we need 37 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[Byte](iter)
                |    val _2ndParam = serializerContainer.read[de.pintono.tools.squeeze.core.mocks.SubWithComplexSubTypesMock](iter)
                |    val _3rdParam = serializerContainer.read[Double](iter)
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.WithComplexSubTypesMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam,
                |      _3rdParam = _3rdParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.WithComplexSubTypesMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.WithComplexSubTypesMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Byte](data._1stParam, hints = hints:_*)
                |    serializerContainer.write[de.pintono.tools.squeeze.core.mocks.SubWithComplexSubTypesMock](data._2ndParam, hints = hints:_*)
                |    serializerContainer.write[Double](data._3rdParam, hints = hints:_*)
                |  }
                |}
                |new WithComplexSubTypesMockSerializer()
                |""".stripMargin
    )
  }
}
