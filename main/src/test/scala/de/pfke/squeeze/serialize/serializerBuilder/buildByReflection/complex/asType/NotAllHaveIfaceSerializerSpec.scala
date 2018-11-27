package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex.asType

import de.pfke.squeeze.serialize.mocks.asType.NotAllHaveIface
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class NotAllHaveIfaceSerializerSpec
  extends BaseSpec {
  "testing serializer for NotAllHaveIface type (having a sub class extending this iface, but does not have the typeForIface annot)" when {
    checkThis[NotAllHaveIface](
      code = s"""
                |$baseImports
                |
                |class NotAllHaveIfaceSerializer
                |  extends CompiledSerializer[de.pfke.squeeze.serialize.mocks.asType.NotAllHaveIface] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.asType.NotAllHaveIface]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.asType.NotAllHaveIface = {
                |    require(iter.len.toByte >= 0, s"[de.pfke.squeeze.serialize.mocks.asType.NotAllHaveIface] given input has only $${iter.len} left, but we need 0 byte")
                |    // read iter
                |    (findIfaceTypeHint(hints = hints), version) match {
                |      case (Some(10), None) => serializerContainer.read[de.pfke.squeeze.serialize.mocks.asType.NAHISubClassA](iter, hints = hints:_*)
                |      case (None, None) => serializerContainer.read[de.pfke.squeeze.serialize.mocks.asType.NAHISubClassB](iter, hints = hints:_*)
                |
                |      case (t1, t2) => throw new SerializerRunException(s"trying to unsqueeze a trait (de.pfke.squeeze.serialize.mocks.asType.NotAllHaveIface), but either iface type ($$t1) or version ($$t2) does not match")
                |    }
                |    // create object
                |    // no its a trait
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.asType.NotAllHaveIface,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.asType.NotAllHaveIface] given input has no ByteStringBuilderHint")
                |    val written = data match {
                |      case t: de.pfke.squeeze.serialize.mocks.asType.NAHISubClassA => serializerContainer.write[de.pfke.squeeze.serialize.mocks.asType.NAHISubClassA](t, hints = hints:_*)
                |      case t: de.pfke.squeeze.serialize.mocks.asType.NAHISubClassB => serializerContainer.write[de.pfke.squeeze.serialize.mocks.asType.NAHISubClassB](t, hints = hints:_*)
                |
                |      case t => throw new SerializerRunException(s"trying to squeeze a trait (de.pfke.squeeze.serialize.mocks.asType.NotAllHaveIface), but type is unknown")
                |    }
                |  }
                |}
                |new NotAllHaveIfaceSerializer()
                |""".stripMargin
    )
  }
}
