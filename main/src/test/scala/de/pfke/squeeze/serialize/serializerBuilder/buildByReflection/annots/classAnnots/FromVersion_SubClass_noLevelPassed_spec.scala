package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots.classAnnots

import de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class FromVersion_SubClass_noLevelPassed_spec
  extends BaseSpec {
  "testing serializer for FromVersion_SubClass_noLevelPassed type" when {
    checkThis[FromVersion_SubClass_noLevelPassed](
      code = s"""
                |$baseImports
                |
                |class FromVersion_SubClass_noLevelPassedSerializer
                |  extends CompiledSerializer[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed = {
                |    require(iter.len.toByte >= 0, s"[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed] given input has only $${iter.len} left, but we need 0 byte")
                |    // read iter
                |    (findIfaceTypeHint(hints = hints), version) match {
                |      case (None, None) => serializerContainer.read[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00000000](iter, hints = hints:_*)
                |      case (None, Some(PatchLevelVersion(2, 1, 0))) => serializerContainer.read[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001](iter, hints = hints:_*)
                |
                |      case (t1, t2) => throw new SerializerRunException(s"trying to unsqueeze a trait (de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed), but either iface type ($$t1) or version ($$t2) does not match")
                |    }
                |    // create object
                |    // no its a trait
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed] given input has no ByteStringBuilderHint")
                |    val written = data match {
                |      case t: de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00000000 => serializerContainer.write[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00000000](t, hints = hints:_*)
                |      case t: de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001 => serializerContainer.write[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed_v00020001](t, hints = hints:_*)
                |
                |      case t => throw new SerializerRunException(s"trying to squeeze a trait (de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_SubClass_noLevelPassed), but type is unknown")
                |    }
                |  }
                |}
                |new FromVersion_SubClass_noLevelPassedSerializer()
                |""".stripMargin
    )
  }
}
