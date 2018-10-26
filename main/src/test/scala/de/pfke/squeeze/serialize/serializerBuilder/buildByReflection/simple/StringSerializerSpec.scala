package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.simple

import de.pfke.squeeze.serialize.serializerBuilder.buildByReflection.BaseSpec

class StringSerializerSpec
  extends BaseSpec {
  "testing serializer for simple String type" when {
    checkThis[java.lang.String](
      prefix = Some("java.lang."),
      code = s"""
                |$baseImports
                |
                |class StringSerializer
                |  extends Serializer[String] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[String]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = None
                |  override protected def defaultSize = None
                |}
                |new StringSerializer()
                |""".stripMargin
    )

    checkThis[String](
      code = s"""
                |$baseImports
                |
                |class StringSerializer
                |  extends Serializer[String] {
                |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[String]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = None
                |  override protected def defaultSize = None
                |}
                |new StringSerializer()
                |""".stripMargin
    )
  }
}
