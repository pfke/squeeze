package de.pfke.squeeze.squeezer.simple

import java.nio.ByteOrder
import java.nio.charset.StandardCharsets

import akka.util.ByteString
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.squeezer.BaseSqueezerSpec

class SqueezerOnStringSpec
  extends BaseSqueezerSpec {
  private val pojo = "hallo du"
  private val beBinaryData = ByteString(
    0x68, 0x61, 0x6c, 0x6c, 0x6f, 0x20, 0x64, 0x75
  )
  private val leBinaryData = ByteString(
    0x68, 0x61, 0x6c, 0x6c, 0x6f, 0x20, 0x64, 0x75
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[String](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[String](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
