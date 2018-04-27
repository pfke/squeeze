package de.pfke.squeeze.serialize

import java.nio.ByteOrder

import de.pfke.squeeze.core.PatchLevelVersion
import de.pfke.squeeze.core.data.collection.AnythingIterator
import de.pfke.squeeze.serialize.serializerHints.SerializerHint

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

trait SerializerContainer {
  /**
    * Return the iface type of the given data (if its class is described with an annotation)
    */
  @throws[SerializerBuildException]("if the class type has no ifaceType annotation")
  def getIfaceType[A](
    in: A
  )(implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Long

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
    version: Option[PatchLevelVersion],
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): A

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
    version: Option[PatchLevelVersion],
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Unit
}
