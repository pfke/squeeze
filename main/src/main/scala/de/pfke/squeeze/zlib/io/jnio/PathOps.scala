package de.pfke.squeeze.zlib.io.jnio

import java.nio.file.attribute.FileAttribute
import java.nio.file.{CopyOption, Files, Path, Paths}
import java.util.UUID

import scala.collection.JavaConverters

object PathOps {
  private[jnio] val MAX_SEARCH_DEPTH: Int = Int.MaxValue

  /**
    * Create file and dir if necessary
    */
  def createFile (
    in: Path,
    attrs: FileAttribute[_]*
  ): Path = {
    require(!Files.exists(in), s"the given file already exists: $in")
    require(
      (Files.exists(in.getParent) && Files.isDirectory(in.getParent)) || !Files.exists(in.getParent),
      s"the parent exists an is not a directory: ${in.getParent}"
    )

    Files.createDirectories(in.getParent, attrs:_*)
    Files.createFile(in, attrs:_*)
  }

  /**
    * Creates a tmp directory with the given name..
    */
  def createTempDirectory (
    filename: String,
    attrs: FileAttribute[_]*
  ): Path = {
    val filenameToCreate = if (filename.isEmpty) UUID.randomUUID().toString else filename
    val fileToCreate = Paths.get("/tmp", filenameToCreate)

    Files.createDirectories(fileToCreate.getParent)
    Files.createTempDirectory(fileToCreate.getParent, fileToCreate.filename + "_", attrs:_*)
  }

  /**
    * Creates a tmp directory with the given name..
    */
  def createTempFile (
    filename: String,
    attrs: FileAttribute[_]*
  ): Path = {
    val filenameToCreate = if (filename.isEmpty) UUID.randomUUID().toString else filename
    val fileToCreate = Paths.get("/tmp", filenameToCreate)

    Files.createDirectories(fileToCreate.getParent)
    Files.createTempFile(fileToCreate.getParent, fileToCreate.filename + "_", null, attrs:_*)
  }

  /**
    * Copy source file to dest
    *
    * @param source is the source file
    * @param dest is the destination file
    * @param copyOptions use <code>StandardCopyOption</code>
    */
  def copy (
    source: Path,
    dest: Path,
    copyOptions: CopyOption*
  ): Path = Files.copy(source, dest, copyOptions:_*)

  /**
    * Move source file to dest
    *
    * @param source is the source file
    * @param dest is the destination file
    * @param copyOptions use <code>StandardCopyOption</code>
    */
  def mv (
    source: Path,
    dest: Path,
    copyOptions: CopyOption*
  ): Path = Files.move(source, dest, copyOptions:_*)

  /**
    * Remove given path
    *
    * @param source path to delete
    * @param force set to true whether its a directory
    */
  def rm (
    source: Path,
    force: Boolean = false
  ): Boolean = {
    if (!Files.exists(source)) {
      return true
    }

    if(Files.isDirectory(source) && force) {
      flatFileTree(source)
        .filterNot(_ == source)
        .foreach { i => rm(i, force) }
    }

    Files.deleteIfExists(source)
  }

  /**
    * This method scans the given path/file and return all direct child files/directories as a stream.
    */
  def flatFileTree(in: Path): Stream[Path] = deepFileTree(maxSearchDepth = 1)(startFile = in).filterNot(_ == in)

  /**
    * Recursive method to return all childs and child-childs of the given file.
    */
  def deepFileTree (
    maxSearchDepth: Int = Int.MaxValue,
    currentDepth: Int = 0
  ) (
    startFile: Path
  ): Stream[Path] = {
    val childss = startFile match {
      case t if Files.isDirectory(t) && currentDepth < maxSearchDepth =>
        Stream.apply(JavaConverters.asScalaIterator(Files.list(t).iterator()).toSeq:_*)

      case _ => Stream.empty[Path]
    }

    val fn = deepFileTree(maxSearchDepth = maxSearchDepth, currentDepth = currentDepth + 1) _

    startFile #:: childss.flatMap(fn)
  }
}
