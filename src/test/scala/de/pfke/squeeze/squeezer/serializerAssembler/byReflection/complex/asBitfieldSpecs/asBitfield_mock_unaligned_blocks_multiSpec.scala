package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.complex.asBitfieldSpecs

import de.pfke.squeeze.squeezer.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class asBitfield_mock_unaligned_blocks_multiSpec
  extends BaseSpec {
  "testing a class with one aligned multi fields" when {
    checkThis[asBitfield_mock_unaligned_blocks_multi](
      code =
        s"""
           |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
           |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
           |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
           |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
           |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
           |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
           |import java.nio.ByteOrder
           |
           |class asBitfield_mock_unaligned_blocks_multiSerializer
           |  extends Serializer[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi] {
           |  override def objectTypeInfo = GenericOps.getTypeInfo[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi]
           |
           |  override def read(
           |    iter: AnythingIterator,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi = {
           |    require(iter.len.toByte >= 22, s"[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi] given input has only $${iter.len} bytes left, but we need 22 byte")
           |    // read iter
           |    val _1stBitIter = iter.iterator(bitAlignment = BitStringAlignment._32Bit)
           |    _1stBitIter.read(BitLength(16)) // read padding bits
           |    val field01 = serializerContainer.read[Byte](_1stBitIter, hints = SizeInBitHint(value = 8))
           |    val field02 = serializerContainer.read[Short](_1stBitIter, hints = SizeInBitHint(value = 16))
           |    val field03 = serializerContainer.read[Byte](_1stBitIter, hints = SizeInBitHint(value = 8))
           |    val field04 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 32))
           |    val field05 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 8))
           |    val field06 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 8))
           |    val field07 = serializerContainer.read[Short](iter)
           |    val _2ndBitIter = iter.iterator(bitAlignment = BitStringAlignment._32Bit)
           |    _2ndBitIter.read(BitLength(24)) // read padding bits
           |    val field08 = serializerContainer.read[Byte](_2ndBitIter, hints = SizeInBitHint(value = 8))
           |    val field09 = serializerContainer.read[Int](iter)
           |    // create object
           |    de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi(
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
           |    data: de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): Unit = {
           |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_blocks_multi] given input has no ByteStringBuilderHint")
           |    val _1stBitBuilder = BitStringBuilder.newBuilder(alignment = BitStringAlignment._32Bit)
           |    serializerContainer.write[Byte](data.field01, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 8))
           |    serializerContainer.write[Short](data.field02, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 16))
           |    serializerContainer.write[Byte](data.field03, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 8))
           |    serializerContainer.write[Int](data.field04, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 32))
           |    serializerContainer.write[Int](data.field05, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 8))
           |    serializerContainer.write[Int](data.field06, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 8))
           |    findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append(_1stBitBuilder.result())
           |    serializerContainer.write[Short](data.field07, hints = hints:_*)
           |    val _2ndBitBuilder = BitStringBuilder.newBuilder(alignment = BitStringAlignment._32Bit)
           |    serializerContainer.write[Byte](data.field08, hints = BitStringBuilderHint(_2ndBitBuilder), SizeInBitHint(value = 8))
           |    findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append(_2ndBitBuilder.result())
           |    serializerContainer.write[Int](data.field09, hints = hints:_*)
           |  }
           |}
           |new asBitfield_mock_unaligned_blocks_multiSerializer()
           |""".stripMargin
    )
  }
}
