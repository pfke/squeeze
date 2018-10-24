package de.pfke.squeeze.zlib.io.compress

object ArchiveType
  extends Enumeration {
  type ArchiveType = Value

  val AR   = Value("ar")
  val CPIO = Value("cpio")
  val JAR  = Value("jar")
  val TAR  = Value("tar")
  val ZIP  = Value("zip")
}
