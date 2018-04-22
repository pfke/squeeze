package de.pfke.squeeze

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.core.data.collection.{AnythingIterator, BitStringAlignment}
import de.pfke.squeeze.core.refl.generic.GenericOps
import de.pfke.squeeze.serialize.serializerHints.{ByteStringBuilderHint, SerializerHint}
import de.pfke.squeeze.serialize.{SerializerContainer, SerializerWrapper}
import de.pfke.squeeze.zlib.{PatchLevelVersion, SerializerRunException}

import scala.collection.mutable
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object Squeezer {
  /**
    * Create w/o PatchLevelVersion
    */
  def apply()(
    implicit
    byteOrder: ByteOrder
  ): Squeezer = new Squeezer(initialFromVersion = None)

  /**
    * Create with parsed PatchLevelVersion
    */
  def apply(
    version: String
  ) (
    implicit
    byteOrder: ByteOrder
  ): Squeezer = apply(PatchLevelVersion.parse(version))

  /**
    * Create with PatchLevelVersion
    */
  def apply(
    version: PatchLevelVersion
  ) (
    implicit
    byteOrder: ByteOrder
  ): Squeezer = new Squeezer(initialFromVersion = Some(version))
}

class Squeezer(
  initialFromVersion: Option[PatchLevelVersion]
)(implicit
  byteOrder: ByteOrder
) extends SerializerContainer {
  implicit val serialzierContainer = this

  // fields
  private val _serializers = new mutable.HashMap[GenericOps.TypeInfo[_], SerializerWrapper[_]]()

  /**
    * Return the iface type of the given data (if its class is described with an annotation)
    */
  override def getIfaceType[A](
    in: A
  )(implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Long = {
    GenericOps
      .typeOf(in)
      .typeSymbol
      .annotations
      .getTypeForIface match {
      case Some(x) if x.value.isInstanceOf[Int] => x.value.asInstanceOf[Int]
      case Some(x) if x.value.isInstanceOf[Long] => x.value.asInstanceOf[Long]
      case Some(x) => throw new SerializerRunException(s"iface type for $typeTag is not in a valid format. Int or Long expected" +
        s"")
      case None => throw new SerializerRunException(s"could not find iface type for $typeTag")
    }
  }

  /**
    * Read from given input and deserialize to an object A
    *
    * @param hints how to deserialize (e.g. read bits or bytes, pass an iface type)
    * @param byteOrder BigEndian or LittleEndian
    * @param version only imported for ifaces
    * @return deserialzed object
    */
  def read[A](
    iter: AnythingIterator,
    hints: SerializerHint*
  )(implicit
    byteOrder: ByteOrder,
    version: Option[PatchLevelVersion] = initialFromVersion,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): A = getSerializer[A]().read(iter = iter, hints = hints:_*)

  /**
    * Serialize the input
    *
    * @param data is the input data
    * @param hints how to deserialize (e.g. read bits or bytes, pass an iface type)
    * @param byteOrder BigEndian or LittleEndian
    * @param version only imported for ifaces
    */
  def write[A](
    data: A,
    hints: SerializerHint*
  )(implicit
    byteOrder: ByteOrder,
    version: Option[PatchLevelVersion] = initialFromVersion,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Unit = getSerializer[A]().write(data = data, hints = hints:_*)

  /**
    * Read from given input and deserialize to an object A
    *
    * @param byteOrder BigEndian or LittleEndian
    * @param version only imported for ifaces
    * @return deserialzed object
    */
  def deSerialize[A](
    in: ByteString
  )(implicit
    byteOrder: ByteOrder,
    version: Option[PatchLevelVersion] = initialFromVersion,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ) = read[A](iter = AnythingIterator(in, bitAlignment = BitStringAlignment._32Bit))

  /**
    * Serialize the input
    *
    * @param data is the input data
    * @param byteOrder BigEndian or LittleEndian
    * @param version only imported for ifaces
    */
  def serialize[A](
    data: A
  )(implicit
    byteOrder: ByteOrder,
    version: Option[PatchLevelVersion] = initialFromVersion,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): ByteString = {
    val result = ByteString.newBuilder

    write[A](data = data, hints = ByteStringBuilderHint(result))

    result.result()
  }

  /**
    * Return a serializer for the given type
    */
  private def getSerializer[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): SerializerWrapper[A] = {
    _serializers
      .getOrElseUpdate(
        GenericOps.TypeInfo(classTag = classTag, typeTag = typeTag),
        new SerializerWrapper[A]()
      ).asInstanceOf[SerializerWrapper[A]]
  }
}
