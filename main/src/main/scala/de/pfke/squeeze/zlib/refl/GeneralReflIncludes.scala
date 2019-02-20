package de.pfke.squeeze.zlib.refl

import scala.reflect.runtime.{universe => ru}

object GeneralReflIncludes
  extends GeneralReflIncludes

trait GeneralReflIncludes {
  implicit class OpsFromObject(
    in: Any
  ) {
    /**
      * return the type of the input
      */
    def getType: ru.Type = GeneralRefl.typeOf(in)
  }

  implicit class OpsFromRUType (
    in: ru.Type
  ) {
    /**
      * Returns true, if the type is a trait or abstract
      */
    def isAbstract: Boolean = GeneralRefl.isAbstract(in)

    /**
      * Return true if the passed type is a list
      */
    def isArray: Boolean = GeneralRefl.isArray(in)

    /**
      * Return true if the passed type is complex
      */
    def isComplexType: Boolean = GeneralRefl.isComplex(in)

    /**
      * Returns true if the given squeezle is an enum.
      *
      * @return true or false
      */
    def isEnum: Boolean = GeneralRefl.isEnum(in)

    /**
      * Returns true if the given squeezle is an enum.
      *
      * @return true or false
      */
    def isEnumeratum: Boolean = GeneralRefl.isEnumeratum(in)

    /**
      * Return true if the passed type is a list
      */
    def isListType: Boolean = GeneralRefl.isListType(in)

    /**
      * Return true if the passed type is a string
      */
    def isPrimitiveType: Boolean = GeneralRefl.isPrimitive(in)

    /**
      * Return true if the passed type is a string
      */
    def isString: Boolean = GeneralRefl.isString(in)

    /**
      * Return all sub types for this complex class
      */
    def subFields: List[FieldDescr] = GeneralRefl.subFields(in)

    /**
      * Get a type and unify to common type.
      * Used to be able to compare e.g. java.lang.Integer == scala.Int
      */
    def unifyType: ru.Type = GeneralRefl.unifyType(in)
  }

  implicit class OpsFromRUTypeTag[A](
    in: ru.TypeTag[A]
  ) extends OpsFromRUType(in.tpe)
}
