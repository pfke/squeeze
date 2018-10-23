package de.pfke.squeeze.zlib.data.byTypes.stream

import java.io.{InputStream, OutputStream}
import java.nio.ByteBuffer

import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object StreamIncludes
  extends StreamIncludes

trait StreamIncludes {
  /**
    * Convert a stream into a byte buffer.
    */
  implicit def StreamToByteBuffer(
    is: InputStream
  ): ByteBuffer = {
    val bb = ByteBuffer.allocate(is.available())

    is.read(bb.array())
    bb
  }

  implicit class InputStreamOps(in: InputStream) {
    def >(out: OutputStream): Unit = pipeTo(out)

    def pipeTo(out: OutputStream, bufferSize: Int = 1<<10): Unit = Future { pipeTo(out, Array.ofDim[Byte](bufferSize)) }

    @tailrec final def pipeTo(out: OutputStream, buffer: Array[Byte]): Unit = {
      in.read(buffer) match {
        case n if n > 0 =>
          println(n)
          out.write(buffer, 0, n)
          pipeTo(out, buffer)
        case _ =>
          in.close()
          out.close()
      }
    }
  }
}
