package de.pfke.squeeze.zlib.refl

import scala.reflect.runtime.{universe => ru}
import scala.util.Try

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
  /**
    * Das wird hier als opt zurückgegeben, das wir auf eine Generic-Klasse treffen könnten und das stehet dann erst zur Laufzeit fest.
    */
  def clazz (
    typeParams_n_typeArgs: List[(ru.Symbol, ru.Type)]
  ): Class[_] = {
    val res = Try(Class.forName(typeSignature.typeSymbol.asClass.fullName)).toOption

    val r1 = typeSignature
    val r2 = typeParams_n_typeArgs.find(_._1.asType.typeSignature =:= r1)
    val r3 = typeParams_n_typeArgs.map(_._1.asType)
    val r4 = typeParams_n_typeArgs.map(_._1.asType.info)

    res.get
  }

  /**
    * Return param name
    */
  def name: String = symbol.name.toString

  /**
    * Return param type
    */
  def typeSignature: ru.Type = symbol.typeSignature
}
