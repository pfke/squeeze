package de.pfke.squeeze.core.io.compress

import java.io.BufferedInputStream
import java.nio.file.{Files, Path}

import de.pfke.squeeze.core._
import de.pfke.squeeze.core.io.compress.CompressorAlgorithm.CompressorAlgorithm
import de.pfke.squeeze.core.io.jnio.{ChannelTools, PathOps}
import org.apache.commons.compress.compressors.CompressorStreamFactory

object Compressor {
  // fields
  private val _cTypeToFactString = Map(
    CompressorAlgorithm.BZIP2 -> CompressorStreamFactory.BZIP2,
    CompressorAlgorithm.GZIP -> CompressorStreamFactory.GZIP
  )

  /**
    * Uncompress the src file into the dest.
    */
  def compress(
    src: Path,
    dest: Option[Path] = None,
    algorithm: CompressorAlgorithm = CompressorAlgorithm.BZIP2,
    deleteSrcFile: Boolean = false
  ): Path = doCompress(src = src, dest = dest, algorithm = algorithm, deleteSrcFile = deleteSrcFile)

  /**
    * Uncompress the src file into the dest.
    *
    * val uncomp = blocking { run { Compressor.unCompress(archive) }} mapToOption
    */
  def unCompress(
    src: Path,
    dest: Option[Path] = None
  ): Path = {
    dest match {
      case Some(x) if Files.isDirectory(x) => doUnCompress(src, x.resolve(src.basename))
      case Some(x)                         => doUnCompress(src, x)
      case None                            => doUnCompress(src, src.getParent.resolve(src.basename))
    }
  }

  /**
    * Compress the src file into the dest.
    */
  private def doCompress(
    src: Path,
    dest: Option[Path] = None,
    algorithm: CompressorAlgorithm,
    deleteSrcFile: Boolean = false
  ): Path = {
    val realTarget = dest match {
      case Some(x) => x
      case None    => src.getParent.resolve(s"${src.toString}.$algorithm")
    }

    if (Files.exists(realTarget)) {
      PathOps.rm(realTarget)
    }
    realTarget.createFile()

    ChannelTools.copyStreams(
      src.inputStream,
      new CompressorStreamFactory()
        .createCompressorOutputStream(
          _cTypeToFactString(algorithm),
          realTarget.outputStream
        ))

    if(deleteSrcFile) {
      PathOps.rm(src)
    }
    realTarget
  }

  /**
    * Uncompress the src file into the dest.
    */
  private def doUnCompress(
    src: Path,
    dest: Path
  ): Path = {
    if (Files.exists(dest)) {
      PathOps.rm(dest)
    }
    dest.createFile()

    ChannelTools.copyStreams(
      new CompressorStreamFactory().createCompressorInputStream(new BufferedInputStream(src.inputStream)),
      dest.outputStream
    )

    dest
  }
}
