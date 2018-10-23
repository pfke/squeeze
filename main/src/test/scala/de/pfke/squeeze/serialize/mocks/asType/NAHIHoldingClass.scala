package de.pfke.squeeze.serialize.mocks.asType

import de.pfke.squeeze.annots.injectType

case class NAHIHoldingClass(
  @injectType(fromField = "_iface") _ifaceType: Int,
                                        _iface: NotAllHaveIface
)
