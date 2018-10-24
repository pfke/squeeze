package de.pfke.squeeze.zlib.io.jnio

import java.nio.file._
import java.nio.file.attribute.FileAttribute

object ImplicitOps_fromString
  extends ImplicitOps_fromString

trait ImplicitOps_fromString {
  implicit class ImplicitOps_fromString_class (
    filename: String
  ) {
    // fields
    private val (name,ext) = extract()

    /**
      * Return string as path.
      */
    def asPath: Path = Paths.get(filename)

    /**
      * Creates a tmp directory with the given name..
      */
    def createTempDirectory (attrs: FileAttribute[_]*): Path = PathOps.createTempDirectory(filename, attrs:_*)

    /**
      * Create a temp file.
      */
    def createTempFile (attrs: FileAttribute[_]*): Path = PathOps.createTempFile(filename, attrs:_*)

    /**
      * Extract filename to name and extension.
      */
    private def extract(): (String, String) = {
      val FileNameRegex = """(.*)\.(\S*)$""".r

      filename match {
        case FileNameRegex(n,e) => (n,e)
        case t                  => (t, null)
      }}
  }
}
