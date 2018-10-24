package de.pfke.squeeze.zlib.io.jio

import java.io._
import java.nio.file.Path

import de.pfke.squeeze.zlib.io.jnio._

object FileIncludes
  extends FileIncludes

trait FileIncludes {
  implicit class FileFromStringOps (
    filename: String
  ) extends ImplicitOps_fromString.ImplicitOps_fromString_class(filename = filename) {
    /**
      * Return string as file
      */
    def asFile: File = new File(filename)
  }

  implicit class FileFromFileOps (
    file: File
  ) extends IncludeOps_fromPath.IncludeOps_fromPath_class(in = file.toPath)

  implicit class FileFromFilesOps[A <: Traversable[File]] (
    files: A
  ) extends IncludeOps_fromPaths.IncludeOps_fromPaths_class[Traversable[Path]](in = files.map(_.toPath))
}
