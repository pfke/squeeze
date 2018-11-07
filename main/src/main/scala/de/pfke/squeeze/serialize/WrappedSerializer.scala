package de.pfke.squeeze.serialize

import de.pfke.squeeze.serialize.serializerBuilder.BuiltSerializer
import de.pfke.squeeze.serialize.serializerCompiler.CompiledSerializer

case class WrappedSerializer[A](
  builtSerializer: BuiltSerializer[A],
  compiledSerializer: CompiledSerializer[A]
)
