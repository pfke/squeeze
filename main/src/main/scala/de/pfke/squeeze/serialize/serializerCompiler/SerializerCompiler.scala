package de.pfke.squeeze.serialize.serializerCompiler

import de.pfke.squeeze.serialize.WrappedSerializer
import javax.script.{Compilable, ScriptEngineManager}
import de.pfke.squeeze.serialize.serializerBuilder.BuiltSerializer

object SerializerCompiler {
  // fields
  private val _engine = this.synchronized { initEngine() }

  /**
    * Compile the given script and return the result: the object itself
    */
  def compile[A](
    builtSerializer: BuiltSerializer[A]
  ): WrappedSerializer[A] = {
    val script = _engine.compile(builtSerializer.code)
    val compiled = script.eval().asInstanceOf[CompiledSerializer[A]]

    WrappedSerializer(builtSerializer, compiled)
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
