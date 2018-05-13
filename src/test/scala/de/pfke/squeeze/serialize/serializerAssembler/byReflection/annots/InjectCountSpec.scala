package de.pfke.squeeze.serialize.serializerAssembler.byReflection.annots

import de.pfke.squeeze.testing.mocks.InjectCountMock
import de.pfke.squeeze.serialize.serializerAssembler.byReflection.BaseSpec

class InjectCountSpec
  extends BaseSpec {
  "testing serializer for InjectCountMock type" when {
    checkThis[InjectCountMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class InjectCountMockSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.InjectCountMock] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[de.pintono.tools.squeeze.core.mocks.InjectCountMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.InjectCountMock = {
                |    require(iter.len.toByte >= 6, s"[de.pintono.tools.squeeze.core.mocks.InjectCountMock] given input has only $${iter.len} bytes left, but we need 6 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[Short](iter)
                |    val _2ndParam = (0 until _1stParam).map { _ => serializerContainer.read[Byte](iter) }.toList
                |    val _3rdParam = serializerContainer.read[Int](iter)
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.InjectCountMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam,
                |      _3rdParam = _3rdParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.InjectCountMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.InjectCountMock] given input has no ByteStringBuilderHint")
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
