package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.annots

import de.pfke.squeeze.squeezer.mocks.InjectTotalLengthMock
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class InjectTotalLengthSpec
  extends BaseSpec {
  "testing serializer for InjectTotalLengthMock type" when {
    checkThis[InjectTotalLengthMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class InjectTotalLengthMockSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.InjectTotalLengthMock] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[de.pintono.tools.squeeze.core.mocks.InjectTotalLengthMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.InjectTotalLengthMock = {
                |    require(iter.len.toByte >= 8, s"[de.pintono.tools.squeeze.core.mocks.InjectTotalLengthMock] given input has only $${iter.len} bytes left, but we need 8 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[Short](iter)
                |    val _2ndParam = serializerContainer.read[Short](iter)
                |    val _3rdParam = serializerContainer.read[Int](iter)
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.InjectTotalLengthMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam,
                |      _3rdParam = _3rdParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.InjectTotalLengthMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.InjectTotalLengthMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Short](data._1stParam, hints = hints:_*)
                |    serializerContainer.write[Short](8, hints = hints:_*)
                |    serializerContainer.write[Int](data._3rdParam, hints = hints:_*)
                |  }
                |}
                |new InjectTotalLengthMockSerializer()
                |""".stripMargin
    )
  }
}
