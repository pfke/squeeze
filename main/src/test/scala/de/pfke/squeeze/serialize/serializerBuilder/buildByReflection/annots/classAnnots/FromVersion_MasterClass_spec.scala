package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots.classAnnots

import de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_MasterClass
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class FromVersion_MasterClass_spec
  extends BaseSpec {
  "testing serializer for FromVersion_MasterClass type" when {
    checkThis[FromVersion_MasterClass](
      code = s"""
                |$baseImports
                |
                |class FromVersion_MasterClassSerializer
                |  extends CompiledSerializer[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_MasterClass] {
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
                |    require(iter.len.toByte >= 0, s"[de.pfke.squeeze.serialize.mocks.annots.classAnnots.FromVersion_MasterClass] given input has only $${iter.len} left, but we need 0 byte")
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
