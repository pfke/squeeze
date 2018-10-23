package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.aha

import de.pfke.squeeze.serialize.mocks.aha.Poll
import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class PollBodySpec
  extends BaseSpec {
  "testing serializer for Enum1Mock type" when {
    checkThis[Poll](
      code = s"""
                |import de.pfke.squeeze.zlib.version.PatchLevelVersion
                |import de.pfke.squeeze.zlib.data.collection.anythingString.AnythingIterator
                |import de.pfke.squeeze.zlib.data.collection.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pfke.squeeze.zlib.data.length.digital.{BitLength, ByteLength}
                |import de.pintono.grind.refl.core.GeneralRefl
                |import de.pfke.squeeze.serialize.{Serializer, SerializerContainer}
                |import de.pfke.squeeze.serialize.serializerHints._
                |import de.pfke.squeeze.zlib._
                |import java.nio.ByteOrder
                |
                |class PollSerializer
                |  extends Serializer[de.pfke.squeeze.serialize.mocks.aha.Poll] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[de.pfke.squeeze.serialize.mocks.aha.Poll]
                |
                |  override def read(
                |    iter: AnythingIterator,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): de.pfke.squeeze.serialize.mocks.aha.Poll = {
                |    require(iter.len.toByte >= 4, s"[de.pfke.squeeze.serialize.mocks.aha.Poll] given input has only $${iter.len} bytes left, but we need 4 byte")
                |    // read iter
                |    val number = serializerContainer.read[Int](iter)
                |    // create object
                |    de.pfke.squeeze.serialize.mocks.aha.Poll(
                |      number = number
                |    )
                |  }
                |
                |  override def write(
                |    data: de.pfke.squeeze.serialize.mocks.aha.Poll,
                |    hints: SerializerHint*
                |  )(
                |    implicit
                |    byteOrder: ByteOrder,
                |    serializerContainer: SerializerContainer,
                |    version: Option[PatchLevelVersion]
                |  ): Unit = {
                |    require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[de.pfke.squeeze.serialize.mocks.aha.Poll] given input has no ByteStringBuilderHint")
                |    serializerContainer.write[Int](data.number, hints = hints:_*)
                |  }
                |}
                |new PollSerializer()
                |""".stripMargin
    )
  }
}
