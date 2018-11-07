package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex.asBitfieldSpecs

import de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class asBitfield_mock_unaligned_blocks_multiSpec
  extends BaseSpec {
  "testing a class with one aligned multi fields" when {
    checkThis[asBitfield_mock_unaligned_blocks_multi](
      code =
        s"""
           |$baseImports
           |
           |class asBitfield_mock_unaligned_blocks_multiSerializer
             extends CompiledSerializer[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi] {
           |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi]
           |
           |  override def read(
           |    iter: AnythingIterator,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi = {
           |    require(iter.len.toByte >= 22, s"[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi] given input has only $${iter.len} bytes left, but we need 22 byte")
           |    // read iter
           |    val _1stBitIter = iter.iterator(bitAlignment = BitStringAlignment._32Bit)
           |    val field01 = serializerContainer.read[Byte](_1stBitIter, hints = SizeInBitHint(value = 8))
           |    val field02 = serializerContainer.read[Short](_1stBitIter, hints = SizeInBitHint(value = 16))
           |    val field03 = serializerContainer.read[Byte](_1stBitIter, hints = SizeInBitHint(value = 8))
           |    val _2ndBitIter = iter.iterator(bitAlignment = BitStringAlignment._32Bit)
           |    val field04 = serializerContainer.read[Int](_2ndBitIter, hints = SizeInBitHint(value = 32))
           |    val _3rdBitIter = iter.iterator(bitAlignment = BitStringAlignment._32Bit)
           |    val field05 = serializerContainer.read[Int](_3rdBitIter, hints = SizeInBitHint(value = 8))
           |    val field06 = serializerContainer.read[Int](_3rdBitIter, hints = SizeInBitHint(value = 8))
           |    val field07 = serializerContainer.read[Short](iter)
           |    val _4thBitIter = iter.iterator(bitAlignment = BitStringAlignment._32Bit)
           |    _4thBitIter.read[Long](BitLength(24)) // read padding bits
           |    val field08 = serializerContainer.read[Byte](_4thBitIter, hints = SizeInBitHint(value = 8))
           |    val field09 = serializerContainer.read[Int](iter)
           |    // create object
           |    de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi(
           |      field01 = field01,
           |      field02 = field02,
           |      field03 = field03,
           |      field04 = field04,
           |      field05 = field05,
           |      field06 = field06,
           |      field07 = field07,
           |      field08 = field08,
           |      field09 = field09
           |    )
           |  }
           |
           |  override def write(
           |    data: de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): Unit = {
           |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi] given input has no ByteStringBuilderHint")
           |    val _1stBitBuilder = BitStringBuilder.newBuilder(alignment = BitStringAlignment._32Bit)
           |    serializerContainer.write[Byte](data.field01, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 8))
           |    serializerContainer.write[Short](data.field02, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 16))
           |    serializerContainer.write[Byte](data.field03, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 8))
           |    findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append(_1stBitBuilder.result())
           |    val _2ndBitBuilder = BitStringBuilder.newBuilder(alignment = BitStringAlignment._32Bit)
           |    serializerContainer.write[Int](data.field04, hints = BitStringBuilderHint(_2ndBitBuilder), SizeInBitHint(value = 32))
           |    findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append(_2ndBitBuilder.result())
           |    val _3rdBitBuilder = BitStringBuilder.newBuilder(alignment = BitStringAlignment._32Bit)
           |    serializerContainer.write[Int](data.field05, hints = BitStringBuilderHint(_3rdBitBuilder), SizeInBitHint(value = 8))
           |    serializerContainer.write[Int](data.field06, hints = BitStringBuilderHint(_3rdBitBuilder), SizeInBitHint(value = 8))
           |    findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append(_3rdBitBuilder.result())
           |    serializerContainer.write[Short](data.field07, hints = hints:_*)
           |    val _4thBitBuilder = BitStringBuilder.newBuilder(alignment = BitStringAlignment._32Bit)
           |    serializerContainer.write[Byte](data.field08, hints = BitStringBuilderHint(_4thBitBuilder), SizeInBitHint(value = 8))
           |    findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append(_4thBitBuilder.result())
           |    serializerContainer.write[Int](data.field09, hints = hints:_*)
           |  }
           |}
           |new asBitfield_mock_unaligned_blocks_multiSerializer()
           |""".stripMargin
    )
  }
}
