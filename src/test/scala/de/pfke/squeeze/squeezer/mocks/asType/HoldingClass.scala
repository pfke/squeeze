package de.pfke.squeeze.squeezer.mocks.asType

import de.pfke.squeeze.annots.fields.injectFieldType

case class HoldingClass(
  @injectFieldType(fromField = "_iface") _ifaceType: Int,
                                         _iface: Iface
)
