package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots.classAnnots

import de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class FromVersion_SubClass_noLevelPassed_v00020001_spec
  extends BaseSpec {
  "testing serializer for FromVersion_SubClass_noLevelPassed_v00020001 type" when {
    checkThis[FromVersion_SubClass_noLevelPassed_v00020001](
      code = s"""
                |$baseImports
                |
                |class FromVersion_SubClass_noLevelPassed_v00020001Serializer
                |  extends CompiledSerializer[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001] {
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
                |    require(iter.len.toByte >= 2, s"[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001] given input has only $${iter.len} left, but we need 2 byte")
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
