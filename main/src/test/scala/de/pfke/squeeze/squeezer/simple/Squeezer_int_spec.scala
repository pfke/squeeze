package de.pfke.squeeze.squeezer.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec

class Squeezer_int_spec
  extends BaseSqueezerSpec {
  private val pojo = 0x42453123
  private val beBinaryData = ByteString(
    0x42, 0x45, 0x31, 0x23
  )
  private val leBinaryData = ByteString(
    0x23, 0x31, 0x45, 0x42
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[Int](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[Int](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
