package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.injectType

case class HoldingClass(
  @injectType(fromField = "_iface") _ifaceType: Int,
                                        _iface: Iface
)
