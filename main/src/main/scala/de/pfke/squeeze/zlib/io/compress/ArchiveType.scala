package de.pfke.squeeze.zlib.io.compress

import de.pfke.squeeze.zlib.io.compress

object ArchiveType
  extends Enumeration {
  type ArchiveType = Value

  val AR: compress.ArchiveType.Value = Value("ar")
  val CPIO: compress.ArchiveType.Value = Value("cpio")
  val JAR: compress.ArchiveType.Value = Value("jar")
  val TAR: compress.ArchiveType.Value = Value("tar")
  val ZIP: compress.ArchiveType.Value = Value("zip")
}
