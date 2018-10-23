package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots.classAnnots

import de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class FromVersion_SubClass_noLevelPassed_spec
  extends BaseSpec {
  "testing serializer for FromVersion_SubClass_noLevelPassed type" when {
    checkThis[FromVersion_SubClass_noLevelPassed](
      code = s"""
                |import de.pfke.squeeze.zlib.version.PatchLevelVersion
                |import de.pfke.squeeze.zlib.data.collection.anythingString.AnythingIterator
                |import de.pfke.squeeze.zlib.data.collection.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pfke.squeeze.zlib.data.length.digital.{BitLength, ByteLength}
                |import de.pintono.grind.refl.core.GeneralRefl
                |import de.pfke.squeeze.serialize.{Serializer, SerializerContainer}
                |import de.pfke.squeeze.serialize.serializerHints._
                |import de.pfke.squeeze.zlib._
                |import java.nio.ByteOrder
                |
                |class FromVersion_SubClass_noLevelPassed_v00020001Serializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001 = {
                |    require(iter.len.toByte >= 2, s"[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001] given input has only $${iter.len} bytes left, but we need 2 byte")
                |    // read iter
                |    val _1stArg = serializerContainer.read[Short](iter)
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001(
                |      _1stArg = _1stArg
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Short](data._1stArg, hints = hints:_*)
                |  }
                |}
                |new FromVersion_SubClass_noLevelPassed_v00020001Serializer()
                |""".stripMargin
    )
  }
}
