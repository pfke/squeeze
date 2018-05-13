package de.pfke.squeeze.serialize.serializerAssembler.byReflection.simple

import de.pfke.squeeze.serialize.serializerAssembler.byReflection.BaseSpec

class StringSerializerSpec
  extends BaseSpec {
  "testing serializer for simple String type" when {
    checkThis[java.lang.String](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class StringSerializer
                |  extends Serializer[java.lang.String] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[java.lang.String]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = None
                |  override protected def defaultSize = None
                |}
                |new StringSerializer()
                |""".stripMargin
    )

    checkThis[String](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class StringSerializer
                |  extends Serializer[String] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[String]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = None
                |  override protected def defaultSize = None
                |}
                |new StringSerializer()
                |""".stripMargin
    )
  }
}
