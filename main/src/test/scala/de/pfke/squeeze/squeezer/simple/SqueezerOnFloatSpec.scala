package de.pfke.squeeze.squeezer.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec

class SqueezerOnFloatSpec
  extends BaseSqueezerSpec {
  private val pojo = 425456213f
  private val beBinaryData = ByteString(
    0x4d, 0xca, 0xdf, 0x93
  )
  private val leBinaryData = ByteString(
    0x93, 0xdf, 0xca, 0x4d
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[Float](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[Float](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
