package de.pfke.squeeze.zlib.refl

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object EnumRefl {
  /**
    * Check if the given value is part of the enumeration.
    */
  def getChildren(
    tpe: ru.Type
  ): List[ru.Symbol] = {
    tpe
      .asInstanceOf[ru.TypeRef]
      .pre
      .members
      .view
      .filter(_.isTerm)
      .filterNot(_.isMethod)
      .filterNot(_.isModule)
      .filterNot(_.isClass)
      .toList
  }

  /**
    * Check if the given value is part of the enumeration.
    */
  def getChildrenNames[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): List[String] = getChildrenNames(tpe = typeTag.tpe)

  /**
    * Check if the given value is part of the enumeration.
    */
  def getChildrenNames(
    tpe: ru.Type
  ): List[String] = {
    getChildren(tpe)
      .map(_.fullName)
      .map(_.trim)
      .sorted
  }

  /**
    * Check if the given value is part of the enumeration.
    */
  def hasDuplicateIds(
    tpe: ru.Type
  ): Option[String] = {
    try {
      getChildren(tpe)
        .headOption match {
        case Some(x) => reflectStringToEnumValue(getClass.getClassLoader, tpe, x.name.toString)
        case None =>
      }
      None
    } catch {
      case e: AssertionError => Some(e.getMessage)
    }
  }

  ///**
  //  * Check if the given value is part of the enumeration.
  //  */
  //def isChild(
  //  tpe: ru.Type,
  //  valueToCheck: ru.Type
  //): Boolean = {
  //  val w1 = tpe.asInstanceOf[ru.TypeRef]
  //  val w2 = w1.pre
  //  val w3 = w2.members
  //  val w4 = w3.view
  //  val w5 = w4.filter(_.isTerm)
  //  val w6 = w5.filterNot(_.isMethod)
  //  val w7 = w6.filterNot(_.isModule)
  //  val w8 = w7.filterNot(_.isClass)
  //  val w9 = w8.map(_.typeSignature)
  //
  //  val t2 = w9.map(_.typeSymbol)
  //  val t3 = w9.find(_.typeSymbol == valueToCheck.typeSymbol)
  //
  //  t3.nonEmpty
  //}

  ///**
  //  * Returns true if the given squeezle is an enum.
  //  *
  //  * @param tpe type info
  //  * @return true or false
  //  */
  //def isEnum(
  //  tpe: ru.Type
  //) = {
  //  tpe
  //    .baseClasses
  //    .exists(_.fullName == classOf[Enumeration#Value].getCanonicalName)
  //}

  /**
    * Trying to reflect enum from int.
    */
  def reflectIntToEnumValue(
    classLoader: ClassLoader,
    enumType: ru.Type,
    valueToPump: Any
  ): Any = {
    val mirror = ru.runtimeMirror(classLoader)

    val realEnumType = enumType
      .asInstanceOf[ru.TypeRef]
      .pre

    val mApply = realEnumType
      .members
      .filter(_.isMethod)
      .find(_.name.decodedName.toString == "apply") match {
      case Some(xx) => xx
      case None     => throw new IllegalStateException(s"no apply method for enum type $realEnumType found")
    }


    val fullName = realEnumType
      .typeSymbol
      .fullName

    val clazz = mirror.classLoader.loadClass(fullName)
    val clazzSymbol = mirror.classSymbol(clazz)
    val module = clazzSymbol.companion.asModule
    val im = mirror.reflect(mirror.reflectModule(module).instance)

    def methodMirror(symbol: ru.Symbol) = im.reflectMethod(symbol.asMethod)

    methodMirror(mApply)
      .apply(valueToPump)
  }

  /**
    * Trying to reflect enum from string.
    */
  def reflectStringToEnumValue(
    classLoader: ClassLoader,
    enumType: ru.Type,
    valueToPump: String
  ): Any = {
    val mirror = ru.runtimeMirror(classLoader)

    val realEnumType = enumType
      .asInstanceOf[ru.TypeRef]
      .pre

    val mApply = realEnumType
      .members
      .filter(_.isMethod)
      .collect {
        case t if t.fullName.trim == valueToPump.trim => t
        case t if t.name.decodedName.toString.trim == valueToPump.trim => t
      }
      .headOption match {
      case Some(xx) => xx
      case None     => throw new IllegalStateException(s"no $valueToPump method for enum type $realEnumType found (given string is not a member of this enum)")
    }

    val fullName = realEnumType
      .typeSymbol
      .fullName

    val clazz = mirror.classLoader.loadClass(fullName)
    val clazzSymbol = mirror.classSymbol(clazz)
    val module = clazzSymbol.companion.asModule
    val im = mirror.reflect(mirror.reflectModule(module).instance)

    def methodMirror(symbol: ru.Symbol) = im.reflectMethod(symbol.asMethod)

    methodMirror(mApply)
      .apply()
  }

  /**
    * Returns the int value (method id) of this enum.
    *
    * @param data enum
    * @param tpe type info
    * @param classTag implicit classtag (used for reflection)
    * @tparam A type parameter
    * @return value as int
    */
  def reflectEnumValue[A](
    data: A,
    tpe: ru.Type
  )(
    implicit
    classTag: ClassTag[A]
  ): Option[Int] = {
    /**
      * Return all members of the given object.
      *
      * @param data is the object to discover
      * @param classTag implicit classtag (used for reflection)
      * @tparam A type parameter
      * @return class members
      */
    def reflectedMembersFromObject[A](
      data: A
    )(implicit classTag: ClassTag[A]): (ru.InstanceMirror, ru.MemberScope) = {
      val typeMirror = ru.runtimeMirror(data.getClass.getClassLoader)
      val instanceMirror = typeMirror.reflect(data)
      val members = instanceMirror.symbol.typeSignature.members

      (instanceMirror, members)
    }

    val (instanceMirror, members) = reflectedMembersFromObject(data)
    def methodMirror(symbol: ru.Symbol) = instanceMirror.reflectMethod(symbol.asMethod)

    members
      .filter(_.isMethod)
      .find(_.name.decodedName.toString == "id") match {
      case Some(x) => Some(methodMirror(x).apply().asInstanceOf[Int])
      case None    => None
    }
  }

  /**
   * Check if the given value is part of the enumeration.
   */
  def isChild(
    childTpe: ru.Type,
    enumTpe: ru.Type
    ): Boolean = {
    if (!EnumValueRefl.isEnumValue(childTpe))
      return false
    if (!isEnum(enumTpe))
      return false

    val s1 = childTpe
    val s2 = s1.widen

    val q1 = enumTpe
    val q2 = q1.widen


//    val t1 = value.getClass
//    val t2 = t1.getField("$outer")
//    val t3 = t2.get(value)
//    val t4 = RichInstanceMirror(t3).typeSignature
//    val t5 = t3.getClass.getField("MODULE$")
//    val t6 = t5.get()
//    val t7 = t6.asInstanceOf[Enumeration]
//    val t8 = RichInstanceMirror(t6).typeSignature
//    val t9 = t6.getClass.getCanonicalName

    val c0 = childTpe.members
    val c1 = childTpe.member(ru.TermName("scala$Enumeration$$outerEnum"))
//    val c2 = c1.asTerm.accessed.asTerm
    val c3 = childTpe.typeSymbol.asClass
//    val c5 = RichInstanceMirror(c3.getClass)
    val c4 = RichRuntimeMirror()
//    val c5 = c4.runtimeMirror.runtimeClass(childTpe)



    val w1 = enumTpe.asInstanceOf[ru.TypeRef]
    val w2 = w1.pre
    val w3 = w2.members
    val w4 = w3.view
    val w5 = w4.filter(_.isTerm)
    val w6 = w5.filterNot(_.isMethod)
    val w7 = w6.filterNot(_.isModule)
    val w8 = w7.filterNot(_.isClass)
    val w9 = w8.map(_.typeSignature)
    val w10 = w9.toList

    val t2 = w10.map(_.typeSymbol)
    val t3 = w10.find(_.typeSymbol == childTpe.typeSymbol)
    val t4 = childTpe.typeSymbol

    t3.nonEmpty
  }

  /**+
    * Returns true, if the given value is of Tyüe Enumeration or Enumeration#Value
    */
  def isEnum(
    tpe: ru.Type
  ): Boolean = {
    tpe match {
      case t: ru.TypeRef if t.pre <:< ru.typeOf[Enumeration] => true
      case _ => false
    }



  }
}

class EnumRefl(
  tpe: ru.Type
) {
  require(EnumRefl.isEnum(tpe), s"given type '$tpe' is not an enumeration")
}