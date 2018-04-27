package de.pfke.squeeze.serialize

import de.pfke.squeeze.serialize.serializerBuilder.BuiltSerializer
import de.pfke.squeeze.serialize.serializerBuilder.byReflection.ByReflectionBuilder
import de.pfke.squeeze.serialize.serializerCompiler.{CompiledSerializer, SerializerCompiler}

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object SerializerFactory {
  // fields
  private val _builder = ByReflectionBuilder()
  private val _compiler = SerializerCompiler

  /**
    * Build serializer code string from given type.
    */
  def build[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): BuiltSerializer[A] = _builder.build()

  /**
    * Build AND compile serizalizer from given type
    */
  def compile[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): CompiledSerializer[A] = _compiler.compile(build())
}
