package de.pfke.squeeze.zlib.io.jnio

import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file._

import de.pfke.squeeze.zlib.data.byTypes.ByteBufferIncludes

object IncludeOps_fromPaths
  extends IncludeOps_fromPaths

trait IncludeOps_fromPaths {
  implicit class IncludeOps_fromPaths_class[A <: Traversable[Path]] (
    in: A
  )
    extends ByteBufferIncludes {
    /**
      * Filter the given file collection by the given regex string.
      */
    def filterByName (
      regex: String
    ): Traversable[Path] = in.filter(_.name.matches(regex))

    /**
      * Filter out all directories
      */
    def filterNotDir: Traversable[Path] = in.filterNot(Files.isDirectory(_))

    /**
      * Filter out all directories
      */
    def filterNotLink: Traversable[Path] = in.filterNot(f=> Files.isSymbolicLink(FileSystems.getDefault.getPath(f.toString)))

    /**
      * This method takes the file collection and group all files by its base name.
      * You will get a Map[String, A]
      */
    def groupByBaseName: Map[String, Traversable[Path]] = in.groupBy(f=> f.basename)

    /**
      * Read given files and pass content to the given parse function.
      * If content is undefined at op, the fully content will be returned.
      */
    def parseBy (
      op: PartialFunction[String, Any]
    ) (
      implicit
      charset: Charset = StandardCharsets.UTF_8
    ): Traversable[(Path, Any)] = in.map(f=> f -> (op orElse parseByDefault)(f.read().asString))

    /**
      * This is the default parse function to return the complete file content.
      */
    private def parseByDefault: PartialFunction[String, Any] = { case m@_ => m }
  }
}
