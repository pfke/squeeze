package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.annots

import de.pfke.squeeze.squeezer.mocks.WithFixedCountOnSubMock
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class WithFixedCountOnSubSpec
  extends BaseSpec {
  "testing serializer for WithFixedCountOnSubMock type" when {
    checkThis[WithFixedCountOnSubMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class WithFixedCountOnSubMockSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.WithFixedCountOnSubMock] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[de.pintono.tools.squeeze.core.mocks.WithFixedCountOnSubMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.WithFixedCountOnSubMock = {
                |    require(iter.len.toByte >= 6, s"[de.pintono.tools.squeeze.core.mocks.WithFixedCountOnSubMock] given input has only $${iter.len} bytes left, but we need 6 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[de.pintono.tools.squeeze.core.mocks.SubWithFixedCountOnSubMock](iter)
                |    val _2ndParam = serializerContainer.read[Short](iter)
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.WithFixedCountOnSubMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.WithFixedCountOnSubMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.WithFixedCountOnSubMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[de.pintono.tools.squeeze.core.mocks.SubWithFixedCountOnSubMock](data._1stParam, hints = hints:_*)
                |    serializerContainer.write[Short](data._2ndParam, hints = hints:_*)
                |  }
                |}
                |new WithFixedCountOnSubMockSerializer()
                |""".stripMargin
    )
  }
}
