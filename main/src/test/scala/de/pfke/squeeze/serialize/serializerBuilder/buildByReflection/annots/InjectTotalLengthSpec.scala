package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.annots

import de.pfke.squeeze.serialize.mocks.annots.{InjectTotalLength_dynamicSize_String_Mock, InjectTotalLength_staticSize_Mock}
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class InjectTotalLengthSpec
  extends BaseSpec {
  "testing serializer for InjectTotalLength_staticSize_Mock type (static len message)" when {
    checkThis[InjectTotalLength_staticSize_Mock](
      code = s"""
                |$baseImports
                |
                |class InjectTotalLength_staticSize_MockSerializer
                |  extends CompiledSerializer[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_staticSize_Mock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_staticSize_Mock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_staticSize_Mock = {
                |    require(iter.len.toByte >= 8, s"[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_staticSize_Mock] given input has only $${iter.len} bytes left, but we need 8 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[Short](iter)
                |    val _2ndParam = serializerContainer.read[Short](iter)
                |    val _3rdParam = serializerContainer.read[Int](iter)
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_staticSize_Mock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam,
                |      _3rdParam = _3rdParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_staticSize_Mock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_staticSize_Mock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Short](data._1stParam, hints = hints:_*)
                |    serializerContainer.write[Short](8, hints = hints:_*)
                |    serializerContainer.write[Int](data._3rdParam, hints = hints:_*)
                |  }
                |}
                |new InjectTotalLength_staticSize_MockSerializer()
                |""".stripMargin
    )
  }

  "testing serializer for InjectTotalLength_dynamicSize_String_Mock type (dynamic len message)" when {
    checkThis[InjectTotalLength_dynamicSize_String_Mock](
      code = s"""
                |$baseImports
                |
                |class InjectTotalLength_dynamicSize_String_MockSerializer
                |  extends CompiledSerializer[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_dynamicSize_String_Mock] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_dynamicSize_String_Mock]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_dynamicSize_String_Mock = {
                |    require(iter.len.toByte >= 6, s"[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_dynamicSize_String_Mock] given input has only $${iter.len} bytes left, but we need 6 byte")
                |    // read iter
                |    val _1stParam = serializerContainer.read[Short](iter)
                |    val _2ndParam = serializerContainer.read[Short](iter)
                |    val _3rdParam = serializerContainer.read[Short](iter)
                |    val _4thParam = serializerContainer.read[String](iter, hints = SizeInByteHint(value = _3rdParam))
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_dynamicSize_String_Mock(
                |      _1stParam = _1stParam,
                |      _2ndParam = _2ndParam,
                |      _3rdParam = _3rdParam,
                |      _4thParam = _4thParam
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_dynamicSize_String_Mock,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.annots.InjectTotalLength_dynamicSize_String_Mock] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Short](data._1stParam, hints = hints:_*)
                |    serializerContainer.write[Short](data._2ndParam.length.toShort, hints = hints:_*)
                |    serializerContainer.write[Short](data._4thParam.length.toShort, hints = hints:_*)
                |    serializerContainer.write[String](data._4thParam, hints = hints:_*)
                |  }
                |}
                |new InjectTotalLength_dynamicSize_String_MockSerializer()
                |""".stripMargin
    )
  }
}
