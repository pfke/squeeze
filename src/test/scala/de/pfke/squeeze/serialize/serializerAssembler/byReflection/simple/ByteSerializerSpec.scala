package de.pfke.squeeze.serialize.serializerAssembler.byReflection.simple

import de.pfke.squeeze.serialize.serializerAssembler.byReflection.BaseSpec

class ByteSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Byte type" when {
    checkThis[java.lang.Byte](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class ByteSerializer
                |  extends Serializer[java.lang.Byte] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[java.lang.Byte]
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
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class ByteSerializer
                |  extends Serializer[Byte] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Byte]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(value) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new ByteSerializer()
                |""".stripMargin
    )

    checkThis[Byte](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class ByteSerializer
                |  extends Serializer[Byte] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Byte]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(value) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new ByteSerializer()
                |""".stripMargin
    )
  }
}
