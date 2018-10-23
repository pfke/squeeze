package de.pintono.grind.refl.core

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object EnumValueRefl {
  /**
    * Return enum object of the given enum value
    */
  def typeOf (
    value: Any
  ): Option[ru.Type] = {
    val r1 = RichInstanceMirror(value)
    val r2 = r1.typeSignature
    val r3 = r1.asTpe


    if (!isEnumValue(value))
      return None

    Some(
      RichInstanceMirror(
        value
          .getClass
          .getField("$outer")
          .get(value)
      )
        .typeSignature
        .typeSymbol
        .asType
        .toType
    )
  }

  /**+
    * Returns true, if the given value is of Type Enumeration or Enumeration#Value
    */
  def isEnumValue(
    tpe: ru.Type
  ): Boolean = {
    val t1 = tpe match {
      case t if t <:< ru.typeOf[Enumeration#Value] => true
      case t => false
    }

    val r1 = tpe
      .baseClasses
      .exists(_.fullName == classOf[Enumeration#Value].getCanonicalName)

    val r2 = tpe.baseClasses
    val r3 = classOf[Enumeration#Value].getCanonicalName
    val r4 = tpe.baseClasses.map(_.fullName)
    val r5 = EnumRefl.isEnum(tpe)

    println("22414")
    t1
  }

  /**+
    * Returns true, if the given value is of Tyüe Enumeration or Enumeration#Value
    */
  def isEnumValue[A] (
    value: A
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Boolean = {
    value.isInstanceOf[Enumeration#Value]
  }
}

class EnumValueRefl (
  tpe: ru.Type
) {
  require(EnumValueRefl.isEnumValue(tpe), s"given type '$tpe' is not an enumeration#value")
}