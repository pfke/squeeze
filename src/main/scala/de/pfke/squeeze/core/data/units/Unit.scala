package de.pfke.squeeze.core.data.units

import de.pfke.squeeze.core.data.units.prefix.Prefix

abstract class Unit(
  prefix: Prefix,
  symbol: String
) {
  override def toString = s"$prefix$symbol"
}
