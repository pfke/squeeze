package de.pfke.squeeze.annots

import scala.annotation.StaticAnnotation

/**
  * With this annotation u can describe the size of a list or length of a string.
  */
case class withFixedSize(
  size: Int
) extends StaticAnnotation
