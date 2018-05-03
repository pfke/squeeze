package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.annots

import de.pfke.squeeze.squeezer.mocks.WithFixedLengthMock
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class WithFixedLengthSpec
  extends BaseSpec {
  "testing serializer for WithFixedLengthMock type" when {
    checkThis[WithFixedLengthMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class WithFixedLengthMockSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.WithFixedLengthMock] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[de.pintono.tools.squeeze.core.mocks.WithFixedLengthMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.WithFixedLengthMock = {
                |    require(iter.len.toByte >= 5, s"[de.pintono.tools.squeeze.core.mocks.WithFixedLengthMock] given input has only $${iter.len} bytes left, but we need 5 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[String](iter, hints = SizeInByteHint(value = 5))
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.WithFixedLengthMock(
                |      _1stParam = _1stParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.WithFixedLengthMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.WithFixedLengthMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[String](data._1stParam, hints = hints ++ Seq(SizeInByteHint(value = 5)):_*)
                |  }
                |}
                |new WithFixedLengthMockSerializer()
                |""".stripMargin
    )
  }
}
