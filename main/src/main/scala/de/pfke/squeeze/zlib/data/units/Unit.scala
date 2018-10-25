package de.pfke.squeeze.zlib.data.units

import de.pfke.squeeze.zlib.data.units.prefix.Prefix

abstract class Unit(
  prefix: Prefix,
  symbol: String
) {
  override def toString = s"$prefix$symbol"
}
