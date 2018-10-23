package de.pintono.grind.refl.core

import java.io.File

import scala.annotation.StaticAnnotation
import scala.collection.mutable
import scala.collection.JavaConverters._
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}
import scala.util.Try
import scala.util.control.NonFatal

object ClassFinder {
  case class ClassInfo[A](classSymbol: ru.ClassSymbol, tpe: ru.Type, clazz: Class[_], annotations: List[ru.Annotation])

  // fields
  private val _cache = new mutable.HashMap[String, ClassInfo[_]]()
  private val _runtimeMirror = new mutable.HashMap[ClassLoader, ru.RuntimeMirror]()

  def defaultClassLoader: ClassLoader = Thread.currentThread().getContextClassLoader

  /**
    * Method to find all classes in the given classloader and within the given package
    */
  def findAllClasses(
    packageName: String = ""
  )(
    implicit
    classLoader: ClassLoader = defaultClassLoader
  ): List[ClassInfo[_]] = {
    val resources = classLoader.getResources(packageName.replace('.', '/'))

    val dirs = resources.asScala.map { i => new File(i.getFile) }
    val findClassesFunc = findClasses(packageName = "", recursiveResult = Nil)(_)
    val classes = dirs
      .toList
      .par
      .map(findClassesFunc)

    classes
      .flatten
      .toList
  }

  /**
    * Method to find all classes in the given classloader and within the given package
    */
  def findAllClassesAnnotatedWith[A <: StaticAnnotation](
    packageName: String = ""
  )(
    implicit
    classLoader: ClassLoader = defaultClassLoader,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): List[ClassInfo[_]] = {
    findAllClasses(packageName = packageName)
      .par
      .filter { i => AnnotationRefl.contains[A](i.annotations) }
      .toList
  }

  /**
    * Method to find all classes in the given classloader and within the given package
    */
  def findAllClassesDerivedFrom[A](
    packageName: String = ""
  )(
    implicit
    classLoader: ClassLoader = defaultClassLoader,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): List[ClassInfo[_]] = findAllClassesDerivedFrom(tpe = typeTag.tpe, packageName = packageName)

  /**
    * Method to find all classes in the given classloader and within the given package
    */
  def findAllClassesDerivedFromAndAnnotatedWith[A, B <: StaticAnnotation](
    packageName: String = ""
  )(
    implicit
    classLoader: ClassLoader = defaultClassLoader,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A],
    annotClassTag: ClassTag[B],
    annotTypeTag: ru.TypeTag[B]
  ): List[ClassInfo[_]] = findAllClassesDerivedFrom(tpe = typeTag.tpe, packageName = packageName)

  /**
    * Method to find all classes in the given classloader and within the given package
    */
  def findAllClassesDerivedFrom(
    tpe: ru.Type,
    packageName: String
  )(
    implicit
    classLoader: ClassLoader
  ): List[ClassInfo[_]] = {
    def isDerived(that: ClassInfo[_]): Boolean = {
      Try(that.tpe <:< tpe)
        .toOption match {
        case Some(x) => x
        case None => false
      }
    }

    findAllClasses(packageName = packageName)
      .filter(isDerived)
      .filterNot(_.tpe =:= tpe) // den angefragten Typ rausschmeissen
  }

  /**
    * Method to find all classes within the given dir
    */
  def findClasses(
    packageName: String,
    recursiveResult: List[ClassInfo[_]]
  )(
    dir: File
  )(
    implicit
    classLoader: ClassLoader = defaultClassLoader
  ): List[ClassInfo[_]] = {

    val r1 = findClasses(recursiveResult = recursiveResult)(dir = dir, packageName = packageName)
    r1
  }

  /**
    * Method to find all classes within the given dir
    */
  def findClasses(
    recursiveResult: List[ClassInfo[_]]
  )(
    dir: File,
    packageName: String
  )(
    implicit
    classLoader: ClassLoader
  ): List[ClassInfo[_]] = {
    if (!dir.exists()) return Nil

    def buildClassName(in: File) = buildName(in.getName.dropRight(6))
    def buildDirName(in: File) = buildName(in.getName + ".")
    def buildName(in: String) = s"$packageName$in"
    val findClassesFunc = findClasses(recursiveResult = recursiveResult)(_, _)

    dir
      .listFiles()
      .par
      .flatMap {
        case t if t.isDirectory => findClassesFunc(t, buildDirName(t))
        case t if t.getName.contains("$$anonfun") => recursiveResult // führt zu einen Null-ptr mit match-Error bei classSymbol.selfType, deswegen annonfun rausfiltern
        case t if t.getName.endsWith(".class") && hasTypeInfo(forName = buildClassName(t)) => recursiveResult.::(getTypeInfo(forName = buildClassName(t)))
        case _ => recursiveResult
      }
      .toList
  }

  private def getTypeInfo(
    forName: String
  )(
    implicit
    classLoader: ClassLoader
  ) = _cache.getOrElseUpdate(forName, reflectTypeInfo(forName))

  private def hasTypeInfo (
    forName: String
  ) (
    implicit
    classLoader: ClassLoader
  ): Boolean = {
    Try(getTypeInfo(forName))
      .toOption
      .nonEmpty
  }

  private def reflectTypeInfo(
    forName: String
  )(
    implicit
    classLoader: ClassLoader
  ): ClassInfo[_] = {
    val rm = _runtimeMirror.getOrElseUpdate(classLoader, ru.runtimeMirror(classLoader))
    val clazz = try { // turning a FatalException into a NonFatal one, can be catched by Try()
      Class.forName(forName)
    } catch {
      case e: NoClassDefFoundError => throw new IllegalArgumentException(e)
    }
    val classSymbol = rm.classSymbol(clazz)

    ClassInfo(
      classSymbol = classSymbol,
      tpe = classSymbol.selfType,
      clazz = clazz,
      annotations = classSymbol.selfType.typeSymbol.annotations
    )
  }
}
