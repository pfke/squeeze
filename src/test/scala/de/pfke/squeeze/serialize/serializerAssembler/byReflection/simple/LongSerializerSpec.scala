package de.pfke.squeeze.serialize.serializerAssembler.byReflection.simple

import de.pfke.squeeze.serialize.serializerAssembler.byReflection.BaseSpec

class LongSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Long type" when {
    checkThis[java.lang.Long](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class LongSerializer
                |  extends Serializer[java.lang.Long] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[java.lang.Long]
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
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class LongSerializer
                |  extends Serializer[Long] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Long]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putLong(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new LongSerializer()
                |""".stripMargin
    )

    checkThis[Long](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class LongSerializer
                |  extends Serializer[Long] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Long]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putLong(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new LongSerializer()
                |""".stripMargin
    )
  }
}
