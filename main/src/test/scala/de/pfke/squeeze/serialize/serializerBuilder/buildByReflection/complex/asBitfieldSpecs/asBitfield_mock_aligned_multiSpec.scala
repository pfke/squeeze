package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex.asBitfieldSpecs

import de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_multi
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class asBitfield_mock_aligned_multiSpec
  extends BaseSpec {
  "testing a class with one aligned multi fields" when {
    checkThis[asBitfield_mock_aligned_multi](
      code =
        s"""
           |$baseImports
           |
           |class asBitfield_mock_aligned_multiSerializer
           |  extends Serializer[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit] {
           |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit]
           |
           |  override def read(
           |    iter: AnythingIterator,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit = {
           |    require(iter.len.toByte >= 4, s"[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit] given input has only $${iter.len} bytes left, but we need 4 byte")
           |    // read iter
           |    val _1stBitIter = iter.iterator(bitAlignment = BitStringAlignment._32Bit)
           |    val field01 = serializerContainer.read[Byte](_1stBitIter, hints = SizeInBitHint(value = 8))
           |    val field02 = serializerContainer.read[Short](_1stBitIter, hints = SizeInBitHint(value = 16))
           |    val field03 = serializerContainer.read[Byte](_1stBitIter, hints = SizeInBitHint(value = 8))
           |    val field04 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 32))
           |    // create object
           |    de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit(
           |      field01 = field01,
           |      field02 = field02,
           |      field03 = field03,
           |      field04 = field04
           |    )
           |  }
           |
           |  override def write(
           |    data: de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): Unit = {
           |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit] given input has no ByteStringBuilderHint")
           |    val _1stBitBuilder = BitStringBuilder.newBuilder(alignment = BitStringAlignment._32Bit)
           |    serializerContainer.write[Byte](data.field01, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 8))
           |    serializerContainer.write[Short](data.field02, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 16))
           |    serializerContainer.write[Byte](data.field03, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 8))
           |    serializerContainer.write[Int](data.field04, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 32))
           |    findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append(_1stBitBuilder.result())
           |  }
           |}
           |new asBitfield_mock_aligned_multiSerializer()
           |""".stripMargin
    )
  }
}
