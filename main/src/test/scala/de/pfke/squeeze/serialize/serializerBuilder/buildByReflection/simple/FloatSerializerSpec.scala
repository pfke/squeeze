package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.simple

import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class FloatSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Float type" when {
    checkThis[java.lang.Float](
      prefix = Some("java.lang."),
      code = s"""
                |$baseImports
                |
                |class FloatSerializer
                |  extends CompiledSerializer[Float] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Float]
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
                |$baseImports
                |
                |class FloatSerializer
                |  extends CompiledSerializer[Float] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Float]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putFloat(value) })
                |  override protected def defaultSize = Some(ByteLength(4))
                |}
                |new FloatSerializer()
                |""".stripMargin
    )

    checkThis[Float](
      code = s"""
                |$baseImports
                |
                |class FloatSerializer
                |  extends CompiledSerializer[Float] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Float]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putFloat(value) })
                |  override protected def defaultSize = Some(ByteLength(4))
                |}
                |new FloatSerializer()
                |""".stripMargin
    )
  }
}
