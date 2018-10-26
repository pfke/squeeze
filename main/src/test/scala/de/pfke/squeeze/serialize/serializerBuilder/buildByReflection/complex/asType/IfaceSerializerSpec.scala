package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex.asType

import de.pfke.squeeze.serialize.mocks.asType.Iface
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class IfaceSerializerSpec
  extends BaseSpec {
  "testing serializer for Iface type" when {
    checkThis[Iface](
      code = s"""
                |$baseImports
                |
                |class IfaceSerializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.asType.Iface] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.asType.Iface]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.asType.Iface = {
                |    require(iter.len.toByte >= 0, s"[de.pfke.squeeze.serialize.mocks.asType.Iface] given input has only $${iter.len} bytes left, but we need 0 byte")
                |    // read iter
                |    (findIfaceTypeHint(hints = hints), version) match {
                |      case (Some(10), None) => serializerContainer.read[de.pfke.squeeze.serialize.mocks.asType.SubClassA](iter, hints = hints:_*)
                |      case (Some(15), Some(PatchLevelVersion(1, 5, 123))) => serializerContainer.read[de.pfke.squeeze.serialize.mocks.asType.SubClassB_fromVersion_1_5_123](iter, hints = hints:_*)
                |      case (Some(15), Some(PatchLevelVersion(1, 5, 124))) => serializerContainer.read[de.pfke.squeeze.serialize.mocks.asType.SubClassB_fromVersion_1_5_124](iter, hints = hints:_*)
                |      case (Some(15), Some(PatchLevelVersion(1, 6, 123))) => serializerContainer.read[de.pfke.squeeze.serialize.mocks.asType.SubClassB_fromVersion_1_6_123](iter, hints = hints:_*)
                |      case (Some(15), Some(PatchLevelVersion(2, 5, 123))) => serializerContainer.read[de.pfke.squeeze.serialize.mocks.asType.SubClassB_fromVersion_2_5_123](iter, hints = hints:_*)
                |      case (Some(15), None) => serializerContainer.read[de.pfke.squeeze.serialize.mocks.asType.SubClassB](iter, hints = hints:_*)
                |      case (Some(17), None) => serializerContainer.read[de.pfke.squeeze.serialize.mocks.asType.SubClassC](iter, hints = hints:_*)
                |
                |      case (t1, t2) => throw new de.pintono.tools.squeeze.core.SerializerRunException(s"trying to unsqueeze a trait (de.pfke.squeeze.serialize.mocks.asType.Iface), but either iface type ($$t1) or version ($$t2) does not match")
                |    }
                |    // create object
                |    // no its a trait
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.asType.Iface,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.asType.Iface] given input has no ByteStringBuilderHint")
                |    val written = data match {
                |      case t: de.pfke.squeeze.serialize.mocks.asType.SubClassA => serializerContainer.write[de.pfke.squeeze.serialize.mocks.asType.SubClassA](t, hints = hints:_*)
                |      case t: de.pfke.squeeze.serialize.mocks.asType.SubClassB => serializerContainer.write[de.pfke.squeeze.serialize.mocks.asType.SubClassB](t, hints = hints:_*)
                |      case t: de.pfke.squeeze.serialize.mocks.asType.SubClassB_fromVersion_1_5_123 => serializerContainer.write[de.pfke.squeeze.serialize.mocks.asType.SubClassB_fromVersion_1_5_123](t, hints = hints:_*)
                |      case t: de.pfke.squeeze.serialize.mocks.asType.SubClassB_fromVersion_1_5_124 => serializerContainer.write[de.pfke.squeeze.serialize.mocks.asType.SubClassB_fromVersion_1_5_124](t, hints = hints:_*)
                |      case t: de.pfke.squeeze.serialize.mocks.asType.SubClassB_fromVersion_1_6_123 => serializerContainer.write[de.pfke.squeeze.serialize.mocks.asType.SubClassB_fromVersion_1_6_123](t, hints = hints:_*)
                |      case t: de.pfke.squeeze.serialize.mocks.asType.SubClassB_fromVersion_2_5_123 => serializerContainer.write[de.pfke.squeeze.serialize.mocks.asType.SubClassB_fromVersion_2_5_123](t, hints = hints:_*)
                |      case t: de.pfke.squeeze.serialize.mocks.asType.SubClassC => serializerContainer.write[de.pfke.squeeze.serialize.mocks.asType.SubClassC](t, hints = hints:_*)
                |
                |      case t => throw new de.pintono.tools.squeeze.core.SerializerRunException(s"trying to squeeze a trait (de.pfke.squeeze.serialize.mocks.asType.Iface), but type is unknown")
                |    }
                |  }
                |}
                |new IfaceSerializer()
                |""".stripMargin
    )
  }
}
