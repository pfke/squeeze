package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots

import de.pfke.squeeze.serialize.mocks.annots.InjectCountMock
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class InjectCountSpec
  extends BaseSpec {
  "testing serializer for InjectCountMock type" when {
    checkThis[InjectCountMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, GeneralRefl}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class InjectCountMockSerializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.InjectCountMock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.InjectCountMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.InjectCountMock = {
                |    require(iter.len.toByte >= 6, s"[de.pfke.squeeze.serialize.mocks.InjectCountMock] given input has only $${iter.len} bytes left, but we need 6 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[Short](iter)
                |    val _2ndParam = (0 until _1stParam).map { _ => serializerContainer.read[Byte](iter) }.toList
                |    val _3rdParam = serializerContainer.read[Int](iter)
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.InjectCountMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam,
                |      _3rdParam = _3rdParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.InjectCountMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.InjectCountMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Short](data._2ndParam.size.toShort, hints = hints:_*)
                |    data._2ndParam.foreach { i => serializerContainer.write[Byte](i, hints = hints:_*) }
                |    serializerContainer.write[Int](data._2ndParam.size.toInt, hints = hints:_*)
                |  }
                |}
                |new InjectCountMockSerializer()
                |""".stripMargin
    )
  }
}
