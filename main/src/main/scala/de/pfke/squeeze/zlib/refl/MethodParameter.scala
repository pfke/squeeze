package de.pfke.squeeze.zlib.refl

import scala.reflect.runtime.{universe => ru}
import scala.util.Try

object MethodParameter {
  def getParamTypeAsClass (
    symbol: ru.Symbol,
    typeParams_n_typeArgs: List[(ru.Symbol, ru.Type)]
  ): Class[_] = {
    val typeSignature: ru.Type = symbol.typeSignature

    def getMathingTypeArg: Option[Class[_]] = {
      typeParams_n_typeArgs
        .find(_._1.asType.fullName == typeSignature.typeSymbol.fullName) match {
        case Some(x) => Try(Class.forName(x._2.typeSymbol.asClass.fullName)).toOption
        case None => None
      }
    }

    Try(
      Class
        .forName(
          typeSignature
            .typeSymbol
            .asClass
            .fullName
        ))
      .toOption
      .orElse(getMathingTypeArg) match {
      case Some(x) => x
      case None => throw new IllegalArgumentException(s"could not guess the type of param '${symbol.name}: ${symbol.info}'. Its neither a direct type nor a defined class generic")
    }
  }
}

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
    * Gibt entweder den Typ des Parameters zurück. oder wenn ein typearg den gematchten oder wirft eine ex.
    */
  def clazz (
    typeParams_n_typeArgs: List[(ru.Symbol, ru.Type)] = List.empty
  ): Class[_] = MethodParameter.getParamTypeAsClass(symbol, typeParams_n_typeArgs)

  /**
    * Return param name
    */
  def name: String = symbol.name.toString

  /**
    * Return param type
    */
  def typeSignature: ru.Type = symbol.typeSignature
}
