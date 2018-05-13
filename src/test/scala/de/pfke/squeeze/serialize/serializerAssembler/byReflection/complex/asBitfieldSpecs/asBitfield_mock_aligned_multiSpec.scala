package de.pfke.squeeze.serialize.serializerAssembler.byReflection.complex.asBitfieldSpecs

import de.pfke.squeeze.testing.mocks.asBitfieldSpecs.asBitfield_mock_aligned_multi
import de.pfke.squeeze.serialize.serializerAssembler.byReflection.BaseSpec

class asBitfield_mock_aligned_multiSpec
  extends BaseSpec {
  "testing a class with one aligned multi fields" when {
    checkThis[asBitfield_mock_aligned_multi](
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
           |class asBitfield_mock_aligned_multiSerializer
           |  extends Serializer[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_multi] {
           |  override def objectTypeInfo = GenericOps.getTypeInfo[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_multi]
           |
           |  override def read(
           |    iter: AnythingIterator,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_multi = {
           |    require(iter.len.toByte >= 8, s"[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_multi] given input has only $${iter.len} bytes left, but we need 8 byte")
           |    // read iter
           |    val _1stBitIter = iter.iterator(bitAlignment = BitStringAlignment._32Bit)
           |    val field01 = serializerContainer.read[Byte](_1stBitIter, hints = SizeInBitHint(value = 8))
           |    val field02 = serializerContainer.read[Short](_1stBitIter, hints = SizeInBitHint(value = 16))
           |    val field03 = serializerContainer.read[Byte](_1stBitIter, hints = SizeInBitHint(value = 8))
           |    val field04 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 32))
           |    // create object
           |    de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_multi(
           |      field01 = field01,
           |      field02 = field02,
           |      field03 = field03,
           |      field04 = field04
           |    )
           |  }
           |
           |  override def write(
           |    data: de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_multi,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): Unit = {
           |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_multi] given input has no ByteStringBuilderHint")
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
