package de.pfke.squeeze.zlib.refl

import java.io.{File, IOException}
import java.net.URL
import java.util.UUID

import scala.collection.JavaConverters
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object GeneralRefl {
  case class TypeInfo[A](classTag: ClassTag[A], typeTag: ru.TypeTag[A])

  /**
    * Return default values for the given types
    * @param tpe type to get values for
    * @return
    */
  def defaultValue(
    tpe: ru.Type
  ): Any = {
    tpe match {
      case t if PrimitiveRefl.isPrimitive(t) => PrimitiveRefl.defaultValue(t)

      case t if t =:= ru.typeOf[String] => ""
      case t if t <:< ru.typeOf[Option[_]] => None
      case t if t =:= ru.typeOf[UUID] => UUID.randomUUID()

      case _ => throw new IllegalArgumentException(s"could not get default arg for type $tpe")
    }
  }

  /**
    * Find all Subclasses
    */
  def findAllSubClasses[A](
    namespace: String = ""
  ) (
    implicit
    classTag: ClassTag[A]
  ): List[Class[A]] = {
    def isAssignable(in: Class[_]) = classTag.runtimeClass.isAssignableFrom(in)

    findClassesByPackage(nameSpace = namespace)
      .filter(isAssignable)
      .map(_.asInstanceOf[Class[A]])
  }

  /**
    * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
    *
    * @param nameSpace The base package
    * @return The classes
    */
  @throws(classOf[ClassNotFoundException])
  @throws(classOf[IOException])
  def findClassesByPackage(
    nameSpace: String = "de"
  ): List[Class[_]] = {
    def buildPathFromPackage(in: String) = in.replace('.', '/')
    def urlToFile(in: URL) = new File(in.getFile)

    val classLoader = Thread.currentThread().getContextClassLoader

    require(classLoader != null, "could not get class loader from current thread")

    val finderMethod = findClassesInDir(nameSpace) _

    JavaConverters.enumerationAsScalaIterator(classLoader.getResources(buildPathFromPackage(nameSpace))) // get all resources in the classloader
      .map(urlToFile)                                   // transform all urls (found files) to file objects
      .flatMap(finderMethod)                            // search all classes and return as List[Class[_]]
      .toList
  }

  /**
    * Recursive method used to find all classes in a given directory and subdirs.
    *
    * @param packageName The package name for classes found inside the base directory
    * @param directory   The base directory
    * @return The classes
    */
  @throws(classOf[ClassNotFoundException])
  private def findClassesInDir(
    packageName: String
  ) (
    directory: File
  ): List[Class[_]] = {
    if (!directory.exists())
      return List.empty

    directory.listFiles().flatMap { i =>
      if (i.isDirectory) {
        findClassesInDir(packageName + "." + i.getName)(i)
      } else if (i.getName.endsWith(".class")) {
        try {
          List(Class.forName(packageName + '.' + i.getName.substring(0, i.getName.length() - 6)))
        } catch {
          case e: ExceptionInInitializerError => List.empty
          case e: NoClassDefFoundError => List.empty
        }
      } else {
        List.empty
      }
    }.toList
  }

  /**
    * Return type info for given type
    *
    * @param classTag is the classtag info
    * @param typeTag is the typetag info
    * @tparam A is the object to generate type info for
    * @return a tuple of classtag and typetag
    */
  def generateTypeInfo[A](implicit classTag: ClassTag[A], typeTag: ru.TypeTag[A]): TypeInfo[A] = TypeInfo(classTag, typeTag)

  /**
    * Return the type from the input
    */
  def getType(
    in: Any
  ): ru.Type = {
    val clazz = in.getClass
    val classLoader = in.getClass.getClassLoader
    val rm = ru.runtimeMirror(classLoader)
    val classSymbol = rm.classSymbol(clazz)

    classSymbol
      .selfType
  }

  /**
    * Returns true, if the type is a trait or abstract
    */
  def isAbstract (in: ru.Type): Boolean = in.typeSymbol.isAbstract

  /**
    * Return true if the passed type is complex
    */
  def isComplexType (in: ru.Type): Boolean = !(isPrimitiveType(in) || isString(in) || isEnum(in) || isListType(in))

  /**
    * Returns true if the given squeezle is an enum.
    *
    * @return true or false
    */
  def isEnum (in: ru.Type): Boolean = {
    in
      .baseClasses
      .exists(_.fullName == classOf[Enumeration#Value].getCanonicalName)
  }

  /**
    * Return true if the passed type is a list
    */
  def isListType (in: ru.Type): Boolean = in <:< ru.typeOf[List[_]]

  /**
    * Return true if the passed type is a string
    */
  def isPrimitiveType (in: ru.Type): Boolean = PrimitiveRefl.isPrimitive(in)

  /**
    * Return true if the passed type is a string
    */
  def isString (in: ru.Type): Boolean = in =:= ru.typeOf[String] || in =:= ru.typeOf[java.lang.String]

  /**
    * Return all sub types for this complex class
    */
  def subFields (in: ru.Type) = ???

  /**
    * Return type signature of the given value
    */
  def typeOf[A] () (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): ru.Type = typeTag.tpe

  /**
    * Return type signature of the given value
    */
  def typeOf (
    value: Any
  ): ru.Type = {
    value.getClass


    val r1 = RichInstanceMirror(value)
    val r2 = r1.typeSignature
    val r3 = r1.asTpe
    val r4 = r1.asType
    val r5 = r2.companion

    r3

//    EnumValueRefl.typeOf(value)                                      // enum value?
//      .orElse (PrimitiveRefl.getType(value))                          // primitive?
//      .orElse (Try(RichInstanceMirror(value).typeSignature).toOption) // last try: complex?
  }

  /**
    * Get a type and unify to common type.
    * Used to be able to compare e.g. java.lang.Integer == scala.Int
    */
  def unifyType (in: ru.Type): ru.Type = PrimitiveRefl.toScalaType(in)
}
