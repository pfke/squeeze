package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.simple

import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class CharSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Char type" when {
    checkThis[java.lang.Character](
      prefix = Some("java.lang."),
      code = s"""
                |$baseImports
                |
                |class CharacterSerializer
                |  extends Serializer[Character] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Character]
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
                |$baseImports
                |
                |class CharSerializer
                |  extends Serializer[Char] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Char]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new CharSerializer()
                |""".stripMargin
    )

    checkThis[Char](
      code = s"""
                |$baseImports
                |
                |class CharSerializer
                |  extends Serializer[Char] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Char]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new CharSerializer()
                |""".stripMargin
    )
  }
}
