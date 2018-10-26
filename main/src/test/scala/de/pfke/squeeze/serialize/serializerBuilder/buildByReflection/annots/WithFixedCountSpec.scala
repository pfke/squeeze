package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots

import de.pfke.squeeze.serialize.mocks.annots.WithFixedCountMock
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class WithFixedCountSpec
  extends BaseSpec {
  "testing serializer for WithFixedCountMock type" when {
    checkThis[WithFixedCountMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, GeneralRefl}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class WithFixedCountMockSerializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.WithFixedCountMock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.WithFixedCountMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.WithFixedCountMock = {
                |    require(iter.len.toByte >= 12, s"[de.pfke.squeeze.serialize.mocks.WithFixedCountMock] given input has only $${iter.len} bytes left, but we need 12 byte")
                |    // read iter
                |    val _1stParam = (0 until 3).map { _ => serializerContainer.read[Int](iter) }.toList
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.WithFixedCountMock(
                |      _1stParam = _1stParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.WithFixedCountMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.WithFixedCountMock] given input has no ByteStringBuilderHint")
                |    data._1stParam.foreach { i => serializerContainer.write[Int](i, hints = hints:_*) }
                |  }
                |}
                |new WithFixedCountMockSerializer()
                |""".stripMargin
    )
  }
}
