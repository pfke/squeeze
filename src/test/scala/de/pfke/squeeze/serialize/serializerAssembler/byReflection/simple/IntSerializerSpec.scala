package de.pfke.squeeze.serialize.serializerAssembler.byReflection.simple

import de.pfke.squeeze.serialize.serializerAssembler.byReflection.BaseSpec

class IntSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Int type" when {
    checkThis[java.lang.Integer](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class IntegerSerializer
                |  extends Serializer[java.lang.Integer] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[java.lang.Integer]
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
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class IntSerializer
                |  extends Serializer[Int] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Int]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putInt(value) })
                |  override protected def defaultSize = Some(ByteLength(4))
                |}
                |new IntSerializer()
                |""".stripMargin
    )

    checkThis[Int](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class IntSerializer
                |  extends Serializer[Int] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Int]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putInt(value) })
                |  override protected def defaultSize = Some(ByteLength(4))
                |}
                |new IntSerializer()
                |""".stripMargin
    )
  }
}
