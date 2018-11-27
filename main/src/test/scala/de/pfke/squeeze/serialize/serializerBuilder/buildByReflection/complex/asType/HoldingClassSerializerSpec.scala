package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex.asType

import de.pfke.squeeze.serialize.mocks.asType.HoldingClass
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class HoldingClassSerializerSpec
  extends BaseSpec {
  "testing serializer for HoldingClass type" when {
    checkThis[HoldingClass](
      code = s"""
                |$baseImports
                |
                |class HoldingClassSerializer
                |  extends CompiledSerializer[de.pfke.squeeze.serialize.mocks.asType.HoldingClass] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.asType.HoldingClass]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.asType.HoldingClass = {
                |    require(iter.len.toByte >= 4, s"[de.pfke.squeeze.serialize.mocks.asType.HoldingClass] given input has only $${iter.len} left, but we need 4 byte")
                |    // read iter
                |    val _ifaceType = serializerContainer.read[Int](iter)
                |    val _iface = serializerContainer.read[de.pfke.squeeze.serialize.mocks.asType.Iface](iter, hints = IfaceTypeHint(value = _ifaceType))
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.asType.HoldingClass(
                |      _ifaceType = _ifaceType,
                |      _iface = _iface
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.asType.HoldingClass,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.asType.HoldingClass] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Int](serializerContainer.getIfaceType(data._iface).toInt, hints = hints:_*)
                |    serializerContainer.write[de.pfke.squeeze.serialize.mocks.asType.Iface](data._iface, hints = hints:_*)
                |  }
                |}
                |new HoldingClassSerializer()
                |""".stripMargin
    )
  }
}
