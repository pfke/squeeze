package de.pfke.squeeze.squeezer.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec

class SqueezerOnLongSpec
  extends BaseSqueezerSpec {
  private val pojo = 0x42789789456l
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x04, 0x27, 0x89, 0x78, 0x94, 0x56
  )
  private val leBinaryData = ByteString(
    0x56, 0x94, 0x78, 0x89, 0x27, 0x04, 0x00, 0x00
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[Long](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[Long](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
