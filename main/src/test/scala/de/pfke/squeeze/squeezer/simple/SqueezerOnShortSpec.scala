package de.pfke.squeeze.squeezer.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec

class SqueezerOnShortSpec
  extends BaseSqueezerSpec {
  private val pojo = 2142.toShort
  private val beBinaryData = ByteString(
    0x08, 0x5e
  )
  private val leBinaryData = ByteString(
    0x5e, 0x08
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[Short](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[Short](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
