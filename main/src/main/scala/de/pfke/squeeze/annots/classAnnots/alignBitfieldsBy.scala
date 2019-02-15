package de.pfke.squeeze.annots.classAnnots

import scala.annotation.StaticAnnotation

/**
  * Align all bit fields in one class by this value.
  * The bits are read in 1Byte, 2Byte or 4Byte blocks from the incoming data and then the bits are extracted
  */
case class alignBitfieldsBy(
  bits: Int
) extends StaticAnnotation
