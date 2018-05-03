package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.simple

import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class BooleanSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Boolean type" when {
    checkThis[java.lang.Boolean](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class BooleanSerializer
                |  extends Serializer[java.lang.Boolean] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[java.lang.Boolean]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(if (value) 1 else 0) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new BooleanSerializer()
                |""".stripMargin
    )

    checkThis[scala.Boolean](
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
                |class BooleanSerializer
                |  extends Serializer[Boolean] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[Boolean]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(if (value) 1 else 0) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new BooleanSerializer()
                |""".stripMargin
    )

    checkThis[Boolean](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class BooleanSerializer
                |  extends Serializer[Boolean] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[Boolean]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(if (value) 1 else 0) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new BooleanSerializer()
                |""".stripMargin
    )
  }
}
