package de.pfke.squeeze.zlib.io.compress

object CompressorType
  extends Enumeration {
  type CompressorType = Value

  val GZIP  = Value("gz")
  val BZIP2 = Value("bz2")
}
