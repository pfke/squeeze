package de.pfke.squeeze.squeezer.serializerBuilder.byReflection.simple

import de.pfke.squeeze.squeezer.serializerBuilder.byReflection.BaseSpec

class CharSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Char type" when {
    checkThis[java.lang.Character](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class CharacterSerializer
                |  extends Serializer[java.lang.Character] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[java.lang.Character]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new CharacterSerializer()
                |""".stripMargin
    )

    checkThis[scala.Char](
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
                |class CharSerializer
                |  extends Serializer[Char] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[Char]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new CharSerializer()
                |""".stripMargin
    )

    checkThis[Char](
      code = s"""
                |import de.pintono.tools.squeeze.core.{Serializer, SerializerContainer}
                |import de.pintono.tools.squeeze.core.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SerializerHint, SizeInBitHint, SizeInByteHint}
                |import de.pintono.tools.squeeze.zlib.{PatchLevelVersion, ReflHelper}
                |import de.pintono.tools.squeeze.zlib.anythingString.AnythingIterator
                |import de.pintono.tools.squeeze.zlib.bitString.{BitStringAlignment, BitStringBuilder}
                |import de.pintono.tools.squeeze.zlib.length.digital.{BitLength, ByteLength}
                |import java.nio.ByteOrder
                |
                |class CharSerializer
                |  extends Serializer[Char] {
                |  override def objectTypeInfo = ReflHelper.generateTypeInfo[Char]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new CharSerializer()
                |""".stripMargin
    )
  }
}
