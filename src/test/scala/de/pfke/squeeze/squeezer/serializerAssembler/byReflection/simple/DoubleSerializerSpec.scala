package de.pfke.squeeze.squeezer.serializerAssembler.byReflection.simple

import de.pfke.squeeze.squeezer.serializerAssembler.byReflection.BaseSpec

class DoubleSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Double type" when {
    checkThis[java.lang.Double](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class DoubleSerializer
                |  extends Serializer[java.lang.Double] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[java.lang.Double]
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
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class DoubleSerializer
                |  extends Serializer[Double] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Double]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putDouble(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new DoubleSerializer()
                |""".stripMargin
    )

    checkThis[Double](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class DoubleSerializer
                |  extends Serializer[Double] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Double]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putDouble(value) })
                |  override protected def defaultSize = Some(ByteLength(8))
                |}
                |new DoubleSerializer()
                |""".stripMargin
    )
  }
}
