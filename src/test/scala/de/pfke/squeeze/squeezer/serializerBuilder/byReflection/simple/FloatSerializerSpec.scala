package de.pfke.squeeze.squeezer.serializerBuilder.byReflection.simple

import de.pfke.squeeze.squeezer.serializerBuilder.byReflection.BaseSpec

class FloatSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Float type" when {
    checkThis[java.lang.Float](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class FloatSerializer
                |  extends Serializer[java.lang.Float] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[java.lang.Float]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putFloat(value) })
                |  override protected def defaultSize = Some(ByteLength(4))
                |}
                |new FloatSerializer()
                |""".stripMargin
    )

    checkThis[scala.Float](
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
                |class FloatSerializer
                |  extends Serializer[Float] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[Float]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putFloat(value) })
                |  override protected def defaultSize = Some(ByteLength(4))
                |}
                |new FloatSerializer()
                |""".stripMargin
    )

    checkThis[Float](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class FloatSerializer
                |  extends Serializer[Float] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[Float]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putFloat(value) })
                |  override protected def defaultSize = Some(ByteLength(4))
                |}
                |new FloatSerializer()
                |""".stripMargin
    )
  }
}
