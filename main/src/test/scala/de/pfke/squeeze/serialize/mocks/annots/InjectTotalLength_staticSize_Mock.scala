package de.pfke.squeeze.serialize.mocks.annots

import de.pfke.squeeze.annots.{injectLength, injectTotalLength}

case class InjectTotalLength_staticSize_Mock(
                     _1stParam: Short,
  @injectTotalLength _2ndParam: Short,
                     _3rdParam: Int
)

case class InjectTotalLength_dynamicSize_String_Mock(
                                         _1stParam: Short,
  @injectTotalLength                     _2ndParam: Short,
  @injectLength(fromField = "_4thParam") _3rdParam: Short,
                                         _4thParam: String
)
