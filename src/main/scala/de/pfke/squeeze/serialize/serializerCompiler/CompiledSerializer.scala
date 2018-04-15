package de.pfke.squeeze.serialize.serializerCompiler

import de.pfke.grind.refl.squeeze.serialize.Serializer
import de.pfke.grind.refl.squeeze.serialize.serializerBuilder.BuiltSerializer

case class CompiledSerializer[A](
  builtSerializer: BuiltSerializer[A],
  serializerObject: Serializer[A]
)
