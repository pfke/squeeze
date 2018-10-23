package de.pfke.squeeze.testing

import java.nio.file.{Files, Path}

import org.scalatest.{BeforeAndAfterAll, Suite}

import scala.collection.{JavaConverters, mutable}

trait FileCleanAfterAll
  extends BeforeAndAfterAll { this: Suite =>
  // fields
  val _filesToDelete = new mutable.HashSet[Path]()

  override protected def afterAll (): Unit = {
    super.afterAll()

    _filesToDelete.foreach { i => deletePath(i) }
  }

  protected def cleanFileAfter(file: Path): Path = {
    synchronized {
      _filesToDelete += file
      file
    }}

  /**
    * Delete given path.
    */
  private def deletePath(path: Path): Unit = {
    if (!Files.exists(path))
      return

    if (Files.isDirectory(path)) {
      JavaConverters.asScalaIterator(
        Files
          .newDirectoryStream(path)
          .iterator()
      )
        .foreach(deletePath)
    }

    Files.delete(path)
  }
}
