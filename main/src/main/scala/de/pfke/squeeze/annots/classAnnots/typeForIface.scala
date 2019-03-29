package de.pfke.squeeze.annots.classAnnots

import scala.annotation.StaticAnnotation

/**
  * This is an annotation for a class to assign a type value to be able to unsqueeze byteStrings for interfaces
  */
case class typeForIface(
  ident: Int
) extends StaticAnnotation
