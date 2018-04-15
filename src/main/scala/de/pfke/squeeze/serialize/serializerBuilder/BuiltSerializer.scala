package de.pfke.squeeze.serialize.serializerBuilder

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

case class BuiltSerializer[A](
  classTag: ClassTag[A],
  typeTag: ru.TypeTag[A],
  fullClassName: String,
  code: String
)
