package de.pfke.squeeze.squeezer.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec

class Squeezer_char_spec
  extends BaseSqueezerSpec {
  private val pojo = 4231.toChar
  private val beBinaryData = ByteString(
    0x10, 0x87
  )
  private val leBinaryData = ByteString(
    0x87, 0x10
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[Char](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[Char](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
