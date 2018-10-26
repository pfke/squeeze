package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.simple

import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class LongSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Long type" when {
    checkThis[java.lang.Long](
      prefix = Some("java.lang."),
      code = s"""
                |$baseImports
                |
                |class LongSerializer
                |  extends Serializer[Long] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Long]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putLong(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new LongSerializer()
                |""".stripMargin
    )

    checkThis[scala.Long](
      prefix = Some("scala."),
      code = s"""
                |$baseImports
                |
                |class LongSerializer
                |  extends Serializer[Long] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Long]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putLong(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new LongSerializer()
                |""".stripMargin
    )

    checkThis[Long](
      code = s"""
                |$baseImports
                |
                |class LongSerializer
                |  extends Serializer[Long] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[Long]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putLong(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new LongSerializer()
                |""".stripMargin
    )
  }
}
