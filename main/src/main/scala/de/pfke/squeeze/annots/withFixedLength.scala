package de.pfke.squeeze.annots

import scala.annotation.StaticAnnotation

/**
 * With this annotation u can describe the length if a string field.
 *
 * @param bytes is the length
 */
case class withFixedLength(
  bytes: Int
) extends StaticAnnotation
