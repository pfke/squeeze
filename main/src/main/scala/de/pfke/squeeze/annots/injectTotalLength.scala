package de.pfke.squeeze.annots

import scala.annotation.StaticAnnotation

/**
  * A field decorated with this annotation will be filled with the total encoded length
  * of the object.
  */
case class injectTotalLength()
  extends StaticAnnotation
