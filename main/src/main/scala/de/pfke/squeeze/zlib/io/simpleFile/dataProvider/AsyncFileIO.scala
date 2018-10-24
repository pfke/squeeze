package de.pfke.squeeze.zlib.io.simpleFile.dataProvider

import java.awt.event.ActionEvent
import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file.Path
import javax.swing.Timer

import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.io._

import scala.concurrent.duration._

/**
  * TODO: no file watching implemented
  */
object AsyncFileIO {
  /**
    * Create-fn for easier java usage.
    */
  def apply(
    file: Path
  ) = new AsyncFileIO(file)

  /**
    * Create-fn for easier java usage.
    */
  def apply(
    file: Path,
    writeTimeoutInSeconds: Int
  ) = new AsyncFileIO(file, writeTimeoutInSeconds seconds)
}

class AsyncFileIO(
  file: Path,
  writeTimeout: Duration = 10.seconds,
  writeCallback: Option[String => Any] = None
) (
  implicit
  charset: Charset = StandardCharsets.ISO_8859_1
)
  extends DataProvider {
  // fields
  private var _writeTimer: Option[Timer] = None


  /**
    * Reads all data and return as string list.
    */
  override def read (): List[String] = readCompleteFile()

  /**
    * Write the given data
    *
    * @param content is the data to be written
    * @param force write now or at your own timed schedule
    */
  override def write (
    content: String,
    force: Boolean
  ): Unit = {
    _writeTimer.synchronized {

      _writeTimer match {
        case Some(x) => x.stop()
        case None =>
      }
      _writeTimer = None

      if (force) {
        file.write(content.asByteBuffer)
      } else {
        _writeTimer = Some(setupTimer(content))
      }
    }
  }

  /**
    * Read the given file and return all rows as a string list.
    */
  private def readCompleteFile (): List[String] = file.read().asString_utf8.split("\n").toList

  private def setupTimer (
    content: String
  ): Timer = {
    val timer = new Timer(writeTimeout.toMillis.toInt, (_: ActionEvent) => {
      file.write(content.asByteBuffer)
    })
    timer.setRepeats(false)
    timer.start()
    timer
  }
}
