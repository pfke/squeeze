package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.complex

import de.pfke.squeeze.squeezer.mocks.Enum1Mock.Enum1Mock
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class Enum1BuilderSpec
  extends BaseSpec {
  "testing serializer for Enum1Mock type" when {
    checkThis[Enum1Mock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class Enum1MockSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.Enum1Mock.Enum1Mock] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[de.pintono.tools.squeeze.core.mocks.Enum1Mock.Enum1Mock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.Enum1Mock.Enum1Mock = {
                |    serializerContainer
                |      .read[Int](iter, hints = hints:_*) match {
                |      case t if t == de.pintono.tools.squeeze.core.mocks.Enum1Mock._1.id => de.pintono.tools.squeeze.core.mocks.Enum1Mock._1
                |      case t if t == de.pintono.tools.squeeze.core.mocks.Enum1Mock._2.id => de.pintono.tools.squeeze.core.mocks.Enum1Mock._2
                |      case t if t == de.pintono.tools.squeeze.core.mocks.Enum1Mock._3.id => de.pintono.tools.squeeze.core.mocks.Enum1Mock._3
                |      case t if t == de.pintono.tools.squeeze.core.mocks.Enum1Mock._4.id => de.pintono.tools.squeeze.core.mocks.Enum1Mock._4
                |
                |      case t => throwException(s"no enum type found with this value ($$t)")
                |    }
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.Enum1Mock.Enum1Mock,
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
