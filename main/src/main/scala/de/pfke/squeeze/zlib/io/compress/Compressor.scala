package de.pfke.squeeze.zlib.io.compress

import java.io.BufferedInputStream
import java.nio.file.Path

import de.pfke.squeeze.zlib.io.compress.CompressorType.CompressorType
import de.pfke.squeeze.zlib.io.jnio._
import de.pfke.squeeze.zlib.io.jnio.ChannelTools
import org.apache.commons.compress.compressors.CompressorStreamFactory

object Compressor {
  // fields
  private val _cTypeToFactString = Map(
    CompressorType.BZIP2 -> CompressorStreamFactory.BZIP2,
    CompressorType.GZIP -> CompressorStreamFactory.GZIP
  )

  /**
    * Uncompress the src file into the dest.
    */
  def compress(
    src: Path,
    target: Option[Path] = None,
    algorithm: CompressorType = CompressorType.BZIP2,
    deleteSrcFile: Boolean = false
  ): Path = doCompress(src, target, algorithm, deleteSrcFile)

  /**
    * Uncompress the src file into the dest.
    *
    * val uncomp = blocking { run { Compressor.unCompress(archive) }} mapToOption
    */
  def unCompress(
    src: Path,
    target: Option[Path] = None
  ): Path = doUnCompress(src, target)

  /**
    * Compress the src file into the dest.
    */
  private def doCompress(
    src: Path,
    target: Option[Path] = None,
    algorithm: CompressorType,
    deleteSrcFile: Boolean = false
  ): Path = {
    val realTarget = target match {
      case Some(x) => x
      case None    => src.getParent.resolve(s"${src.name}.$algorithm")
    }

    if (realTarget.exists)
      realTarget.delete()
    realTarget.createFile()

    ChannelTools.copyStreams(
      src.inputStream,
      new CompressorStreamFactory()
        .createCompressorOutputStream(
          _cTypeToFactString(algorithm),
          realTarget.outputStream
        ))

    if(deleteSrcFile)
      src.delete()
    realTarget
  }

  /**
    * Uncompress the src file into the dest.
    */
  private def doUnCompress(
    src: Path,
    target: Option[Path] = None
  ): Path = {
    target match {
      case Some(x) if x.isADirectory => doUnCompress(src, x.resolve(src.basename))
      case Some(x)                   => doUnCompress(src, x)
      case None                      => doUnCompress(src, src.getParent.resolve(src.basename))
    }
  }

  /**
    * Uncompress the src file into the dest.
    */
  private def doUnCompress(
    src: Path,
    dest: Path
  ): Path = {
    if (dest.exists)
      dest.delete()
    dest.createFile()

    ChannelTools.copyStreams(
      new CompressorStreamFactory().createCompressorInputStream(new BufferedInputStream(src.inputStream)),
      dest.outputStream
    )

    dest
  }
}
