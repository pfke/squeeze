package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.simple

import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class DoubleSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Double type" when {
    checkThis[java.lang.Double](
      prefix = Some("java.lang.."),
      code = s"""
                |$baseImports
                |
                |class DoubleSerializer
                |  extends CompiledSerializer[Double] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Double]
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
                |$baseImports
                |
                |class DoubleSerializer
                |  extends CompiledSerializer[Double] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Double]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putDouble(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new DoubleSerializer()
                |""".stripMargin
    )

    checkThis[Double](
      code = s"""
                |$baseImports
                |
                |class DoubleSerializer
                |  extends CompiledSerializer[Double] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Double]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putDouble(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new DoubleSerializer()
                |""".stripMargin
    )
  }
}
