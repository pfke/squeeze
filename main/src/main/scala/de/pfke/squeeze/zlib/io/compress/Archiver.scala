package de.pfke.squeeze.zlib.io.compress

import java.io.BufferedInputStream
import java.nio.file.{Files, Path}

import de.pfke.squeeze.zlib.io.compress.ArchiveType.ArchiveType
import de.pfke.squeeze.zlib.io.jnio._
import de.pfke.squeeze.zlib.io.jnio.ChannelTools
import org.apache.commons.compress.archivers.ArchiveStreamFactory

import scala.collection.mutable.ArrayBuffer

object Archiver {
  /**
    * Archive the src files into the dest.
    *
    * @param src list of source files
    * @param target this is the target file base name (will be enriched with the correct extension.
    * @param algorithm is the algo to use
    * @param rootDir this is the absolute path and all src files should be archived relative to this
    * @return a path to the archived file
    */
  def archive(
    src: List[Path],
    target: Path,
    algorithm: ArchiveType,
    rootDir: Option[Path] = None
  ): Path = doArchive(src, target, algorithm, rootDir)

  /**
    * Unarchive the src file into the dest.
    */
  def unArchive(
    src: Path,
    target: Option[Path] = None,
    deleteSrcPath: Boolean = false
  ): (Path, List[Path]) = {
    val res = doUnArchive(src, target)

    if(deleteSrcPath)
      src.delete()

    res
  }

  /**
    * Archive the src files into the dest.
    */
  private def doArchive(
    src: List[Path],
    target: Path,
    algorithm: ArchiveType,
    rootDir: Option[Path]
  ): Path = {
    val realTarget = target.getParent.resolve(s"${target.basename}.$algorithm")

    if (realTarget.exists)
      realTarget.delete()
    realTarget.createFile()

    val co = new ArchiveStreamFactory().createArchiveOutputStream(algorithm.toString, realTarget.outputStream)

    (src
      .flatMap(_.deepFileTree) ++ src) // get dir content AND src if no dirs
      .filter(_.isAFile)
      .foreach { f =>
        val relativeName = rootDir match {
          case Some(x) => x.relativize(f).toString
          case None    => f.toString.substring(1) // filter the first "/"
        }

        val entry = co.createArchiveEntry(f.toFile, relativeName)

        co.putArchiveEntry(entry)
        ChannelTools.copyStreams(Files.newInputStream(f), co, closeOutput = false)
        co.closeArchiveEntry()
      }
    co.close()

    realTarget
  }

  /**
    * Unarchive the src file into the dest.
    */
  private def doUnArchive(
    src: Path,
    target: Option[Path] = None
  ): (Path, List[Path]) = {
    target match {
      case Some(x) if x.exists && !x.isADirectory => throw new IllegalArgumentException(s"$x is a file, can not unarchive to a file")
      case Some(x)                                => doUnArchive(src, x)
      case None                                   => doUnArchive(src, src.getParent.resolve(src.basename))
    }
  }

  /**
    * Unarchive the src file into the dest.
    */
  private def doUnArchive(
    src: Path,
    target: Path
  ): (Path, List[Path]) = {
    if (!target.exists)
      target.createDirectory()

    var files = new ArrayBuffer[Path]()
    val in = new ArchiveStreamFactory().createArchiveInputStream(new BufferedInputStream(Files.newInputStream(src)))

    var entry = in.getNextEntry
    while (entry != null) {
      val file = target.resolve(entry.getName)

      if(entry.isDirectory) {
        file.toFile.mkdirs()
      } else if (file.exists) {
      } else {
        file.getParent.toFile.mkdirs()
        file.createFile()
        ChannelTools.copyStreams(in, Files.newOutputStream(file), bytesToCopy = entry.getSize, closeInput = false)

        files += file
      }

      entry = in.getNextEntry
    }

    (target, files.toList)
  }
}
