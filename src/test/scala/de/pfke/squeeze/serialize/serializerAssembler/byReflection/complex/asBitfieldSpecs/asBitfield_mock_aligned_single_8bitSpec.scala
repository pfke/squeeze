package de.pfke.squeeze.serialize.serializerAssembler.byReflection.complex.asBitfieldSpecs

import de.pfke.squeeze.testing.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit
import de.pfke.squeeze.serialize.serializerAssembler.byReflection.BaseSpec

class asBitfield_mock_aligned_single_8bitSpec
  extends BaseSpec {
  "testing a class with one aligned multi fields" when {
    checkThis[asBitfield_mock_aligned_single_8bit](
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
           |class asBitfield_mock_aligned_single_8bitSerializer
           |  extends Serializer[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit] {
           |  override def objectTypeInfo = GenericOps.getTypeInfo[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit]
           |
           |  override def read(
           |    iter: AnythingIterator,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit = {
           |    require(iter.len.toByte >= 1, s"[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit] given input has only $${iter.len} bytes left, but we need 1 byte")
           |    // read iter
           |    val _1stBitIter = iter.iterator(bitAlignment = BitStringAlignment._8Bit)
           |    val field01 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 8))
           |    // create object
           |    de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit(
           |      field01 = field01
           |    )
           |  }
           |
           |  override def write(
           |    data: de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): Unit = {
           |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pintono.tools.squeeze.core.mocks.asBitfieldSpecs.asBitfield_mock_aligned_single_8bit] given input has no ByteStringBuilderHint")
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
