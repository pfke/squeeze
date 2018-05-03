package de.pfke.squeeze.serialize.serializerCompiler

import de.pfke.squeeze.serialize.Serializer
import de.pfke.squeeze.serialize.serializerAssembler.AssembledSerializer

case class CompiledSerializer[A](
  builtSerializer: AssembledSerializer[A],
  serializerObject: Serializer[A]
)
