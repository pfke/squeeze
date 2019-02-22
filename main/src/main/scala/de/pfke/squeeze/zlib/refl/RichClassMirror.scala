package de.pfke.squeeze.zlib.refl

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object RichClassMirror {
  /**
    * Create instance.
    */
  def apply[A] () (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): RichClassMirror = apply(classSymbol = typeTag.mirror.classSymbol(classTag.runtimeClass))

  /**
    * Create instance.
    */
  def apply (
    className: String
  ): RichClassMirror = apply(richRuntimeMirror = RichRuntimeMirror(), className = className)

  /**
    * Create instance.
    */
  def apply (
    clazz: Class[_]
  ): RichClassMirror = apply(richRuntimeMirror = RichRuntimeMirror(clazz), className = clazz.getName)

  /**
    * Create instance.
    */
  def apply (
    classSymbol: ru.ClassSymbol
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): RichClassMirror = apply(richRuntimeMirror = RichRuntimeMirror(), classSymbol = classSymbol)

  /**
    * Create instance.
    */
  def apply (
    value: Any
  ): RichClassMirror = apply(RichRuntimeMirror(value.getClass), value.getClass.getName)

  /**
    * Create instance.
    */
  def apply (
    richRuntimeMirror: RichRuntimeMirror,
    classSymbol: ru.ClassSymbol
  ): RichClassMirror = new RichClassMirror(richRuntimeMirror = richRuntimeMirror, classMirror = richRuntimeMirror.getClassMirror(classSymbol))

  /**
    * Create instance.
    */
  def apply (
    richRuntimeMirror: RichRuntimeMirror,
    className: String
  ): RichClassMirror = new RichClassMirror(richRuntimeMirror = richRuntimeMirror, classMirror = richRuntimeMirror.getClassMirror(richRuntimeMirror.getClassSymbol(className)))
}

class RichClassMirror (
  val richRuntimeMirror: RichRuntimeMirror,
  val classMirror: ru.ClassMirror
) {
  // fields
  private lazy val _ctorMethodRefls = RichMethodRefl(classMirror.symbol, RichMethodRefl.TERMNAME_CTOR)

  // getter
  def ctorMethodRefls: List[RichMethodRefl] = _ctorMethodRefls

  def annotations: List[ru.Annotation] = classMirror.symbol.annotations
}
