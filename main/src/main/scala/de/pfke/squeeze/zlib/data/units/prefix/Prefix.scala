package de.pfke.squeeze.zlib.data.units.prefix

object Prefix {
  case object No extends Prefix(2, 0, "", "")
}

abstract class Prefix(
  base: Double,
  exp: Double,
  symbol: String,
  name: String
  ) {
  override def toString: String = symbol
  def toValue: Double = math.pow(base, exp)
}
