package de.pfke.squeeze.zlib.crypto

import java.io.{File, InputStream}
import java.nio.file.{Files, Path}
import java.security.MessageDigest

import de.pfke.squeeze.zlib.crypto
import de.pfke.squeeze.zlib.crypto.ChecksumAlgorithm.ChecksumAlgorithm

object ChecksumAlgorithm
  extends Enumeration {
  type ChecksumAlgorithm = Value

  val MD2: crypto.ChecksumAlgorithm.Value = Value("MD2")
  val MD5: crypto.ChecksumAlgorithm.Value = Value("MD5")
  val SHA_1: crypto.ChecksumAlgorithm.Value = Value("SHA-1")
  val SHA_256: crypto.ChecksumAlgorithm.Value = Value("SHA-256")
  val SHA_384: crypto.ChecksumAlgorithm.Value = Value("SHA-384")
  val SHA_512: crypto.ChecksumAlgorithm.Value = Value("SHA-512")
}

object ChecksumCalculator {
  /**
    * Build check sum from inputstream.
    */
  def fromInputStream (
    algorithm: ChecksumAlgorithm = ChecksumAlgorithm.SHA_256
  ) (
    is: InputStream
  ): Array[Byte] = {
    val md = MessageDigest.getInstance(algorithm.toString)
    val ar = new Array[Byte](1024*1024)
    var len = is.read(ar)

    while(len != -1) {
      md.update(ar, 0, len)
      len = is.read(ar)
    }

    md.digest()
  }

  /**
    * Build check sum from file.
    */
  def fromFile (
    algorithm: ChecksumAlgorithm = ChecksumAlgorithm.SHA_256
  ) (
    file: File
  ): Array[Byte] = fromPath(algorithm = algorithm)(file = file.toPath)

  /**
    * Build check sum from nio file.
    */
  def fromPath (
    algorithm: ChecksumAlgorithm = ChecksumAlgorithm.SHA_256
  ) (
    file: Path
  ): Array[Byte] = fromInputStream(algorithm = algorithm)(Files.newInputStream(file))

  /**
    * Build check sum from string.
    */
  def fromString (
    algorithm: ChecksumAlgorithm = ChecksumAlgorithm.SHA_256
  ) (
    in: String
  ): Array[Byte] = {
    val md = MessageDigest.getInstance(algorithm.toString)

    md.update(in.getBytes)
    md.digest()
  }
}

class ChecksumCalculator(
  algorithm: ChecksumAlgorithm
) {
  /**
    * Build check sum from file.
    */
  def calc (in: File): Array[Byte] = ChecksumCalculator.fromFile(algorithm)(in)

  /**
    * Build check sum from inputstream.
    */
  def calc (in: InputStream): Array[Byte] = ChecksumCalculator.fromInputStream(algorithm)(in)

  /**
    * Build check sum from nio file.
    */
  def calc (in: Path): Array[Byte] = ChecksumCalculator.fromPath(algorithm)(in)

  /**
    * Calc check sum from string
    */
  def calc (in: String): Array[Byte] = ChecksumCalculator.fromString(algorithm)(in)
}

object ChecksumIncludes
  extends ChecksumIncludes

trait ChecksumIncludes {
  implicit class FromChecksumAlgorithm(
    algorithm: ChecksumAlgorithm
  ) {
    // fields
    private val _calculator = new ChecksumCalculator(algorithm)

    /**
      * Build check sum from file.
      */
    def calc (in: File): Array[Byte] = _calculator.calc(in)

    /**
      * Build check sum from inputstream.
      */
    def calc (in: InputStream): Array[Byte] = _calculator.calc(in)

    /**
      * Build check sum from nio file.
      */
    def calc (in: Path): Array[Byte] = _calculator.calc(in)

    /**
      * Calc check sum from string
      */
    def calc (in: String): Array[Byte] = _calculator.calc(in)
  }
}
