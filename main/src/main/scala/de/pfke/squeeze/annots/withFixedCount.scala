package de.pfke.squeeze.annots

import scala.annotation.StaticAnnotation

/**
 * With this annotation u can describe the size of a list.
 */
case class withFixedCount(
  count: Int
) extends StaticAnnotation
