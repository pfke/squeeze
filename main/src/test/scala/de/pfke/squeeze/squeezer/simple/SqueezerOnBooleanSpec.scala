package de.pfke.squeeze.squeezer.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec

class SqueezerOnBooleanSpec
  extends BaseSqueezerSpec {
  val pojo = true
  val beBinaryData = ByteString(
    0x01
  )
  val leBinaryData = ByteString(
    0x01
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[Boolean](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[Boolean](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
