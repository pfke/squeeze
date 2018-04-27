package de.pfke.squeeze.squeezer.serializerBuilder.byReflection.simple

import de.pfke.squeeze.squeezer.serializerBuilder.byReflection.BaseSpec

class ByteSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Byte type" when {
    checkThis[java.lang.Byte](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class ByteSerializer
                |  extends Serializer[java.lang.Byte] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[java.lang.Byte]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(value) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new ByteSerializer()
                |""".stripMargin
    )

    checkThis[scala.Byte](
      prefix = Some("scala."),
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class ByteSerializer
                |  extends Serializer[Byte] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[Byte]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(value) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new ByteSerializer()
                |""".stripMargin
    )

    checkThis[Byte](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class ByteSerializer
                |  extends Serializer[Byte] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[Byte]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(value) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new ByteSerializer()
                |""".stripMargin
    )
  }
}
