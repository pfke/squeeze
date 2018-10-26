package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.simple

import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class BooleanSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Boolean type" when {
    checkThis[java.lang.Boolean](
      prefix = Some("java.lang."),
      code = s"""
                |$baseImports
                |
                |class BooleanSerializer
                |  extends Serializer[Boolean] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Boolean]
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
                |$baseImports
                |
                |class BooleanSerializer
                |  extends Serializer[Boolean] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Boolean]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(if (value) 1 else 0) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new BooleanSerializer()
                |""".stripMargin
    )

    checkThis[Boolean](
      code = s"""
                |$baseImports
                |
                |class BooleanSerializer
                |  extends Serializer[Boolean] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Boolean]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(if (value) 1 else 0) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new BooleanSerializer()
                |""".stripMargin
    )
  }
}
