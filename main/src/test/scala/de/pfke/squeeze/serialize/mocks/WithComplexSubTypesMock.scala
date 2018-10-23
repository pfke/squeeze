package de.pfke.squeeze.serialize.mocks

case class WithComplexSubTypesMock(
  _1stParam: Byte,
  _2ndParam: SubWithComplexSubTypesMock,
  _3rdParam: Double
)
case class SubWithComplexSubTypesMock(
  _1stParam: Boolean,
  _2ndParam: Byte,
  _3rdParam: SubSubWithComplexSubTypesMock,
  _4thParam: Double,
  _5thParam: Float
)
case class SubSubWithComplexSubTypesMock(
  _1stParam: Int,
  _2ndParam: Long,
  _3rdParam: Short
)
