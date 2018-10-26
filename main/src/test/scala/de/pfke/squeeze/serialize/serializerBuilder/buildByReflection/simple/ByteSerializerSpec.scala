package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.simple

import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class ByteSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Byte type" when {
    checkThis[java.lang.Byte](
      prefix = Some("java.lang."),
      code = s"""
                |$baseImports
                |
                |class ByteSerializer
                |  extends Serializer[Byte] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Byte]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(value) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new ByteSerializer()
                |""".stripMargin
    )

    checkThis[scala.Byte](
      prefix = Some("scala."),
      code = s"""
                |$baseImports
                |
                |class ByteSerializer
                |  extends Serializer[Byte] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Byte]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(value) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new ByteSerializer()
                |""".stripMargin
    )

    checkThis[Byte](
      code = s"""
                |$baseImports
                |
                |class ByteSerializer
                |  extends Serializer[Byte] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Byte]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(value) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new ByteSerializer()
                |""".stripMargin
    )
  }
}
