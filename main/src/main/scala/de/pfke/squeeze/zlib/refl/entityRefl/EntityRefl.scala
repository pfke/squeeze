package de.pfke.squeeze.zlib.refl.entityRefl

import de.pfke.squeeze.zlib.refl.{GeneralRefl, MethodParameter, PrimitiveRefl, RichMethodRefl, RichRuntimeMirror}

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

abstract class EntityRefl (
  classSymbol: ru.ClassSymbol,
  dynamicTypeArgs: List[ru.Type] = List.empty
) (
  implicit
  classLoader: ClassLoader
) {
  /**
    * Getter
    */
  def className: String = classSymbol.fullName
  def typeSignature: ru.Type = classSymbol.selfType

  /**
    * Call an instance method (instance given)
    */
  def call[A] (
    instance: Any,
    methodName: String,
    args: Any*
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): A = {
    call[A](
      instanceMirror = RichRuntimeMirror(instance.getClass).getInstanceMirror(instance),
      methodName = methodName,
      args = args:_*
    )
  }

  /**
    * Call an instance method (instance mirror given)
    */
  def call[A] (
    instanceMirror: ru.InstanceMirror,
    methodName: String,
    args: Any*
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): A = {
    val richMethodReflOpt = RichMethodRefl(classSymbol, ru.TermName(methodName))
      .find(_.matchesParameter(args))

    require(richMethodReflOpt.nonEmpty, s"[$className] no method with the passed parameter types+order found (passed: $args)")
    val richMethodRefl = richMethodReflOpt.get

    val methodReturnType = richMethodRefl
      .methodSymbol
      .returnType

    require(methodReturnType <:< typeTag.tpe, s"[$className] return type of method $methodName ($methodReturnType) is different to passed generic (${typeTag.tpe})")

    richMethodRefl.call[A](instanceMirror, args:_*)
  }

  /**
    * Findet die Methode mit angegebenem Namen und den vorhandenen Paremetern
    */
  def findMethod_matching_methodName_and_paramNames[A] (
    useCompanion: Boolean,
    methodName: String,
    paramNames: Seq[String]
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Option[RichMethodRefl] = {
    findMethod_matching_methodName_and_paramNames(
      useCompanion = useCompanion,
      methodName = RichMethodRefl.toTermName(methodName),
      paramNames = paramNames
    )
  }

  /**
    * Findet die Methode mit angegebenem Namen und den vorhandenen Paremetern
    */
  def findMethod_matching_methodName_and_paramNames[A] (
    useCompanion: Boolean,
    methodName: ru.TermName,
    paramNames: Seq[String]
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Option[RichMethodRefl] = {
    findMethod_matching_methodName_and_paramNames(
      symbol = if (useCompanion) classSymbol.companion else classSymbol,
      methodName = methodName,
      paramNames = paramNames
    )
  }

  /**
    * Findet die Methode mit angegebenem Namen und den vorhandenen Paremetern
    */
  def findMethod_matching_methodName_and_paramNames[A] (
    symbol: ru.Symbol,
    methodName: ru.TermName,
    paramNames: Seq[String]
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Option[RichMethodRefl] = {
    def hasAllParamNames (in: RichMethodRefl): Boolean = paramNames.forall { i => in.parameter.exists(_.name == i) }

    RichMethodRefl(symbol, methodName)
      .find(hasAllParamNames)
  }

  /**
    * Return the apply RichMethodRefl which contains the given name
    */
  def findMethod_matching_methodName_and_paramTypes[A] (
    useCompanion: Boolean,
    methodName: String,
    args: Seq[Any]
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Option[RichMethodRefl] = {
    findMethod_matching_methodName_and_paramTypes(
      useCompanion = useCompanion,
      methodName = RichMethodRefl.toTermName(methodName),
      args = args
    )
  }

  /**
    * Return the apply RichMethodRefl which contains the given name
    */
  def findMethod_matching_methodName_and_paramTypes[A] (
    useCompanion: Boolean,
    methodName: ru.TermName,
    args: Seq[Any]
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Option[RichMethodRefl] = {
    findMethod_matching_methodName_and_paramTypes[A](
      symbol = if (useCompanion) classSymbol.companion else classSymbol,
      methodName = methodName,
      args = args
    )
  }

  /**
    * Return the apply RichMethodRefl which contains the given name
    */
  private def findMethod_matching_methodName_and_paramTypes[A] (
    symbol: ru.Symbol,
    methodName: ru.TermName,
    args: Seq[Any]
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Option[RichMethodRefl] = {
    case class EnrichedArg(tpe: ru.Type, clazz: Class[_])

    val enrichedArgs = args.map { i => EnrichedArg(PrimitiveRefl.toScalaType(GeneralRefl.typeOf(i)), GeneralRefl.unifyType(i.getClass)) }

    def mapParamToSimple(in: MethodParameter) = EnrichedArg(
      GeneralRefl.unifyType(
        GeneralRefl.toScalaType(in.clazz(zipClassTypeParamsWInstanceTypeArgs[A]()))
      ),
      GeneralRefl.unifyType(
        in.clazz(zipClassTypeParamsWInstanceTypeArgs[A]())
      )
    )
    def isAssignableFrom(_1: Class[_], _2: Class[_]) = _1.isAssignableFrom(_2)
    def isTypeFrom(_1: ru.Type, _2: ru.Type) = _1 <:< _2

    def hasAllParamTypes (in: RichMethodRefl): Boolean = {
      if (args.size > in.parameter.size)
        return false

      val paramsAsTuple = in.parameter.map(mapParamToSimple)
      val paddedArgs = enrichedArgs ++ paramsAsTuple.drop(enrichedArgs.size)

      val classCheck = paramsAsTuple.zipWithIndex.map { i => isAssignableFrom(i._1.clazz, paddedArgs(i._2).clazz) }
      val typeCheck = paramsAsTuple.zipWithIndex.map { i => isTypeFrom(i._1.tpe, paddedArgs(i._2).tpe) }

      val res = !typeCheck
        .zip(classCheck)
        .forall { i => i._1 || i._2 }

      !res
      true
    }

    val r1 = RichMethodRefl(symbol, methodName)
    val r2 = r1.head
    val r3 = r2.parameter

    RichMethodRefl(symbol, methodName)
      .find(hasAllParamTypes)
  }

  protected def zipClassTypeParamsWInstanceTypeArgs[A] () (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): List[(ru.Symbol, ru.Type)] = {
    val res = classSymbol.typeParams.zip(typeTag.tpe.typeArgs)

    val r0 = typeTag.tpe.typeArgs
    val r1 = dynamicTypeArgs
    val r3 = r0.zip(r1)
    val r4 = r1 ++ r0.drop(r1.size)
    val res2 = classSymbol.typeParams.zip(r4)


    res2
  }
}
