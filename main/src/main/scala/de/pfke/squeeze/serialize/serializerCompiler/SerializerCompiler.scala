package de.pfke.squeeze.serialize.serializerCompiler

import javax.script.{Compilable, ScriptEngineManager}

import de.pfke.squeeze.serialize.Serializer
import de.pfke.squeeze.serialize.serializerBuilder.BuiltSerializer

object SerializerCompiler {
  // fields
  private val _engine = initEngine()

  /**
    * Compile the given script and return the result: the object itself
    */
  def compile[A](
    reflectedSerializer: BuiltSerializer[A]
  ): CompiledSerializer[A] = {
    val script = _engine.compile(reflectedSerializer.code)
    val compiled = script.eval().asInstanceOf[Serializer[A]]

    CompiledSerializer(reflectedSerializer, compiled)
  }

  /**
    * Initialize the script engine
    */
  private def initEngine(): Compilable = {
    val sem = new ScriptEngineManager()
    val engine = sem.getEngineByName("scala")

    require(engine != null, "could not find scala script engine")
    require(engine.isInstanceOf[scala.tools.nsc.interpreter.Scripted], "returned engine not derived from 'scala.tools.nsc.interpreter.Scripted'")

    val settings = engine.asInstanceOf[scala.tools.nsc.interpreter.Scripted].intp.settings

    settings.usejavacp.value = true

    engine.asInstanceOf[Compilable]
  }
}
