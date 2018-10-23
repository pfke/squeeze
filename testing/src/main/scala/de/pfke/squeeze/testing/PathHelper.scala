package de.pfke.squeeze.testing

import java.io.{BufferedWriter, OutputStreamWriter}
import java.nio.file.{Files, Path}

import scala.util.Random

object PathHelper {
  def createRandomFile (
    filename: String
  ): Path = createFile(filename, new Random().nextString(1024))

  def createFile (
    filename: String,
    content: String
  ): Path = writeToFile(Files.createTempFile(filename, null), content)

  def writeToFile(
    file: Path,
    content: String
    ): Path = {
    if(Files.exists(file)) {
      Files.deleteIfExists(file)
      file.toFile.createNewFile()
    }

    val writer = new BufferedWriter(
      new OutputStreamWriter(Files.newOutputStream(file), "utf-8"))

    writer.write(content)
    writer.close()

    file
  }
}
