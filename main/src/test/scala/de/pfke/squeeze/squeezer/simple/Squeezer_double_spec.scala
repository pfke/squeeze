package de.pfke.squeeze.squeezer.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec

class Squeezer_double_spec
  extends BaseSqueezerSpec {
  private val pojo = 42547891d
  private val beBinaryData = ByteString(
    0x41, 0x84, 0x49, 0xd5, 0x98, 0x00, 0x00, 0x00
  )
  private val leBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x98, 0xd5, 0x49, 0x84, 0x41
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[Double](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[Double](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
