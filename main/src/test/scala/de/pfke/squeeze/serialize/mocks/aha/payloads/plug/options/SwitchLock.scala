package de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.plug.options

object SwitchLock
  extends Enumeration {
  type SwitchLock = Value

  val UNKNOWN                      = Value(0xffff)
  val NORMAL                       = Value(0)
  val NO_WEB                       = Value(1)
  val NO_EXTERNAL                  = Value(2)
  val NO_WEB_NO_EXTERNAL           = Value(3)
  val NO_BUTTON                    = Value(4)
  val NO_BUTTON_NO_WEB             = Value(5)
  val NO_BUTTON_NO_EXTERNAL        = Value(6)
  val NO_WEB_NO_EXTERNAL_NO_BUTTON = Value(7)
}
