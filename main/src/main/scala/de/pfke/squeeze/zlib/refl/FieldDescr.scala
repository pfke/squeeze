package de.pintono.grind.refl

import de.pintono.grind.refl.core.GeneralReflIncludes

import scala.annotation.StaticAnnotation
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

case class FieldDescr(
  name: String,
  tpe: ru.Type,
  annos: List[ru.Annotation]
)

