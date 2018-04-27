package de.pfke.squeeze.squeezer.serializerBuilder.byReflection.complex.asType

import de.pfke.squeeze.squeezer.mocks.asType.HoldingClass
import de.pfke.squeeze.squeezer.serializerBuilder.byReflection.BaseSpec

class HoldingClassSerializerSpec
  extends BaseSpec {
  "testing serializer for HoldingClass type" when {
    checkThis[HoldingClass](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class HoldingClassSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.asType.HoldingClass] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[de.pintono.tools.squeeze.core.mocks.asType.HoldingClass]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.asType.HoldingClass = {
                |    require(iter.len.toByte >= 4, s"[de.pintono.tools.squeeze.core.mocks.asType.HoldingClass] given input has only $${iter.len} bytes left, but we need 4 byte")
                |    // read iter
                |    val _ifaceType = serializerContainer.read[Int](iter)
                |    val _iface = serializerContainer.read[de.pintono.tools.squeeze.core.mocks.asType.Iface](iter, hints = de.pintono.tools.squeeze.core.serializerHints.IfaceTypeHint(value = _ifaceType))
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.asType.HoldingClass(
                |      _ifaceType = _ifaceType,
                |      _iface = _iface
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.asType.HoldingClass,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.asType.HoldingClass] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Int](serializerContainer.getIfaceType(data._iface).toInt, hints = hints:_*)
                |    serializerContainer.write[de.pintono.tools.squeeze.core.mocks.asType.Iface](data._iface, hints = hints:_*)
                |  }
                |}
                |new HoldingClassSerializer()
                |""".stripMargin
    )
  }
}
