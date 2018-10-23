package de.pintono.grind.refl.core

import scala.reflect.runtime.{universe => ru}

/**
  * Method parameter (got by reflection).
  *
  * @param index calling position
  * @param symbol parmater symbol
  */
case class MethodParameter (
  index: Int,
  symbol: ru.Symbol,
  defaultValue: Option[Any] = None
) {
  lazy val clazz = Class.forName(typeSignature.typeSymbol.asClass.fullName)

  /**
    * Return param name
    */
  def name = symbol.name.toString

  /**
    * Return param type
    */
  def typeSignature = symbol.typeSignature
}
