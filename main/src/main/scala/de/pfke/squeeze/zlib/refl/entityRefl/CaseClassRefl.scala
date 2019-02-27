package de.pfke.squeeze.zlib.refl.entityRefl

import de.pfke.squeeze.zlib.refl.{MethodParameter, MethodParameterValue, RichInstanceMirror, RichMethodRefl, RichRuntimeMirror}

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}
import scala.util.Try

object CaseClassRefl {
  /**
    * Create instance
    */
  def apply (
    clazz: Class[_]
  ): CaseClassRefl = apply(clazz.getCanonicalName)(clazz.getClassLoader)

  /**
    * Create instance
    */
  def apply[A] () (
    implicit
    classTag: ClassTag[A]
  ): CaseClassRefl = apply(classTag.runtimeClass)

  /**
    * Create instance
    */
  def apply (
    className: String
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): CaseClassRefl = new CaseClassRefl(classSymbol = RichRuntimeMirror().getClassSymbol(className = className))

  /**
    * Test if the given type is a case class.
    */
  def isCaseClass[T](
    implicit
    classTag: ClassTag[T]
  ): Boolean = isCaseClass(classTag.runtimeClass)

  /**
    * Test if the given type is a case class.
    */
  def isCaseClass(
    clazz: Class[_]
  ): Boolean = isCaseClass(RichRuntimeMirror(clazz).getClassSymbol(clazz.getName))

  /**
    * Test if the given type is a case class.
    */
  def isCaseClass(
    className: String
  )(
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): Boolean = isCaseClass(RichRuntimeMirror().getClassSymbol(className))

  /**
    * Test if the given type is a case class.
    */
  def isCaseClass(
    clazzSymbol: ru.ClassSymbol
  ): Boolean = clazzSymbol.isCaseClass

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

  /**
    * Return classymbols compagnion class name (the objects name)
    */
  def getCompagnionClassName(
    classSymbol: ru.ClassSymbol
  ): String = getCompagnionClassName(classSymbol.fullName)

  /**
    * Return classymbols compagnion class name (the objects name)
    */
  def getCompagnionClassName(
    name: String
  ): String = name + (if (name.endsWith("$")) "" else "$")
}

