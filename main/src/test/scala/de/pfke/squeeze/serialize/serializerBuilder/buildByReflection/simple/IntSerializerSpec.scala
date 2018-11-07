package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.simple

import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class IntSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Int type" when {
    checkThis[java.lang.Integer](
      prefix = Some("java.lang."),
      code = s"""
                |$baseImports
                |
                |class IntegerSerializer
                |  extends CompiledSerializer[Integer] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Integer]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putInt(value) })
                |  override protected def defaultSize = Some(ByteLength(4))
                |}
                |new IntegerSerializer()
                |""".stripMargin
    )

    checkThis[scala.Int](
      prefix = Some("scala."),
      code = s"""
                |$baseImports
                |
                |class IntSerializer
                |  extends CompiledSerializer[Int] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Int]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putInt(value) })
                |  override protected def defaultSize = Some(ByteLength(4))
                |}
                |new IntSerializer()
                |""".stripMargin
    )

    checkThis[Int](
      code = s"""
                |$baseImports
                |
                |class IntSerializer
                |  extends CompiledSerializer[Int] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Int]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putInt(value) })
                |  override protected def defaultSize = Some(ByteLength(4))
                |}
                |new IntSerializer()
                |""".stripMargin
    )
  }
}
