package de.pfke.squeeze.serialize.serializerAssembler

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

case class AssembledSerializer[A](
  classTag: ClassTag[A],
  typeTag: ru.TypeTag[A],
  fullClassName: String,
  code: String
)
