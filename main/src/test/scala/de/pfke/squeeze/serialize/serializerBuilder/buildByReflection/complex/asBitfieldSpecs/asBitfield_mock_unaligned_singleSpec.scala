package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.complex.asBitfieldSpecs

import de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class asBitfield_mock_unaligned_singleSpec
  extends BaseSpec {
  "testing a class with one aligned multi fields" when {
    checkThis[asBitfield_mock_unaligned_single](
      code =
        s"""
           |import de.pfke.squeeze.zlib.version.PatchLevelVersion
           |import de.pfke.squeeze.zlib.data.collection.anythingString.AnythingIterator
           |import de.pfke.squeeze.zlib.data.collection.bitString.{BitStringAlignment, BitStringBuilder}
           |import de.pfke.squeeze.zlib.data.length.digital.{BitLength, ByteLength}
           |import de.pfke.squeeze.zlib.refl.GeneralRefl
           |import de.pfke.squeeze.serialize.{Serializer, SerializerContainer}
           |import de.pfke.squeeze.serialize.serializerHints._
           |import de.pfke.squeeze.zlib._
           |import java.nio.ByteOrder
           |
           |class asBitfield_mock_unaligned_singleSerializer
           |  extends Serializer[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single] {
           |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single]
           |
           |  override def read(
           |    iter: AnythingIterator,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single = {
           |    require(iter.len.toByte >= 2, s"[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single] given input has only $${iter.len} bytes left, but we need 2 byte")
           |    // read iter
           |    val _1stBitIter = iter.iterator(bitAlignment = BitStringAlignment._16Bit)
           |    _1stBitIter.read[Long](BitLength(14)) // read padding bits
           |    val field01 = serializerContainer.read[Int](_1stBitIter, hints = SizeInBitHint(value = 2))
           |    // create object
           |    de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single(
           |      field01 = field01
           |    )
           |  }
           |
           |  override def write(
           |    data: de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single,
           |    hints: SerializerHint*
           |  )(
           |    implicit
           |    byteOrder: ByteOrder,
           |    serializerContainer: SerializerContainer,
           |    version: Option[PatchLevelVersion]
           |  ): Unit = {
           |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.asBitfieldSpecs.asBitfield_mock_unaligned_single] given input has no ByteStringBuilderHint")
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
