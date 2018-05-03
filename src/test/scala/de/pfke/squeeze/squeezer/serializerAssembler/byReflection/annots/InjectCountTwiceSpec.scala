package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.annots

import de.pfke.squeeze.squeezer.mocks.InjectCountTwiceMock
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class InjectCountTwiceSpec
  extends BaseSpec {
  "testing serializer for InjectCountTwiceMock type" when {
    checkThis[InjectCountTwiceMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class InjectCountTwiceMockSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.InjectCountTwiceMock] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[de.pintono.tools.squeeze.core.mocks.InjectCountTwiceMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.InjectCountTwiceMock = {
                |    require(iter.len.toByte >= 4, s"[de.pintono.tools.squeeze.core.mocks.InjectCountTwiceMock] given input has only $${iter.len} bytes left, but we need 4 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[Short](iter)
                |    val _2ndParam = serializerContainer.read[Short](iter)
                |    val _3rdParam = (0 until _2ndParam).map { _ => serializerContainer.read[Byte](iter) }.toList
                |    val _4thParam = (0 until _1stParam).map { _ => serializerContainer.read[Byte](iter) }.toList
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.InjectCountTwiceMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam,
                |      _3rdParam = _3rdParam,
                |      _4thParam = _4thParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.InjectCountTwiceMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.InjectCountTwiceMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Short](data._4thParam.size.toShort, hints = hints:_*)
                |    serializerContainer.write[Short](data._3rdParam.size.toShort, hints = hints:_*)
                |    data._3rdParam.foreach { i => serializerContainer.write[Byte](i, hints = hints:_*) }
                |    data._4thParam.foreach { i => serializerContainer.write[Byte](i, hints = hints:_*) }
                |  }
                |}
                |new InjectCountTwiceMockSerializer()
                |""".stripMargin
    )
  }
}
