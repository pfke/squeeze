package de.pfke.squeeze.annots

import scala.annotation.StaticAnnotation

/**
 * The annotated field will be encoded as bit field.
 * One after another bit fields will be generated together into 32 bit values
 *
 * @param bits number of bits
 */
case class asBitfield(
  bits: Int
) extends StaticAnnotation
