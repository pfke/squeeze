package de.pintono.grind.refl.core

import scala.reflect.runtime.{universe => ru}

trait PrimitiveReflIncludes {
  implicit class PrimitiveReflFrom_ruType(
    data: ru.Type
  ) {
    def toScalaType = PrimitiveRefl.toScalaType(data)
  }
}

object PrimitiveRefl {
  /**
    * Return default values for primitive types
    * @param tpe type to get values for
    * @return
    */
  def defaultValue(
    tpe: ru.Type
  ): Any = {
    toScalaType(tpe) match {
      case t if t =:= ru.typeOf[Boolean] => false
      case t if t =:= ru.typeOf[Byte] => 0.toByte
      case t if t =:= ru.typeOf[Char] => 0.toChar
      case t if t =:= ru.typeOf[Double] => 0.toDouble
      case t if t =:= ru.typeOf[Float] => 0.toFloat
      case t if t =:= ru.typeOf[Int] => 0
      case t if t =:= ru.typeOf[Long] => 0.toLong
      case t if t =:= ru.typeOf[Short] => 0.toShort

      case t => throw new IllegalArgumentException(s"not a primitive type ($t)")
    }
  }

  /**
    * Return type of the given primitive value
    */
  def getType(
    value: Any
  ): Option[ru.Type] = {
    value match {
      case t: Boolean => Some(ru.typeOf[Boolean])
      case t: Byte => Some(ru.typeOf[Byte])
      case t: Char => Some(ru.typeOf[Char])
      case t: Double => Some(ru.typeOf[Double])
      case t: Float => Some(ru.typeOf[Float])
      case t: Int => Some(ru.typeOf[Int])
      case t: Long => Some(ru.typeOf[Long])
      case t: Short => Some(ru.typeOf[Short])

      case _ => None
    }
  }

  /**
    * Returns true if the given type is a primitive one
    */
  def isPrimitive(
    tpe: ru.Type
  ): Boolean = {
    toScalaType(tpe) match {
      case t if t =:= ru.typeOf[Boolean] => true
      case t if t =:= ru.typeOf[Byte] => true
      case t if t =:= ru.typeOf[Char] => true
      case t if t =:= ru.typeOf[Double] => true
      case t if t =:= ru.typeOf[Float] => true
      case t if t =:= ru.typeOf[Int] => true
      case t if t =:= ru.typeOf[Long] => true
      case t if t =:= ru.typeOf[Short] => true

      case t => false
    }
  }

  /**
    * Returns true if the given value is a primitive one
    */
  def isPrimitive(
    value: Any
  ): Boolean = getType(value).nonEmpty

  /**
   * Take a long as input and promote to given type.
   */
  def promoteTo[A](
    data: Long
  )(
    implicit
    typeTag: ru.TypeTag[A]
  ): A = promoteTo(tpe = typeTag.tpe, data = data).asInstanceOf[A]

  /**
   * Take a long as input and promote to given type.
   *
   * @param tpe this is the target type
   * @param data is the data to convert
   * @return converted value
   */
  def promoteTo(tpe: ru.Type, data: Long): Any = {
    toScalaType(tpe) match {
      case t if t =:= ru.typeOf[Char]    => data.toChar
      case t if t =:= ru.typeOf[Boolean] => data != 0
      case t if t =:= ru.typeOf[Byte]    => data.toByte
      case t if t =:= ru.typeOf[Double]  => data.toDouble
      case t if t =:= ru.typeOf[Float]   => data.toFloat
      case t if t =:= ru.typeOf[Int]     => data.toInt
      case t if t =:= ru.typeOf[Long]    => data
      case t if t =:= ru.typeOf[Short]   => data.toShort

      case t => throw new IllegalArgumentException(s"$t is not a primitive type")
    }
  }

  /**
    * Convert java types to scala types
    */
  def toScalaType(
    tpe: ru.Type
  ): ru.Type = {
    tpe match {
      case t if t =:= ru.typeOf[Boolean] => t
      case t if t =:= ru.typeOf[java.lang.Boolean] => ru.typeOf[Boolean]

      case t if t =:= ru.typeOf[Char] => t
      case t if t =:= ru.typeOf[java.lang.Character] => ru.typeOf[Char]

      case t if t =:= ru.typeOf[Byte] => t
      case t if t =:= ru.typeOf[java.lang.Byte] => ru.typeOf[Byte]

      case t if t =:= ru.typeOf[Double] => t
      case t if t =:= ru.typeOf[java.lang.Double] => ru.typeOf[Double]

      case t if t =:= ru.typeOf[Float] => t
      case t if t =:= ru.typeOf[java.lang.Float] => ru.typeOf[Float]

      case t if t =:= ru.typeOf[Int] => t
      case t if t =:= ru.typeOf[java.lang.Integer] => ru.typeOf[Int]

      case t if t =:= ru.typeOf[Long] => t
      case t if t =:= ru.typeOf[java.lang.Long] => ru.typeOf[Long]

      case t if t =:= ru.typeOf[Short] => t
      case t if t =:= ru.typeOf[java.lang.Short] => ru.typeOf[Short]

      case t if t =:= ru.typeOf[String] => t
      case t if t =:= ru.typeOf[java.lang.String] => ru.typeOf[String]

      case t => t
    }
  }
}
