package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.annots

import de.pfke.squeeze.squeezer.mocks.WithFixedCountMock
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class WithFixedCountSpec
  extends BaseSpec {
  "testing serializer for WithFixedCountMock type" when {
    checkThis[WithFixedCountMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class WithFixedCountMockSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.WithFixedCountMock] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[de.pintono.tools.squeeze.core.mocks.WithFixedCountMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.WithFixedCountMock = {
                |    require(iter.len.toByte >= 12, s"[de.pintono.tools.squeeze.core.mocks.WithFixedCountMock] given input has only $${iter.len} bytes left, but we need 12 byte")
                |    // read iter
                |    val _1stParam = (0 until 3).map { _ => serializerContainer.read[Int](iter) }.toList
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.WithFixedCountMock(
                |      _1stParam = _1stParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.WithFixedCountMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.WithFixedCountMock] given input has no ByteStringBuilderHint")
                |    data._1stParam.foreach { i => serializerContainer.write[Int](i, hints = hints:_*) }
                |  }
                |}
                |new WithFixedCountMockSerializer()
                |""".stripMargin
    )
  }
}
