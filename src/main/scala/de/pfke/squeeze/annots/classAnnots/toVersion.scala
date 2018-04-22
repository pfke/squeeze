package de.pfke.squeeze.annots.classAnnots

import scala.annotation.StaticAnnotation

/**
  * This is an annotation for a class to assign a class to a minimal version
  */
case class toVersion (
  major: Int,
  minor: Int,
  patch: Int = 0
) extends StaticAnnotation
