package de.pfke.squeeze.serialize.serializerAssembler.byReflection.complex

import de.pfke.squeeze.testing.mocks.FullOfSimpleTypesMock
import de.pfke.squeeze.serialize.serializerAssembler.byReflection.BaseSpec

class FullOfSimpleTypesBuilderSpec
  extends BaseSpec {
  "testing serializer for FullOfSimpleTypes type" when {
    checkThis[FullOfSimpleTypesMock](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class FullOfSimpleTypesMockSerializer
                |  extends Serializer[de.pintono.tools.squeeze.core.mocks.FullOfSimpleTypesMock] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[de.pintono.tools.squeeze.core.mocks.FullOfSimpleTypesMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pintono.tools.squeeze.core.mocks.FullOfSimpleTypesMock = {
                |    require(iter.len.toByte >= 30, s"[de.pintono.tools.squeeze.core.mocks.FullOfSimpleTypesMock] given input has only $${iter.len} bytes left, but we need 30 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[Boolean](iter)
                |    val _2ndParam = serializerContainer.read[Byte](iter)
                |    val _3rdParam = serializerContainer.read[Char](iter)
                |    val _4thParam = serializerContainer.read[Double](iter)
                |    val _5thParam = serializerContainer.read[Float](iter)
                |    val _6thParam = serializerContainer.read[Int](iter)
                |    val _7thParam = serializerContainer.read[Long](iter)
                |    val _8thParam = serializerContainer.read[Short](iter)
                |    // create object
                |    de.pintono.tools.squeeze.core.mocks.FullOfSimpleTypesMock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam,
                |      _3rdParam = _3rdParam,
                |      _4thParam = _4thParam,
                |      _5thParam = _5thParam,
                |      _6thParam = _6thParam,
                |      _7thParam = _7thParam,
                |      _8thParam = _8thParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pintono.tools.squeeze.core.mocks.FullOfSimpleTypesMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.FullOfSimpleTypesMock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Boolean](data._1stParam, hints = hints:_*)
                |    serializerContainer.write[Byte](data._2ndParam, hints = hints:_*)
                |    serializerContainer.write[Char](data._3rdParam, hints = hints:_*)
                |    serializerContainer.write[Double](data._4thParam, hints = hints:_*)
                |    serializerContainer.write[Float](data._5thParam, hints = hints:_*)
                |    serializerContainer.write[Int](data._6thParam, hints = hints:_*)
                |    serializerContainer.write[Long](data._7thParam, hints = hints:_*)
                |    serializerContainer.write[Short](data._8thParam, hints = hints:_*)
                |  }
                |}
                |new FullOfSimpleTypesMockSerializer()
                |""".stripMargin
    )
  }
}
