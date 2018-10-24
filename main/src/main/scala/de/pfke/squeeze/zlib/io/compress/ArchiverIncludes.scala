package de.pfke.squeeze.zlib.io.compress

import java.nio.file.Path

import de.pfke.squeeze.zlib.io.compress.ArchiveType.ArchiveType

import scala.collection.immutable.Iterable

object ArchiverIncludes
  extends ArchiverIncludes

trait ArchiverIncludes {
  implicit class ArchiveOnPathOps(
    src: Iterable[Path]
  ) {
    def archive(
      target: Path,
      algorithm: ArchiveType = ArchiveType.TAR,
      rootDir: Option[Path] = None
    ) = Archiver.archive(
      src.toList,
      target,
      algorithm = algorithm,
      rootDir = rootDir
    )
  }

  implicit class UnArchiveOnPathOps(
    src: Path
  ) {
    def unArchive(
      deleteSrcPath: Boolean = false
    ): (Path, List[Path]) = Archiver.unArchive(src, None, deleteSrcPath = deleteSrcPath)

    def unArchive(
      target: Path,
      deleteSrcPath: Boolean
    ): (Path, List[Path]) = Archiver.unArchive(src, Some(target), deleteSrcPath = deleteSrcPath)
  }
}
