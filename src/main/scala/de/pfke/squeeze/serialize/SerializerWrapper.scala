package de.pfke.squeeze.serialize

import java.nio.ByteOrder

import de.pfke.squeeze.core.PatchLevelVersion
import de.pfke.squeeze.core.data.collection.AnythingIterator
import de.pfke.squeeze.core.refl.generic.GenericOps.TypeInfo
import de.pfke.squeeze.serialize.serializerCompiler.CompiledSerializer
import de.pfke.squeeze.serialize.serializerHints.SerializerHint

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

/**
  * This class is used to wrap a (e.g. reflected) and compiled serializer and ist read and write calls
  */
class SerializerWrapper[A]()(
  implicit
  classTag: ClassTag[A],
  typeTag: ru.TypeTag[A]
) extends Serializer[A] {
  // fields
  private val _serizalizer: CompiledSerializer[A] = SerializerFactory.compile()

  /**
    * Return type info for the serialized/deserialized object
    *
    * @return classtag and type tag
    */
  override def objectTypeInfo: TypeInfo[A] = _serizalizer.serializerObject.objectTypeInfo

  /**
    * Read from given input and deserialize to an object A
    *
    * @param hints               how to deserialize (e.g. read bits or bytes, pass an iface type)
    * @param byteOrder           BigEndian or LittleEndian
    * @param serializerContainer can be asked for sub types
    * @param version             only imported for ifaces
    * @return deserialzed object
    */
  override def read (
    iter: AnythingIterator,
    hints: SerializerHint*
  )(
    implicit byteOrder: ByteOrder,
    serializerContainer: SerializerContainer,
    version: Option[PatchLevelVersion]
  ): A = _serizalizer.serializerObject.read(iter = iter, hints = hints:_*)

  /**
    * Serialize the input
    *
    * @param data                is the input data
    * @param hints               how to deserialize (e.g. read bits or bytes, pass an iface type)
    * @param byteOrder           BigEndian or LittleEndian
    * @param serializerContainer is the object which can call write code on complex sub objects
    * @param version             only imported for ifaces
    */
  override def write (
    data: A,
    hints: SerializerHint*
  )(
    implicit byteOrder: ByteOrder,
    serializerContainer: SerializerContainer,
    version: Option[PatchLevelVersion]
  ): Unit = _serizalizer.serializerObject.write(data = data, hints = hints:_*)
}
