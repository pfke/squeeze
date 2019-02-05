package de.pfke.squeeze.squeezer.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec

class SqueezerOnByteSpec
  extends BaseSqueezerSpec {
  val pojo = 42.toByte
  val beBinaryData = ByteString(
    0x2a
  )
  val leBinaryData = ByteString(
    0x2a
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[Byte](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[Byte](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
