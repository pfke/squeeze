package de.pfke.squeeze.zlib.io.compress

import de.pfke.squeeze.zlib.io.compress

object CompressorType
  extends Enumeration {
  type CompressorType = Value

  val GZIP: compress.CompressorType.Value = Value("gz")
  val BZIP2: compress.CompressorType.Value = Value("bz2")
}
