package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.simple

import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class FloatSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Float type" when {
    checkThis[java.lang.Float](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class FloatSerializer
                |  extends Serializer[java.lang.Float] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[java.lang.Float]
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
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class FloatSerializer
                |  extends Serializer[Float] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Float]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putFloat(value) })
                |  override protected def defaultSize = Some(ByteLength(4))
                |}
                |new FloatSerializer()
                |""".stripMargin
    )

    checkThis[Float](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class FloatSerializer
                |  extends Serializer[Float] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Float]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putFloat(value) })
                |  override protected def defaultSize = Some(ByteLength(4))
                |}
                |new FloatSerializer()
                |""".stripMargin
    )
  }
}
