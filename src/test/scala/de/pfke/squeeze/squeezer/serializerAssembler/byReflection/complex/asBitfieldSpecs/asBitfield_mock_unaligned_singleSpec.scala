package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.complex.asBitfieldSpecs

import de.pfke.squeeze.squeezer.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single
import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class asBitfield_mock_unaligned_singleSpec
  extends BaseSpec {
  "testing a class with one aligned multi fields" when {
    checkThis[asBitfield_mock_unaligned_single](
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
           |class asBitfield_mock_unaligned_singleSerializer
           |  extends Serializer[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single] {
           |  override def objectTypeInfo = ReflHelper.generateTypeInfo[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single]
           |
           |  override def read(
           |    iter: AnythingIterator,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single = {
           |    require(iter.len.toByte >= 2, s"[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single] given input has only $${iter.len} bytes left, but we need 2 byte")
           |    // read iter
           |    val _1stBitIter = iter.iterator(bitAlignment = BitStringAlignment._16Bit)
           |    _1stBitIter.read(BitLength(14)) // read padding bits
           |    val field01 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 2))
           |    // create object
           |    de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single(
           |      field01 = field01
           |    )
           |  }
           |
           |  override def write(
           |    data: de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): Unit = {
           |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single] given input has no ByteStringBuilderHint")
           |    val _1stBitBuilder = BitStringBuilder.newBuilder(alignment = BitStringAlignment._16Bit)
           |    serializerContainer.write[Int](data.field01, hints = BitStringBuilderHint(_1stBitBuilder), SizeInBitHint(value = 2))
           |    findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append(_1stBitBuilder.result())
           |  }
           |}
           |new asBitfield_mock_unaligned_singleSerializer()
           |""".stripMargin
    )
  }
}
