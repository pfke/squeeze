package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.simple

import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class CharSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Char type" when {
    checkThis[java.lang.Character](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class CharacterSerializer
                |  extends Serializer[java.lang.Character] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[java.lang.Character]
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
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class CharSerializer
                |  extends Serializer[Char] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Char]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new CharSerializer()
                |""".stripMargin
    )

    checkThis[Char](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class CharSerializer
                |  extends Serializer[Char] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Char]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new CharSerializer()
                |""".stripMargin
    )
  }
}
