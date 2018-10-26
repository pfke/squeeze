package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex

import de.pfke.squeeze.serialize.mocks.Enum1Mock.Enum1Mock
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class Enum1BuilderSpec
  extends BaseSpec {
  "testing serializer for Enum1Mock type" when {
    checkThis[Enum1Mock](
      code = s"""
                |$baseImports
                |
                |class Enum1MockSerializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.Enum1Mock.Enum1Mock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.Enum1Mock.Enum1Mock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.Enum1Mock.Enum1Mock = {
                |    serializerContainer
                |      .read[Int](iter, hints = hints:_*) match {
                |      case t if t == de.pfke.squeeze.serialize.mocks.Enum1Mock._1.id => de.pfke.squeeze.serialize.mocks.Enum1Mock._1
                |      case t if t == de.pfke.squeeze.serialize.mocks.Enum1Mock._2.id => de.pfke.squeeze.serialize.mocks.Enum1Mock._2
                |      case t if t == de.pfke.squeeze.serialize.mocks.Enum1Mock._3.id => de.pfke.squeeze.serialize.mocks.Enum1Mock._3
                |      case t if t == de.pfke.squeeze.serialize.mocks.Enum1Mock._4.id => de.pfke.squeeze.serialize.mocks.Enum1Mock._4
                |
                |      case t => throwException(s"no enum type found with this value ($$t)")
                |    }
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.Enum1Mock.Enum1Mock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    serializerContainer.write[Int](data.id, hints = hints:_*)
                |  }
                |}
                |new Enum1MockSerializer()
                |""".stripMargin
    )
  }
}
