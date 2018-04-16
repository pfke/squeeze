package de.pfke.squeeze.core.refl.custom

import scala.reflect.runtime.{universe => ru}

case class FieldDescr(
  name: String,
  tpe: ru.Type,
  annos: List[ru.Annotation]
)
