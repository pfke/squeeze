package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex.asType

import de.pfke.squeeze.serialize.mocks.asType.Iface
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class IfaceSerializerSpec
  extends BaseSpec {
  "testing serializer for Iface type" when {
    checkThis[Iface](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class IfaceSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.asType.Iface] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[de.pintono.tools.squeeze.core.mocks.asType.Iface]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.asType.Iface = {
                |    require(iter.len.toByte >= 0, s"[de.pintono.tools.squeeze.core.mocks.asType.Iface] given input has only $${iter.len} bytes left, but we need 0 byte")
                |    // read iter
                |    (findIfaceTypeHint(hints = hints), version) match {
                |      case (Some(10), None) => serializerContainer.read[de.pintono.tools.squeeze.core.mocks.asType.SubClassA](iter, hints = hints:_*)
                |      case (Some(15), Some(PatchLevelVersion(1, 5, 123))) => serializerContainer.read[de.pintono.tools.squeeze.core.mocks.asType.SubClassB_fromVersion_1_5_123](iter, hints = hints:_*)
                |      case (Some(15), Some(PatchLevelVersion(1, 5, 124))) => serializerContainer.read[de.pintono.tools.squeeze.core.mocks.asType.SubClassB_fromVersion_1_5_124](iter, hints = hints:_*)
                |      case (Some(15), Some(PatchLevelVersion(1, 6, 123))) => serializerContainer.read[de.pintono.tools.squeeze.core.mocks.asType.SubClassB_fromVersion_1_6_123](iter, hints = hints:_*)
                |      case (Some(15), Some(PatchLevelVersion(2, 5, 123))) => serializerContainer.read[de.pintono.tools.squeeze.core.mocks.asType.SubClassB_fromVersion_2_5_123](iter, hints = hints:_*)
                |      case (Some(15), None) => serializerContainer.read[de.pintono.tools.squeeze.core.mocks.asType.SubClassB](iter, hints = hints:_*)
                |      case (Some(17), None) => serializerContainer.read[de.pintono.tools.squeeze.core.mocks.asType.SubClassC](iter, hints = hints:_*)
                |
                |      case (t1, t2) => throw new de.pintono.tools.squeeze.core.SerializerRunException(s"trying to unsqueeze a trait (de.pintono.tools.squeeze.core.mocks.asType.Iface), but either iface type ($$t1) or version ($$t2) does not match")
                |    }
                |    // create object
                |    // no its a trait
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.asType.Iface,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.asType.Iface] given input has no ByteStringBuilderHint")
                |    val written = data match {
                |      case t: de.pintono.tools.squeeze.core.mocks.asType.SubClassA => serializerContainer.write[de.pintono.tools.squeeze.core.mocks.asType.SubClassA](t, hints = hints:_*)
                |      case t: de.pintono.tools.squeeze.core.mocks.asType.SubClassB => serializerContainer.write[de.pintono.tools.squeeze.core.mocks.asType.SubClassB](t, hints = hints:_*)
                |      case t: de.pintono.tools.squeeze.core.mocks.asType.SubClassB_fromVersion_1_5_123 => serializerContainer.write[de.pintono.tools.squeeze.core.mocks.asType.SubClassB_fromVersion_1_5_123](t, hints = hints:_*)
                |      case t: de.pintono.tools.squeeze.core.mocks.asType.SubClassB_fromVersion_1_5_124 => serializerContainer.write[de.pintono.tools.squeeze.core.mocks.asType.SubClassB_fromVersion_1_5_124](t, hints = hints:_*)
                |      case t: de.pintono.tools.squeeze.core.mocks.asType.SubClassB_fromVersion_1_6_123 => serializerContainer.write[de.pintono.tools.squeeze.core.mocks.asType.SubClassB_fromVersion_1_6_123](t, hints = hints:_*)
                |      case t: de.pintono.tools.squeeze.core.mocks.asType.SubClassB_fromVersion_2_5_123 => serializerContainer.write[de.pintono.tools.squeeze.core.mocks.asType.SubClassB_fromVersion_2_5_123](t, hints = hints:_*)
                |      case t: de.pintono.tools.squeeze.core.mocks.asType.SubClassC => serializerContainer.write[de.pintono.tools.squeeze.core.mocks.asType.SubClassC](t, hints = hints:_*)
                |
                |      case t => throw new de.pintono.tools.squeeze.core.SerializerRunException(s"trying to squeeze a trait (de.pintono.tools.squeeze.core.mocks.asType.Iface), but type is unknown")
                |    }
                |  }
                |}
                |new IfaceSerializer()
                |""".stripMargin
    )
  }
}
