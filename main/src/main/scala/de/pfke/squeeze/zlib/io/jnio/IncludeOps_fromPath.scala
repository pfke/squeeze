package de.pfke.squeeze.zlib.io.jnio

import java.io.{ByteArrayOutputStream, InputStream, OutputStream}
import java.nio.ByteBuffer
import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file._
import java.nio.file.attribute.{FileAttribute, PosixFilePermissions}

import de.pfke.squeeze.zlib.crypto.{ChecksumAlgorithm, ChecksumCalculator}
import de.pfke.squeeze.zlib.crypto.ChecksumAlgorithm.ChecksumAlgorithm
import de.pfke.squeeze.zlib.data.byTypes.ByteBufferIncludes
import de.pfke.squeeze.zlib.data.byTypes.simple.StringIncludes

object IncludeOps_fromPath
  extends IncludeOps_fromPath

trait IncludeOps_fromPath {
  implicit class IncludeOps_fromPath_class(
    in: Path
  ) {
    /**
      * Return the filename w/o file extension + w/o path
      */
    def basename: String = extract(filename)._1

    /**
      * Return the file extension.
      */
    def extension: String = extract(filename)._2

    /**
      * Return the filename w/ file extension
      */
    def filename: String = in.getFileName.toString

    /**
      * Return the filename w/ file extension
      */
    def name: String = in.toString

    /**
      * Calc checksum from file
      */
    def checksum (
      algo: ChecksumAlgorithm = ChecksumAlgorithm.SHA_512
    ): Array[Byte] = new ChecksumCalculator(algo).calc(in)

    /**
      * Create dir from path.
      */
    def createDirectory (attrs: FileAttribute[_]*): Path = Files.createDirectory(in, attrs:_*)

    /**
      * Create file from path.
      */
    def createFile (attrs: FileAttribute[_]*): Path = PathOps.createFile(in, attrs:_*)

    /**
      * Copy file.
      */
    def copy (
      dest: Path,
      copyOptions: CopyOption*
    ): Path = Files.copy(in, dest, copyOptions:_*)

    /**
      * Delete file.
      */
    def delete (): Boolean = PathOps.rm(in)

    /**
      * Check if file exists.
      */
    def exists: Boolean = Files.exists(in)

    /**
      * Return true if the given file is a directory.
      */
    def isADirectory: Boolean = Files.isDirectory(in)

    /**
      * Return true if the given file is a file.
      */
    def isAFile: Boolean = Files.isRegularFile(in)

    /**
      * Return true if file is empty.
      */
    def isEmpty: Boolean = Files.size(in) == 0

    /**
      * This method scans the given path/file and return all child-child files/directories as a stream.
      */
    def deepFileTree: Stream[Path] = maxDeepFileTree(maxSearchDepth = PathOps.MAX_SEARCH_DEPTH)
    def maxDeepFileTree (maxSearchDepth: Int): Stream[Path] = PathOps.deepFileTree(maxSearchDepth = maxSearchDepth)(startFile = in)

    /**
      * This method scans the given path/file and return all direct child files/directories as a stream.
      */
    def flatFileTree: Stream[Path] = maxDeepFileTree(maxSearchDepth = 1).filterNot(_ == in)

    /**
      * Move file.
      *
      * @param dest dest path
      * @param options e.g. 'StandardCopyOption.REPLACE_EXISTING'
      * @return
      */
    def move (
      dest: Path,
      options: CopyOption*
    ): Path = PathOps.mv(in, dest, options:_*)

    /**
      * Return file size as byte length.
      */
    def size: Long = {
      if(isADirectory) {
        flatFileTree
          .filterNot(_ == in)
          .foldLeft(0l)((i,f) => i + new IncludeOps_fromPath_class(f).size)
      } else {
        Files.size(in)
      }
    }

    def inputStream: InputStream = Files.newInputStream(in)
    def outputStream: OutputStream = Files.newOutputStream(in)

    /**
      * Read the file binary.
      */
    def read (): ByteBuffer = {
      if (!exists)
        ByteBuffer.allocateDirect(0)
      else {
        ByteBuffer
          .wrap(
            ChannelTools
              .copyStreams(
                Files.newInputStream(in),
                new ByteArrayOutputStream(size.toInt),
                closeOutput = false
              )
              .toByteArray
          )
      }
    }

    /**
      * Write something to file.
      */
    def write (
      is: InputStream
    ): Path = {
      if (!Files.exists(in)) {
        val perms = PosixFilePermissions.fromString("rwxr-x---")
        val attr = PosixFilePermissions.asFileAttribute(perms)

        Files.createDirectories(in.getParent, attr)
        Files.createFile(in, attr)
      }

      ChannelTools.copyStreams(is, outputStream)
      in
    }

    /**
      * Write something to file.
      */
    def write (
      in: ByteBuffer
    ): Path = write(ByteBufferIncludes.ByteBufferToInputStream(in))

    /**
      * Write something to file.
      */
    def write (
      in: String
    ) (
      implicit
      charset: Charset = StandardCharsets.UTF_8
    ): Path = write(new StringIncludes.StringToByteBuffer(in).asByteBuffer)

    /**
      * Write something to file.
      */
    def appendWrite (
      is: InputStream
    ): Path = {
      Files.delete(in)
      if (!Files.exists(in)) {
        val perms = PosixFilePermissions.fromString("rwxr-x---")
        val attr = PosixFilePermissions.asFileAttribute(perms)

        Files.createDirectories(in.getParent, attr)
        Files.createFile(in, attr)
      }

      ChannelTools.copyStreams(is, outputStream)
      in
    }

    /**
      * Write something to file.
      */
    def appendWrite (
      in: ByteBuffer
    ): Path = appendWrite(ByteBufferIncludes.ByteBufferToInputStream(in))

    /**
      * Write something to file.
      */
    def appendWrite (
      in: String
    ) (
      implicit
      charset: Charset = StandardCharsets.UTF_8
    ): Path = appendWrite(new StringIncludes.StringToByteBuffer(in).asByteBuffer)

    /**
      * Extract filename to name and extension.
      */
    private def extract (
      str: String
    ): (String, String) = {
      // fields
      val filenameRegex = """(.*)\.(\S*)$""".r

      str match {
        case filenameRegex(n,e) => (n, e)
        case t                  => (t, "")
      }}
  }
}
