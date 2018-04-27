package de.pfke.squeeze.squeezer.serializerBuilder.byReflection.simple

import de.pfke.squeeze.squeezer.serializerBuilder.byReflection.BaseSpec

class StringSerializerSpec
  extends BaseSpec {
  "testing serializer for simple String type" when {
    checkThis[java.lang.String](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class StringSerializer
                |  extends Serializer[java.lang.String] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[java.lang.String]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = None
                |  override protected def defaultSize = None
                |}
                |new StringSerializer()
                |""".stripMargin
    )

    checkThis[String](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class StringSerializer
                |  extends Serializer[String] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[String]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = None
                |  override protected def defaultSize = None
                |}
                |new StringSerializer()
                |""".stripMargin
    )
  }
}
