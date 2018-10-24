package de.pfke.squeeze.zlib.io.compress

import java.nio.file.Path

import de.pfke.squeeze.zlib.io.compress.CompressorType.CompressorType

object CompressorIncludes
  extends CompressorIncludes

trait CompressorIncludes {
  implicit class CompressOnPathOps(
    src: Path
  ) {
    def compress(
      target: Option[Path] = None,
      algorithm: CompressorType = CompressorType.BZIP2,
      deleteSrcFile: Boolean = false
    ): Path = Compressor.compress(src = src, target = target, algorithm = algorithm, deleteSrcFile = deleteSrcFile)

    def unCompress(): Path = unCompress(None)

    def unCompress(
      target: Path
    ): Path = unCompress(Some(target))

    def unCompress(
      target: Option[Path]
    ): Path = Compressor.unCompress(src = src, target = target)
  }
}
