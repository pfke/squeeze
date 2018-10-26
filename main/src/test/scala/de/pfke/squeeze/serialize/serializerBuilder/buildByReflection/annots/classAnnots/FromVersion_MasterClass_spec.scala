package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots.classAnnots

import de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_MasterClass
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class FromVersion_MasterClass_spec
  extends BaseSpec {
  "testing serializer for FromVersion_MasterClass type" when {
    checkThis[FromVersion_MasterClass](
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
                |class FromVersion_MasterClassSerializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_MasterClass] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_MasterClass]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_MasterClass = {
                |    require(iter.len.toByte >= 0, s"[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_MasterClass] given input has only $${iter.len} bytes left, but we need 0 byte")
                |    // read iter
                |    val _1stArg = serializerContainer.read[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed](iter)
                |    val _2ndArg = serializerContainer.read[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_levelPassed](iter)
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_MasterClass(
                |      _1stArg = _1stArg,
                |      _2ndArg = _2ndArg
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_MasterClass,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_MasterClass] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed](data._1stArg, hints = hints:_*)
                |    serializerContainer.write[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_levelPassed](data._2ndArg, hints = hints:_*)
                |  }
                |}
                |new FromVersion_MasterClassSerializer()
                |""".stripMargin
    )
  }
}
