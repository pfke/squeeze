package de.pfke.squeeze.zlib.data.byTypes.array

import java.nio.charset.{StandardCharsets, Charset}

object ArrayIncludes
  extends ArrayIncludes

trait ArrayIncludes {
  /**
    * Convert an array to a string
    */
  implicit def ArrayToString (
    in: Array[Byte]
  ) (
    implicit
    charset: Charset = StandardCharsets.UTF_8
  ): String = new String(in, charset)

  /**
    * Utils for byte arrays.
    */
  implicit class ByteArrayUtils (
    value: Array[Byte]
  ) {
    /**
      * Convert byte array to hex string.
      */
    def toHex: String = value.map("%02x" format _).mkString
  }
}
