package de.pfke.squeeze.annots

import scala.annotation.StaticAnnotation

/**
 * The length of the annotated field will be injected.
 *
 * @param fromField is the source field name
 */
case class injectLength(
  fromField: String
) extends StaticAnnotation
