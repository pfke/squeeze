package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.complex.asBitfieldSpecs

import de.pfke.squeeze.squeezer.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class asBitfield_mock_unaligned_multiSpec
  extends BaseSpec {
  "testing a class with one aligned multi fields" when {
    checkThis[asBitfield_mock_unaligned_multi](
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
           |class asBitfield_mock_unaligned_multiSerializer
           |  extends Serializer[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi] {
           |  override def objectTypeInfo = GenericOps.getTypeInfo[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi]
           |
           |  override def read(
           |    iter: AnythingIterator,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi = {
           |    require(iter.len.toByte >= 2, s"[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi] given input has only $${iter.len} bytes left, but we need 2 byte")
           |    // read iter
           |    val _1stBitIter = iter.iterator(bitAlignment = BitStringAlignment._16Bit)
           |    _1stBitIter.read(BitLength(11)) // read padding bits
           |    val field01 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 2))
           |    val field02 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 3))
           |    // create object
           |    de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi(
           |      field01 = field01,
           |      field02 = field02
           |    )
           |  }
           |
           |  override def write(
           |    data: de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): Unit = {
           |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_multi] given input has no ByteStringBuilderHint")
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
