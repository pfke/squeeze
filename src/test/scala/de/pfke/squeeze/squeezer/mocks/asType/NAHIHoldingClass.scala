package de.pfke.squeeze.squeezer.mocks.asType

import de.pfke.squeeze.annots.fields.injectFieldType

case class NAHIHoldingClass(
  @injectFieldType(fromField = "_iface") _ifaceType: Int,
                                         _iface: NotAllHaveIface
)
