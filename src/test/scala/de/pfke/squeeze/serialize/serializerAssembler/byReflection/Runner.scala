package de.pfke.squeeze.serialize.serializerAssembler.byReflection

import java.nio.ByteOrder
import java.time.LocalDateTime

import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.serialize.serializerCompiler.SerializerCompiler

object Runner {
  def main (args: Array[String]): Unit = {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN

//    val r1 = ByReflectionAssembler().assemble[Boolean]()
    val r1 = ByReflectionAssembler().assemble[Byte]()
    val r2 = SerializerCompiler.compile(r1)

    println(LocalDateTime.now())
    val s1 = Squeezer().serialize[Byte](31)
    val s2 = Squeezer().deSerialize[Byte](s1)
    println(s2)
//    println(LocalDateTime.now())
//    (0 to 100).foreach { _ =>
//      Squeezer().deSerialize[Boolean](Squeezer().serialize(false))
//      println(LocalDateTime.now())
//
//    }
//
//    println()
  }
}
