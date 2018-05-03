package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.simple

import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class LongSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Long type" when {
    checkThis[java.lang.Long](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class LongSerializer
                |  extends Serializer[java.lang.Long] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[java.lang.Long]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putLong(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new LongSerializer()
                |""".stripMargin
    )

    checkThis[scala.Long](
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
                |class LongSerializer
                |  extends Serializer[Long] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[Long]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putLong(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new LongSerializer()
                |""".stripMargin
    )

    checkThis[Long](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class LongSerializer
                |  extends Serializer[Long] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[Long]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putLong(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new LongSerializer()
                |""".stripMargin
    )
  }
}
