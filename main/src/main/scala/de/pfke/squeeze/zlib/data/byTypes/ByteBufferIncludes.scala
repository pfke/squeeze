package de.pfke.squeeze.zlib.data.byTypes

import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.charset.{Charset, StandardCharsets}

import de.pfke.squeeze.zlib.data.byTypes.stream.ByteBufferBackedInputStream

object ByteBufferIncludes
  extends ByteBufferIncludes

trait ByteBufferIncludes {
  /**
    * Convert byte buffer to input stream
    */
  implicit def ByteBufferToInputStream(
    in: ByteBuffer
  ): InputStream = new ByteBufferBackedInputStream(in)

  implicit class ByteBufferFromByteBuffer(
    in: ByteBuffer
  ) {
    def asArray: Array[Byte] = {
      val out = new Array[Byte](in.remaining())
      in.get(out)
      in.flip()
      out
    }

    def asString_iso8859_1: String = asString(StandardCharsets.ISO_8859_1)
    def asString_utf8: String = asString(StandardCharsets.UTF_8)

    def asString(implicit charset: Charset = StandardCharsets.UTF_8): String = {
      try {
        val oldPos = in.position()
        val str = charset
          .newDecoder()
          .decode(in)
          .toString

        in.position(oldPos)
        str
      } catch {
        case _: Exception => ""
      }
    }
  }
}
