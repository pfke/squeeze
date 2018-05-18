package de.pfke.squeeze.serialize.serializerAssembler.byReflection

import java.nio.ByteOrder
import java.time.LocalDateTime

import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.serialize.serializerCompiler.SerializerCompiler
import de.pfke.squeeze.testing.mocks.FullOfSimpleTypesMock

object Runner {
  def main (args: Array[String]): Unit = {
    implicit val byteOrder = ByteOrder.BIG_ENDIAN

//    val r1 = ByReflectionAssembler().assemble[Boolean]()
    val r1 = ByReflectionAssembler().assemble[FullOfSimpleTypesMock]()
    val r2 = SerializerCompiler.compile(r1)

    println(LocalDateTime.now())
    val s1 = Squeezer().serialize[FullOfSimpleTypesMock](
      FullOfSimpleTypesMock(
        _1stParam = true,
        _2ndParam = 0x01,
        _3rdParam = 0x02,
        _4thParam = 3d,
        _5thParam = 4f,
        _6thParam = 5,
        _7thParam = 6,
        _8thParam = 7
      )
    )
    val s2 = Squeezer().deSerialize[FullOfSimpleTypesMock](s1)
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
