package de.pfke.squeeze.zlib

trait Version
  extends Ordered[Version]

object Version {
  def apply(in: String): Version = {
    if (PatchLevelVersion.isVersion(in))
      PatchLevelVersion.parse(in)
    else
      throw new IllegalArgumentException(s"given string '$in' is not a valid version")
  }
}

trait HasMajorMinor {
  def major: Int
  def minor: Int

  def compareMajorMinor(that: HasMajorMinor): Int = if(major != that.major) major - that.major else minor - that.minor
}

object PatchLevelVersion {
  // fields
  private val fullVersionWithPoint_r = """(\d+)\.(\d+).(\d+)""".r
  private val fullVersion_r = """(\d+)\.(\d+)-(\d+)""".r
  private val majorVersion_r = """(\d+)""".r
  private val minorVersion_r = """\.(\d+)-(\d+)""".r

  /**
    * Try to convert from string to int and return as Option[Int]:
    *
    * toInt("102").getOrElse(23)
    */
  private implicit def asInt(value: String): Int = {
    try {
      Integer.parseInt(value, 10)
    } catch {
      case e: NumberFormatException => 0
    }
  }

  /**
    * Convert from string to version.
    */
  def parse ( in: String ): PatchLevelVersion = in match {
    case fullVersionWithPoint_r(_1, _2, _3) => PatchLevelVersion(major = _1, minor = _2, patchLevel = _3)
    case fullVersion_r(_1, _2, _3)          => PatchLevelVersion(major = _1, minor = _2, patchLevel = _3)
    case majorVersion_r(_1)                 => PatchLevelVersion(major = _1)
    case minorVersion_r(_1, _2)             => PatchLevelVersion(minor = _1, patchLevel = _2)

    case _                                  => throw new IllegalArgumentException(s"given string '$in' is not a valid patch level version")
  }

  /**
    * Returns true, if the given string is a patch level version
    */
  def isVersion(in: String): Boolean = in match {
    case fullVersionWithPoint_r(_, _, _) => true
    case fullVersion_r(_, _, _)          => true
    case majorVersion_r(_)               => true
    case minorVersion_r(_, _)            => true

    case _ => false
  }
}

case class PatchLevelVersion(
  major: Int = 0,
  minor: Int = 0,
  patchLevel: Int = 0
)
  extends Version
    with HasMajorMinor
    with Ordered[Version]
{
  /**
    * Result of comparing `this` with operand `that`.
    *
    * Implement this method to determine how instances of A will be sorted.
    */
  override def compare(that: Version): Int = {
    that match {
      case v: PatchLevelVersion => compareMajorMinor(v) + patchLevel - v.patchLevel

      case _ => 0
    }
  }

  override def toString = s"$major.$minor-$patchLevel"
}
