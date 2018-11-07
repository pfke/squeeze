package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex.asBitfieldSpecs

import de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class asBitfield_mock_unaligned_multiSpec
  extends BaseSpec {
  "testing a class with one aligned multi fields" when {
    checkThis[asBitfield_mock_unaligned_multi](
      code =
        s"""
           |$baseImports
           |
           |class asBitfield_mock_unaligned_multiSerializer
           |  extends CompiledSerializer[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi] {
           |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi]
           |
           |  override def read(
           |    iter: AnythingIterator,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi = {
           |    require(iter.len.toByte >= 2, s"[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi] given input has only $${iter.len} bytes left, but we need 2 byte")
           |    // read iter
           |    val _1stBitIter = iter.iterator(bitAlignment = BitStringAlignment._16Bit)
           |    val field01 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 2))
           |    val field02 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 3))
           |    // create object
           |    de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi(
           |      field01 = field01,
           |      field02 = field02
           |    )
           |  }
           |
           |  override def write(
           |    data: de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): Unit = {
           |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi] given input has no ByteStringBuilderHint")
           |    val _1stBitBuilder = BitStringBuilder.newBuilder(alignment = BitStringAlignment._16Bit)
           |    serializerContainer.write[Int](data.field01, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 2))
           |    serializerContainer.write[Int](data.field02, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 3))
           |    findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append(_1stBitBuilder.result())
           |  }
           |}
           |new asBitfield_mock_unaligned_multiSerializer()
           |""".stripMargin
    )
  }
}