class CaseClassRefl (
  classSymbol: ru.ClassSymbol
) (
  implicit
  classLoader: ClassLoader = getClass.getClassLoader
) extends EntityRefl (
  classSymbol = classSymbol
) {
  require(CaseClassRefl.isCaseClass(classSymbol), s"passed class: '${classSymbol.fullName}' is not a case class")

  // fields
  val r1 = RichMethodRefl(classSymbol, RichMethodRefl.TERMNAME_CTOR)
  val r1_1 = r1.map(_.parameter)
//  val r2 = RichMethodRefl(classSymbol.companion, RichMethodRefl.TERMNAME_APPLY)
//  val r2_1 = r2.map(_.parameter)

//  private val _applyMethods = RichMethodRefl(classSymbol.companion, RichMethodRefl.TERMNAME_APPLY)
  private val _applyMethods = RichMethodRefl(classSymbol, RichMethodRefl.TERMNAME_CTOR)
  private val _compagnionClassName = CaseClassRefl.getCompagnionClassName(classSymbol)
  private val _richInstanceMirror = RichInstanceMirror(classSymbol = classSymbol)

  // requirements
  require(applyMethods.nonEmpty, s"could not find any apply method of case class ${classSymbol.typeSignature}")

  def applyMethods: List[RichMethodRefl] = _applyMethods
  def applyMethodParameters: List[List[MethodParameter]] = applyMethods.map(_.parameter)

  /**
    * Instantiate this class, using the passed args
    * @param args apply method arguments
    * @return
    */
  def instantiate[A] (
    args: Any*
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): A = {
    val r1 = classSymbol
    val r2 = r1.selfType
    val r2_1 = r2.typeSymbol
    val r2_2 = r2_1.typeSignature
    val r3 = typeTag.tpe
    val r3_1 = r3.typeSymbol
    val r3_2 = r3_1.typeSignature
    val r4 = r2 <:< r3
    val r5 = r3 <:< r2
    val r6 = r2_2 =:= r3_2

//    require(classSymbol.selfType <:< typeTag.tpe, s"given generic is neither a super nor the same class (${classSymbol.selfType} <:< ${typeTag.tpe})")

    findApplyMethod_matching_paramTypes[A](args:_*) match {
      case Some(t) => t.apply[A](args:_*)
      case None => throw new IllegalArgumentException(s"no apply method found, which matches the passed args: '${args.mkString(", ")}'")
    }
  }

  /**
    * Call an instance method (try to instanciate case clase without any arg)
    */
  def call[A] (
    methodName: String,
    args: Any*
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): A = {
    val instanceOpt = Try(instantiate[Any]()).toOption
    require(instanceOpt.nonEmpty, s"the case class $className needs apply arguments to instantiate -> could not call method $methodName without instance")

    call[A](
      instanceMirror = RichRuntimeMirror(classTag.runtimeClass).getInstanceMirror(instanceOpt.get),
      methodName = methodName,
      args = args:_*
    )
  }

  /**
    * Create a new instance with the given parameters and copy missing from passed instance
    */
  /**
    * Erstellt eine Kopie des mitgelieferten Objectes (<code>that</code>) und überschriebt dabei
    * die als <code>args</code> mitgelieferten Arguments/Parameter.
    *
    * @param that ist die zu kopierende Instanz
    * @param args sind die zu ersetzenden Parameter-Werte (also nicht mitzukopieren)
    * @param classTag ist der ClassTag zu dem zu kopierendem Object
    * @param typeTag ist der TypeTag zu dem zu kopierendem Object
    * @tparam A ist der Typ des zu kopierenden Objectes. Muss derselbe Typ sein wie unser <code>classSymbol</code>
    * @return Instanz des neuen Objectes
    */
  def copy[A] (
    that: A,
    args: MethodParameterValue*
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): A = {
    case class ParamAndValueOpt(param: MethodParameter, value: Option[MethodParameterValue])
    case class ParamAndValue(param: MethodParameter, value: MethodParameterValue, idx: Int)

    // prüfen, ob der Type der unsrige ist
    require(typeTag.tpe =:= classSymbol.selfType, s"passed generic is of type '${typeTag.tpe}', but has to be of '${classSymbol.selfType}'")

    // prüfen, ob alle mitgegebenen Parameter unterschiedliche Namen haben
    val names = args.map(_.name)
    require(names.distinct.size == args.size, s"There are duplicate names in passed args (${names.mkString(", ")})")

    // finden der Apply-Methode mit den meisten Argumenten (denn wir wollen ja ein komplett kopiertes Object)
    val applyMethod = applyMethods.minBy(_.parameter.size)
    // prüfen, alle übergebenen Parameter auch in der Apply-Methode genannt sind
    require(names.forall { i => applyMethod.parameter.exists { c => c.name == i } }, s"one or more args passed which cannot be found in that apply method: '$applyMethod'")

    def buildMPV(methodName: String) = MethodParameterValue(methodName, call[Any](that, methodName))
    def mergeParamValue(in: MethodParameter): ParamAndValueOpt = ParamAndValueOpt(in, args.find(_.name == in.name))
    def enrichWithValues: PartialFunction[(ParamAndValueOpt, Int), ParamAndValue] = {
      case (ParamAndValueOpt(t, Some(v)), i) => ParamAndValue(t, v, i)
      case (ParamAndValueOpt(t, None),    i) => ParamAndValue(t, buildMPV(t.name), i)
    }

    val mergedArgs = applyMethod
      .parameter                  // get parameter from apply method: List[MethodParameter]
      .map(mergeParamValue)       // merge param + param value: List[ParamAndValueOpt]
      .zipWithIndex               // prefix with index: List[(ParamAndValueOpt, Int)]
      .collect(enrichWithValues)  // get parameter values (from old entity, or - in one case - the passed value): List[(ParamAndValue, Int)]

    val applyArgs = mergedArgs
      .sortBy(_.idx)              // sort by param idx
      .map(_.value.value)         // get values only

    instantiate[A](applyArgs:_*)
  }

  /**
    * Return the apply RichMethodRefl which contains the given name
    */
  def findApplyMethod_matching_paramNames[A] (
    paramNames: String*
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Option[RichMethodRefl] = findMethod_matching_methodName_and_paramNames[A](useCompanion = true, RichMethodRefl.TERMNAME_APPLY, paramNames)

  /**
    * Return the apply RichMethodRefl which contains the given name
    */
  def findApplyMethod_matching_paramTypes[A] (
    args: Any*
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Option[RichMethodRefl] = findMethod_matching_methodName_and_paramTypes[A](useCompanion = true, RichMethodRefl.TERMNAME_APPLY, args)
}
