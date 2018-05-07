package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.simple

import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class BooleanSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Boolean type" when {
    checkThis[java.lang.Boolean](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class BooleanSerializer
                |  extends Serializer[java.lang.Boolean] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[java.lang.Boolean]
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
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class BooleanSerializer
                |  extends Serializer[Boolean] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Boolean]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(if (value) 1 else 0) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new BooleanSerializer()
                |""".stripMargin
    )

    checkThis[Boolean](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class BooleanSerializer
                |  extends Serializer[Boolean] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Boolean]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putByte(if (value) 1 else 0) })
                |  override protected def defaultSize = Some(ByteLength(1))
                |}
                |new BooleanSerializer()
                |""".stripMargin
    )
  }
}
