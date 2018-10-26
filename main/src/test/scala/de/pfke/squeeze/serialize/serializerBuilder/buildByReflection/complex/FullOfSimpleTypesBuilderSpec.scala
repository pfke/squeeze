package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex

import de.pfke.squeeze.serialize.mocks.FullOfSimpleTypesMock
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class FullOfSimpleTypesBuilderSpec
  extends BaseSpec {
  "testing serializer for FullOfSimpleTypes type" when {
    checkThis[FullOfSimpleTypesMock](
      code = s"""
                |$baseImports
                |
                |class FullOfSimpleTypesMockSerializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.FullOfSimpleTypesMock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.FullOfSimpleTypesMock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.FullOfSimpleTypesMock = {
                |    require(iter.len.toByte >= 30, s"[de.pfke.squeeze.serialize.mocks.FullOfSimpleTypesMock] given input has only $${iter.len} bytes left, but we need 30 byte")
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
                |    de.pfke.squeeze.serialize.mocks.FullOfSimpleTypesMock(
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
                |    data: de.pfke.squeeze.serialize.mocks.FullOfSimpleTypesMock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.FullOfSimpleTypesMock] given input has no ByteStringBuilderHint")
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
