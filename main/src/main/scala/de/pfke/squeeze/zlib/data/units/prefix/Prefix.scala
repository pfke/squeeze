package de.pfke.squeeze.zlib.data.units.prefix

object Prefix {
  case object No extends Prefix(2, 0, "", "")
}

abstract class Prefix(
  base: Int,
  exp: Int,
  symbol: String,
  name: String
  ) {
  override def toString: String = symbol
  def toValue: Int = math.pow(base.toDouble, exp.toDouble).toInt
}
