package de.pfke.squeeze.annots.fieldAnnots

import scala.annotation.StaticAnnotation

/**
  * The length of the annotated field or the number of elements will be injected.
  *
  * @param from is the source field name
  */
case class injectSize(
  from: String
) extends StaticAnnotation
