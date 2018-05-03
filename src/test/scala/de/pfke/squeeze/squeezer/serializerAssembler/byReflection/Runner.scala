package de.pfke.squeeze.squeezer.serializerAssembler.byReflection

import java.nio.ByteOrder
import java.time.LocalDateTime

import akka.util.ByteStringBuilder
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.serialize.serializerAssembler.byReflection.ByReflectionAssembler
import de.pfke.squeeze.serialize.serializerCompiler.SerializerCompiler
import de.pfke.squeeze.serialize.serializerHints.ByteStringBuilderHint

object Runner {
  def main (args: Array[String]): Unit = {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN

    val r1 = ByReflectionAssembler().assemble[Boolean]()
    val r2 = SerializerCompiler.compile(r1)

    println(LocalDateTime.now())
    val s1 = Squeezer().serialize(false)
    val s2 = Squeezer().deSerialize[Boolean](s1)
    println(LocalDateTime.now())
    (0 to 100).foreach { _ =>
      Squeezer().deSerialize[Boolean](Squeezer().serialize(false))
      println(LocalDateTime.now())

    }

    println()
  }
}
