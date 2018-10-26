package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots

import de.pfke.squeeze.serialize.mocks.annots.InjectLengthOnSubMock
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class InjectLengthOnSubSpec
  extends BaseSpec {
    "testing serializer for InjectLengthOnSubMock type" when {
      checkThis[InjectLengthOnSubMock](
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
                  |class InjectLengthOnSubMockSerializer
                  |  extends Serializer[de.pfke.squeeze.serialize.mocks.annots.InjectLengthOnSubMock] {
                  |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.annots.InjectLengthOnSubMock]
                  |
                  |  override def read(
                  |    iter: AnythingIterator,
                  |    hints: SerializerHint*
                  |  )(
                  |    implicit
                  |    byteOrder: ByteOrder,
                  |    serializerContainer: SerializerContainer,
                  |    version: Option[PatchLevelVersion]
                  |  ): de.pfke.squeeze.serialize.mocks.annots.InjectLengthOnSubMock = {
                  |    require(iter.len.toByte >= 2, s"[de.pfke.squeeze.serialize.mocks.annots.InjectLengthOnSubMock] given input has only $${iter.len} bytes left, but we need 2 byte")
                  |    // read iter
                  |    val _1stParam = serializerContainer.read[de.pfke.squeeze.serialize.mocks.annots.SubInjectLengthOnSubMock](iter)
                  |    // create object
                  |    de.pfke.squeeze.serialize.mocks.annots.InjectLengthOnSubMock(
                  |      _1stParam = _1stParam
                  |    )
                  |  }
                  |
                  |  override def write(
                  |    data: de.pfke.squeeze.serialize.mocks.annots.InjectLengthOnSubMock,
                  |    hints: SerializerHint*
                  |  )(
                  |    implicit
                  |    byteOrder: ByteOrder,
                  |    serializerContainer: SerializerContainer,
                  |    version: Option[PatchLevelVersion]
                  |  ): Unit = {
                  |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.annots.InjectLengthOnSubMock] given input has no ByteStringBuilderHint")
                  |    serializerContainer.write[de.pfke.squeeze.serialize.mocks.annots.SubInjectLengthOnSubMock](data._1stParam, hints = hints:_*)
                  |  }
                  |}
                  |new InjectLengthOnSubMockSerializer()
                  |""".stripMargin
      )
    }
  }
