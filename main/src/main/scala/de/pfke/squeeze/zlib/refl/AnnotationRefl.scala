package de.pfke.squeeze.zlib.refl

import de.pfke.squeeze.zlib.refl.entityRefl.CaseClassRefl

import scala.annotation.StaticAnnotation
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object AnnotationRefl {
  /**
    * Returns true if the list contains the given annotation
    *
    * @param annots list of annotations
    * @param typeTag type tp find
    * @tparam A type to return
    * @return option of an instantiated annotation
    */
  def contains[A] (
    annots: List[ru.Annotation]
  ) (
    implicit
    typeTag: ru.TypeTag[A]
  ): Boolean = {
    find[A](annots) match {
      case Some(_) => true
      case None    => false
    }
  }

  /**
    * Find annotation of the given type within the list and instantiate.
    *
    * @param annots list of annotations
    * @param typeTag type tp find
    * @tparam A type to return
    * @return option of an instantiated annotation
    */
  def create[A] (
    annots: List[ru.Annotation]
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A],
    classLoader: ClassLoader
  ): Option[A] = {
    find[A](annots) match {
      case Some(x) => Some(instantiate[A](x))
      case None    => None
    }
  }

  /**
    * Find annotation of the given type within the list.
    *
    * @param annots list of annotations
    * @param typeTag type tp find
    * @tparam A type to return
    * @return option of the annotation
    */
  def find[A] (
    annots: List[ru.Annotation]
  ) (
    implicit
    typeTag: ru.TypeTag[A]
  ): Option[ru.Annotation] = {
    val r1 = annots.map(_.tree.tpe)
    val r2 = annots.map(_.tree.tpe.typeSymbol.typeSignature)

    val e1 = typeTag.tpe
    val e2 = e1.typeSymbol.typeSignature

    val res2 = annots.find(i => i.tree.tpe.typeSymbol.typeSignature =:= typeTag.tpe.typeSymbol.typeSignature)

    val res = annots.find { a => a.tree.tpe =:= typeTag.tpe }
    res2
  }

  /**
    * Get the class file annotation of the given class type.
    * An exception is thrown if the given type is not annotated.
    *
    * @param tpe type information of the class
    * @param typeTag type information of the annotation
    * @tparam A is the type of the annotation to find
    * @return instance of the annotation
    */
  def get[A <: StaticAnnotation] (
    tpe: ru.Type
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A],
    classLoader: ClassLoader
  ): A = {
    find[A](tpe.typeSymbol.asClass.annotations) match {
      case Some(x) => instantiate[A](x)
      case None    => throw new IllegalArgumentException(s"$tpe has no annotation of type ${typeTag.tpe}")
    }
  }

  /**
    * Get the class file annotation of the given class type.
    * An exception is thrown if the given type is not annotated.
    *
    * @param annotTypeTag type information of the annotation
    * @param classTypeTag type information of the class
    * @tparam A is the type of the annotation to find
    * @tparam V is the type of the annotated class
    * @return instance of the annotation
    */
  def get[A <: StaticAnnotation, V] () (
    implicit
    annotClassTag: ClassTag[A],
    annotTypeTag: ru.TypeTag[A],
    classTypeTag: ru.TypeTag[V],
    classLoader: ClassLoader
  ): A = get[A](classTypeTag.tpe)

  /**
    * Instantiate the given annotation
    *
    * @param annot the AnnotationInfo
    * @param annotTypeTag annotation type
    * @tparam A generic type
    * @return instantiated
    */
  def instantiate[A] (
    annot: ru.Annotation
  ) (
    implicit
    annotClassTag: ClassTag[A],
    annotTypeTag: ru.TypeTag[A],
    classLoader: ClassLoader
  ): A = {
    import scala.reflect.runtime.universe._ // sorgt dafür, dass die haessliche 'abstract type pattern reflect.runtime.universe.AssignOrNamedArg is unchecked since it is eliminated by erasure' wegkommt

    require(annot.tree.tpe.typeSymbol.typeSignature =:= annotTypeTag.tpe.typeSymbol.typeSignature, "passed argument does not match generic annotation type")

    val annotType = annotTypeTag.tpe                    // get the expected annotation type to match
    val args = annot                                    // retrieve the args. These are returned as a list of Tree.
      .tree
      .children
      .tail
      .collect {
      case ru.Literal(ru.Constant(m)) => m
      case m => throw new IllegalArgumentException(s"only contant values allowed as anno params ($m)")
    }

    new CaseClassRefl(annotType.typeSymbol.asClass, dynamicTypeArgs = annot.tree.tpe.typeArgs)
      .instantiate[A](args:_*)
  }

  /**
    * Return true if one of the given annotations is derived from the wanted type.
    *
    * @param annots list of annotations
    * @param typeTag type info of the wanted super class
    * @tparam A generic info
    * @return true if on of the annotations has A as its super class
    */
  def isDerivedFrom[A] (
    annots: List[ru.Annotation]
  ) (
    implicit
    typeTag: ru.TypeTag[A]
  ): Boolean = {
    annots
      .flatMap(_.tree.tpe.baseClasses) // get all base classes of this annotation
      .distinct                        // remove duplicates
      .exists(isSameClass[A])          // exists searched type?
  }

  /**
    * Returns true if the symbol is the same class like the generic type parameter.
    *
    * @param symbol to check
    * @param typeTag type info
    * @tparam A type to match
    * @return true is symbol is the same class like A
    */
  def isSameClass[A] (
    symbol: ru.Symbol
  ) (
    implicit
    typeTag: ru.TypeTag[A]
  ): Boolean = symbol.asType.toType == typeTag.tpe
}
