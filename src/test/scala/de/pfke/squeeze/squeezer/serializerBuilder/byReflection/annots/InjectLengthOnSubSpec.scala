package de.pfke.squeeze.squeezer.serializerBuilder.byReflection.annots

import de.pfke.squeeze.squeezer.mocks.InjectLengthOnSubMock
import de.pfke.squeeze.squeezer.serializerBuilder.byReflection.BaseSpec

class InjectLengthOnSubSpec
  extends BaseSpec {
    "testing serializer for InjectLengthOnSubMock type" when {
      val r1 = "lköklö"
      r1.length
      checkThis[InjectLengthOnSubMock](
        code = s"""
                  |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                  |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                  |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                  |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                  |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                  |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                  |import java.nio.ByteOrder
                  |
                  |class InjectLengthOnSubMockSerializer
                  |  extends Serializer[de.pintono.tools.squeeze.core.mocks.InjectLengthOnSubMock] {
                  |  override def objectTypeInfo = ReflHelper.generateTypeInfo[de.pintono.tools.squeeze.core.mocks.InjectLengthOnSubMock]
                  |
                  |  override def read(
                  |    iter: AnythingIterator,
                  |    hints: SerializerHint*
                  |  )(
                  |    implicit
                  |    byteOrder: ByteOrder,
                  |    serializerContainer: SerializerContainer,
                  |    version: Option[PatchLevelVersion]
                  |  ): de.pintono.tools.squeeze.core.mocks.InjectLengthOnSubMock = {
                  |    require(iter.len.toByte >= 2, s"[de.pintono.tools.squeeze.core.mocks.InjectLengthOnSubMock] given input has only $${iter.len} bytes left, but we need 2 byte")
                  |    // read iter
                  |    val _1stParam = serializerContainer.read[de.pintono.tools.squeeze.core.mocks.SubInjectLengthOnSubMock](iter)
                  |    // create object
                  |    de.pintono.tools.squeeze.core.mocks.InjectLengthOnSubMock(
                  |      _1stParam = _1stParam
                  |    )
                  |  }
                  |
                  |  override def write(
                  |    data: de.pintono.tools.squeeze.core.mocks.InjectLengthOnSubMock,
                  |    hints: SerializerHint*
                  |  )(
                  |    implicit
                  |    byteOrder: ByteOrder,
                  |    serializerContainer: SerializerContainer,
                  |    version: Option[PatchLevelVersion]
                  |  ): Unit = {
                  |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.InjectLengthOnSubMock] given input has no ByteStringBuilderHint")
                  |    serializerContainer.write[de.pintono.tools.squeeze.core.mocks.SubInjectLengthOnSubMock](data._1stParam, hints = hints:_*)
                  |  }
                  |}
                  |new InjectLengthOnSubMockSerializer()
                  |""".stripMargin
      )
    }
  }
