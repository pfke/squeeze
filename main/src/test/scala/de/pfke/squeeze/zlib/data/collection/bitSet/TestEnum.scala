package de.pfke.squeeze.zlib.data.collection.bitSet

import de.pfke.squeeze.zlib.data.collection.bitSet

object TestEnum
  extends Enumeration {
  type TestEnum = Value

  val e1: bitSet.TestEnum.Value = Value(0)
  val e2: bitSet.TestEnum.Value = Value(1)
  val e3: bitSet.TestEnum.Value = Value(3)
}
