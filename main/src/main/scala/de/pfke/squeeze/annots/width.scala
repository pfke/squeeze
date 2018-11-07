package de.pfke.squeeze.annots

import scala.annotation.StaticAnnotation

/**
 * With this annotation u can describe the width of an field.
 */
case class width(
  size: Int
) extends StaticAnnotation
