package de.pfke.squeeze.zlib.refl.entityRefl

import de.pfke.squeeze.zlib.refl.{GeneralRefl, MethodParameter, PrimitiveRefl, RichClassMirror, RichMethodRefl, RichRuntimeMirror}

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object ClassRefl {
  /**
    * Create instance
    */
  def apply (
    clazz: Class[_]
  ): ClassRefl = apply(clazz.getCanonicalName)(clazz.getClassLoader)

  /**
    * Create instance
    */
  def apply[A] () (
    implicit
    classTag: ClassTag[A]
  ): ClassRefl = apply(classTag.runtimeClass)

  /**
    * Create instance
    */
  def apply (
    className: String
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): ClassRefl = new ClassRefl(classSymbol = RichRuntimeMirror().getClassSymbol(className = className))

  /**
    * Test if the given type is a case class.
    */
  def isClass[T] (
    implicit
    classTag: ClassTag[T]
  ): Boolean = isClass(classTag.runtimeClass)

  /**
    * Test if the given type is a case class.
    */
  def isClass (
    clazz: Class[_]
  ): Boolean = isClass(RichRuntimeMirror(clazz).getClassSymbol(clazz.getName))

  /**
    * Test if the given type is a case class.
    */
  def isClass (
    className: String
  )(
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Boolean = isClass(RichRuntimeMirror().getClassSymbol(className))

  /**
    * Test if the given type is a case class.
    */
  def isClass (
    clazzSymbol: ru.ClassSymbol
  ): Boolean = !clazzSymbol.isCaseClass

  /**
    * Return classymbols compagnion class name (the objects name)
    */
  def getClassName(
    classSymbol: ru.ClassSymbol
  ): String = getClassName(classSymbol.fullName)

  /**
    * Return classymbols compagnion class name (the objects name)
    */
  def getClassName(
    name: String
  ): String = if (name.endsWith("$")) name.substring(0, name.length - 1) else name
}

class ClassRefl (
  classSymbol: ru.ClassSymbol
) (
  implicit
  classLoader: ClassLoader = getClass.getClassLoader
) {
  require(ClassRefl.isClass(classSymbol), s"passed class: '${classSymbol.fullName}' is not a class")

  // fields
  private val _richClassMirror = RichClassMirror(classSymbol = classSymbol)

  // requirements
  val r1 = _richClassMirror.ctorMethodRefls
  val r2 = _richClassMirror

//  require(ctorRichMethodRefls.nonEmpty, s"could1 not find any ctor of class ${classSymbol.typeSignature}")

  /**
    * Getter
    */
  def ctorRichMethodRefls: List[RichMethodRefl] = _richClassMirror.ctorMethodRefls
  def className: String = classSymbol.fullName
  def typeSignature: ru.Type = classSymbol.selfType

  def ctorMethods: List[RichMethodRefl] = ctorRichMethodRefls
  def ctorMethodParameters: List[List[MethodParameter]] = ctorRichMethodRefls.map(_.parameter)

  /**
    * Instantiate this class, using the passed args
    * @param args apply method arguments
    * @return
    */
  def ctor[A] (
    args: Any*
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): A = {
    require(classSymbol.selfType <:< typeTag.tpe, s"given generic is neither a super nor the same class of ${classSymbol.selfType}")

    findCtorMatchingTheseValueTypes[A](args:_*) match {
      case Some(t) => t.apply[A](args:_*)
      case None => throw new IllegalArgumentException(s"no apply method found, which matches the passed args: '${args.mkString(", ")}'")
    }
  }

  /**
    * Return the apply RichMethodRefl which contains the given name
    */
  def findCtorMatchingTheseNames (
    methodNames: String*
  ): Option[RichMethodRefl] = {
    def hasAllParamNames (in: RichMethodRefl): Boolean = methodNames.forall { i => in.parameter.exists(_.name == i) }

    ctorRichMethodRefls
      .find(hasAllParamNames)
  }

  /**
    * Return the apply RichMethodRefl which contains the given name
    */
  def findCtorMatchingTheseValueTypes[A] (
    args: Any*
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Option[RichMethodRefl] = {
    case class ParamTypeToClass(tpe: ru.Type, clazz: Class[_])

    val enrichedArgs = args.map { i => ParamTypeToClass(GeneralRefl.typeOf(i), i.getClass) }

    def mapParamToSimple(in: MethodParameter) = ParamTypeToClass(PrimitiveRefl.toScalaType(in.typeSignature), in.clazz(zipClassTypeParamsWInstanceTypeArgs[A]()))
    def isAssignableFrom(_1: Class[_], _2: Class[_]) = PrimitiveRefl.toScalaType(_1).isAssignableFrom(PrimitiveRefl.toScalaType(_2))
    def isTypeFrom(_1: ru.Type, _2: ru.Type) = PrimitiveRefl.toScalaType(_1) <:< PrimitiveRefl.toScalaType(_2)

    def hasAllParamTypes (in: RichMethodRefl): Boolean = {
      if (args.size > in.parameter.size)
        return false

      val paramsAsTuple = in.parameter.map(mapParamToSimple)
      val paddedArgs = enrichedArgs ++ paramsAsTuple.drop(enrichedArgs.size)

      val classCheck = paramsAsTuple.zipWithIndex.map { i => isAssignableFrom(i._1.clazz, paddedArgs(i._2).clazz) }
      val typeCheck = paramsAsTuple.zipWithIndex.map { i => isTypeFrom(i._1.tpe, paddedArgs(i._2).tpe) }

      val res = typeCheck
        .zip(classCheck)
        .forall { i => i._1 || i._2 }

      res
    }

    ctorRichMethodRefls
      .find(hasAllParamTypes)
  }

  private def zipClassTypeParamsWInstanceTypeArgs[A] () (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): List[(ru.Symbol, ru.Type)] = classSymbol.typeParams.zip(typeTag.tpe.typeArgs)
}
