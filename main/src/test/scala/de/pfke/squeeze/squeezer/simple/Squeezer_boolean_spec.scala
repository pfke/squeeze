package de.pfke.squeeze.squeezer.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec

class Squeezer_boolean_spec
  extends BaseSqueezerSpec {
  private val pojo = true
  private val beBinaryData = ByteString(
    0x01
  )
  private val leBinaryData = ByteString(
    0x01
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[Boolean](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[Boolean](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
