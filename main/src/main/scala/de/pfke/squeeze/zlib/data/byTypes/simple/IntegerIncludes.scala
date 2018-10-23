package de.pfke.squeeze.zlib.data.byTypes.simple

object IntegerIncludes
  extends IntegerIncludes

trait IntegerIncludes {
  implicit def toBool(v: Int): Boolean = if(v == 0) false else true
  implicit def toBool(v: Short): Boolean = toBool(v.toInt)

  implicit class IntegerFromInt (
    in: Int
  ) {
    def lift: Option[Int] = Some(in)
  }

  /**
    * Scale a value.
    */
  implicit class IntegerScale (
    v: Double
  ) {
    def scaleBy(count: Int): Double = v / count
  }
}
