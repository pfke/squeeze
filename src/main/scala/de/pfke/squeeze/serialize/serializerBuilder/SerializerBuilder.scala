package de.pfke.squeeze.serialize.serializerBuilder

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

abstract class SerializerBuilder {
  /**
    * Build the serializer
    */
  def build[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): BuiltSerializer[A]
}
