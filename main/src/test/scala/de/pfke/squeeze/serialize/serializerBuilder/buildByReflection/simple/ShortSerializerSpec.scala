package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.simple

import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class ShortSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Short type" when {
    checkThis[java.lang.Short](
      prefix = Some("java.lang."),
      code = s"""
                |$baseImports
                |
                |class ShortSerializer
                |  extends CompiledSerializer[Short] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Short]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new ShortSerializer()
                |""".stripMargin
    )

    checkThis[scala.Short](
      prefix = Some("scala."),
      code = s"""
                |$baseImports
                |
                |class ShortSerializer
                |  extends CompiledSerializer[Short] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Short]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new ShortSerializer()
                |""".stripMargin
    )

    checkThis[Short](
      code = s"""
                |$baseImports
                |
                |class ShortSerializer
                |  extends CompiledSerializer[Short] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Short]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new ShortSerializer()
                |""".stripMargin
    )
  }
}
