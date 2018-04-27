package de.pfke.squeeze.squeezer.serializerBuilder.byReflection.simple

import de.pfke.squeeze.squeezer.serializerBuilder.byReflection.BaseSpec

class DoubleSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Double type" when {
    checkThis[java.lang.Double](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class DoubleSerializer
                |  extends Serializer[java.lang.Double] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[java.lang.Double]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putDouble(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new DoubleSerializer()
                |""".stripMargin
    )

    checkThis[scala.Double](
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
                |class DoubleSerializer
                |  extends Serializer[Double] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[Double]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putDouble(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new DoubleSerializer()
                |""".stripMargin
    )

    checkThis[Double](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class DoubleSerializer
                |  extends Serializer[Double] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[Double]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putDouble(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new DoubleSerializer()
                |""".stripMargin
    )
  }
}
