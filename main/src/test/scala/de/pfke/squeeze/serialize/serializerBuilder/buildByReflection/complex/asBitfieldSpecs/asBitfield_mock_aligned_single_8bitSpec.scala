package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex.asBitfieldSpecs

import de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class asBitfield_mock_aligned_single_8bitSpec
  extends BaseSpec {
  "testing a class with one aligned multi fields" when {
    checkThis[asBitfield_mock_aligned_single_8bit](
      code =
        s"""
           |$baseImports
           |
           |class asBitfield_mock_aligned_single_8bitSerializer
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
           |    require(iter.len.toByte >= 1, s"[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit] given input has only $${iter.len} bytes left, but we need 1 byte")
           |    // read iter
           |    val _1stBitIter = iter.iterator(bitAlignment = BitStringAlignment._8Bit)
           |    val field01 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 8))
           |    // create object
           |    de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit(
           |      field01 = field01
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
           |    val _1stBitBuilder = BitStringBuilder.newBuilder(alignment = BitStringAlignment._8Bit)
           |    serializerContainer.write[Int](data.field01, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 8))
           |    findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append(_1stBitBuilder.result())
           |  }
           |}
           |new asBitfield_mock_aligned_single_8bitSerializer()
           |""".stripMargin
    )
  }
}
