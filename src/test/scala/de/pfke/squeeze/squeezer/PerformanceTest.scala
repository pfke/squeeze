package de.pfke.squeeze.squeezer

import java.nio.ByteOrder

import de.pfke.squeeze.Squeezer

object PerformanceTest {
  def main (args: Array[String]) {
    val squeezer = Squeezer()(ByteOrder.BIG_ENDIAN)

//    val sw = new StopWatch()
//
//    sw.start()
//    squeezer.write[Boolean](true)
//    sw.round("1st run")
//    (0 to 1000000).foreach { i => squeezer.write[Boolean](false) }
//    sw.round("xst run")
//    sw.stop()
//
//    println(sw.overview)
//
//
//    val r1 = ByteString.newBuilder
//    val r2 = r1.putLong(1)(ByteOrder.BIG_ENDIAN)
//
//    println()
  }
}
