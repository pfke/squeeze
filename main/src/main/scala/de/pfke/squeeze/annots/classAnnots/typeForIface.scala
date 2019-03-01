package de.pfke.squeeze.annots.classAnnots

import scala.annotation.StaticAnnotation
import scala.reflect.runtime.{universe => ru}

/**
  * This is an annotation for a class to assign a type value to be able to unsqueeze byteStrings for interfaces
  */
case class typeForIface(
  typeToKeyList: Map[ru.Type, Any]
) extends StaticAnnotation
