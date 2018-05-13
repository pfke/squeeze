package de.pfke.squeeze.serialize.serializerAssembler.byReflection.simple

import de.pfke.squeeze.serialize.serializerAssembler.byReflection.BaseSpec

class ShortSerializerSpec
  extends BaseSpec {
  "testing serializer for simple Short type" when {
    checkThis[java.lang.Short](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class ShortSerializer
                |  extends Serializer[java.lang.Short] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[java.lang.Short]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new ShortSerializer()
                |""".stripMargin
    )

    checkThis[scala.Short](
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
                |class ShortSerializer
                |  extends Serializer[Short] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Short]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new ShortSerializer()
                |""".stripMargin
    )

    checkThis[Short](
      code = s"""
                |import de.pfke.squeeze.core._
                |import de.pfke.squeeze.core.data.collection._
                |import de.pfke.squeeze.core.data.length.digital._
                |import de.pfke.squeeze.core.refl.generic._
                |import de.pfke.squeeze.serialize._
                |import de.pfke.squeeze.serialize.serializerHints._
                |import java.nio.ByteOrder
                |
                |class ShortSerializer
                |  extends Serializer[Short] {
                |  override def objectTypeInfo = GenericOps.getTypeInfo[Short]
                |
                |  override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.putShort(value) })
                |  override protected def defaultSize = Some(ByteLength(2))
                |}
                |new ShortSerializer()
                |""".stripMargin
    )
  }
}
