package de.pfke.squeeze.serialize.serializerAssembler

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

abstract class SerializerAssembler {
  /**
    * Build the serializer
    */
  def assemble[A]() (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): AssembledSerializer[A]
}
