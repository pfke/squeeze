package de.pfke.squeeze.annots.classAnnots

import scala.annotation.StaticAnnotation

/**
  * This is an annotation for a class to assign a type value to be able to unsqueeze byteStrings for interfaces
  *
  * <code>
  *   case class Message (
  *     \@injectFieldType(fromField = "sub")
  *     subType: Int,
  *     sub: BaseClass
  *   )
  *
  *   trait BaseClass
  *
  *   \@fromIfaceToType(1)
  *     case class A (
  *   ) extends BaseClass
  *
  *   \@fromIfaceToType(2)
  *     case class B (
  *   ) extends BaseClass
  * </code>
  */
case class fromIfaceToType (
  value: Any
) extends StaticAnnotation
