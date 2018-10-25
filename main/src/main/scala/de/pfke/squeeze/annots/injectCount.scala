package de.pfke.squeeze.annots

import scala.annotation.StaticAnnotation

/**
 * The number of elements of the annotated field will be injected.
 *
 * @param fromField is the source field name
 */
case class injectCount(
  fromField: String
) extends StaticAnnotation
