package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots

import de.pfke.squeeze.serialize.mocks.annots.InjectCountOnSubMock
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class InjectCountOnSubSpec
  extends BaseSpec {
  "testing serializer for InjectCountOnSubMock type" when {
    checkThis[InjectCountOnSubMock](
      code = s"""
                |import de.pfke.squeeze.zlib.version.PatchLevelVersion
                |import de.pfke.squeeze.zlib.data.collection.anythingString.AnythingIterator
                |import de.pfke.squeeze.zlib.data.collection.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pfke.squeeze.zlib.data.length.digital.{BitLength, ByteLength}
                |import de.pfke.squeeze.zlib.refl.GeneralRefl
                |import de.pfke.squeeze.serialize.{Serializer, SerializerContainer}
                |import de.pfke.squeeze.serialize.serializerHints._
                |import de.pfke.squeeze.zlib._
                |import java.nio.ByteOrder
                |
                |class InjectCountOnSubMockSerializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.InjectCountOnSubMock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.InjectCountOnSubMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.InjectCountOnSubMock = {
                |    require(iter.len.toByte >= 6, s"[de.pfke.squeeze.serialize.mocks.InjectCountOnSubMock] given input has only $${iter.len} bytes left, but we need 6 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[de.pfke.squeeze.serialize.mocks.SubInjectCountOnSubMock](iter)
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.InjectCountOnSubMock(
                |      _1stParam = _1stParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.InjectCountOnSubMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.InjectCountOnSubMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[de.pfke.squeeze.serialize.mocks.SubInjectCountOnSubMock](data._1stParam, hints = hints:_*)
                |  }
                |}
                |new InjectCountOnSubMockSerializer()
                |""".stripMargin
    )
  }
}
