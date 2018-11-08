package de.pfke.squeeze.zlib.refl

import de.pfke.squeeze.zlib.data._

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}
import scala.util.Try

trait RichInstanceMirrorIncludes {
  implicit class RichInstanceMirrorFromAny(
    data: Any
  ) {
    // fields
    private lazy val _richInstanceMirror = RichInstanceMirror(data)

    def asTpe: ru.Type = _richInstanceMirror.asTpe

    def toScalaType: ru.Type = PrimitiveRefl.toScalaType(asTpe)
  }
}

object RichInstanceMirror {
  /**
    * Create instance.
    */
  def apply (
    className: String
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): RichInstanceMirror = apply(richRuntimeMirror = RichRuntimeMirror(), className = className)

  /**
    * Create instance.
    */
  def apply(
    clazz: Class[_]
  ): RichInstanceMirror = apply(className = clazz.getCanonicalName)(clazz.getClassLoader)

  /**
    * Create instance.
    */
  def apply(
    classSymbol: ru.ClassSymbol
  ): RichInstanceMirror = apply(className = classSymbol.fullName)

  /**
    * Create instance.
    */
  def apply(
    value: Any
  ): RichInstanceMirror = apply(RichRuntimeMirror(value.getClass), value)

  /**
    * Create instance.
    */
  def apply(
    richRuntimeMirror: RichRuntimeMirror,
    className: String
  ): RichInstanceMirror = apply(richRuntimeMirror = richRuntimeMirror, instanceMirror = richRuntimeMirror.getInstanceMirror(className))


  /**
    * Create instance.
    */
  def apply(
    richRuntimeMirror: RichRuntimeMirror,
    value: Any
  ): RichInstanceMirror = apply(richRuntimeMirror = richRuntimeMirror, instanceMirror = richRuntimeMirror.getInstanceMirror(value))

  /**
    * Create instance.
    */
  def apply(
    richRuntimeMirror: RichRuntimeMirror,
    instanceMirror: ru.InstanceMirror
  ): RichInstanceMirror = new RichInstanceMirror(richRuntimeMirror = richRuntimeMirror, instanceMirror = instanceMirror)
}

class RichInstanceMirror (
  val richRuntimeMirror: RichRuntimeMirror,
  val instanceMirror: ru.InstanceMirror
) {
  // fields
  private lazy val _applyMethodRefls = RichMethodRefl(instanceMirror.symbol, RichMethodRefl.TERMNAME_APPLY)

  /**
    * Getter
    */
  def applyMethodRefls: List[RichMethodRefl] = _applyMethodRefls

  def asType: ru.TypeSymbol = symbol.asType
  def asTpe: ru.Type = asType.toType
  def fullName: String = instanceMirror.symbol.fullName
  def symbol: ru.ClassSymbol = instanceMirror.symbol
  def typeSignature: ru.Type = instanceMirror.symbol.typeSignature

  /**
    * Call the method
    */
  def call(
    methodName: String,
    args: Any*
  ): Option[Any] = {
    getMethodMirror(methodName = methodName) match {
      case Some(t) => Try(t.apply(args:_*)).toOption
      case None => throw new IllegalArgumentException("no apply method found")
    }
  }

  /**
    * Return value of that field
    */
  def getFieldValue[A] (
    fieldName: String
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): A = {
    val methodSymbolOpt = getMethodSymbol(methodName = fieldName)
    val methodSymbol = methodSymbolOpt.getOrException(new IllegalArgumentException(s"no field found w/ name $fieldName"))

    val fieldMirror = instanceMirror.reflectField(methodSymbol)

    methodSymbol.returnType match {
      case t if t =:= typeTag.tpe => fieldMirror.get.asInstanceOf[A]
      case t if t <:< ru.typeOf[List[_]] => fieldMirror.get.asInstanceOf[A]

      case t => throw new IllegalArgumentException(s"could not reflect field value, because expected ${typeTag.tpe}, but is $t")
    }
  }

  /**
    * Return method mirror for the given instance and the given method name.
    */
  def getMethodMirror(
    methodName: String
  ): Option[ru.MethodMirror] = getMethodMirror(methodSymbol = getMethodSymbol(methodName))

  /**
    * Return method mirror for the given instance and the given method name.
    */
  def getMethodMirror(
    methodSymbol: Option[ru.MethodSymbol]
  ): Option[ru.MethodMirror] = {
    methodSymbol match {
      case Some(x) => Some(getMethodMirror(x))
      case None => None
    }
  }

  /**
    * Return method mirror for the given instance and the given method name.
    */
  def getMethodMirror(
    methodSymbol: ru.MethodSymbol
  ): ru.MethodMirror = instanceMirror.reflectMethod(methodSymbol)

  /**
    * Return method mirror for the given instance and the given method name.
    */
  def getMethodSymbol(
    methodName: String
  ): Option[ru.MethodSymbol] = {
    val typeSignature = instanceMirror.symbol.typeSignature
    val methodDef = typeSignature.member(ru.TermName(methodName))

    if (methodDef == ru.NoSymbol) None else Some(methodDef.asMethod)
  }
}
