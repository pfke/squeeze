package de.pfke.squeeze.annots

import scala.annotation.StaticAnnotation

/**
 * The type of the named field (interface, abstract types) will be injected.
 * On de serialization, this info is used to extract the interface type.
 *
 * @param fromField is the source field name
 */
case class injectType(
  fromField: String
  ) extends StaticAnnotation
