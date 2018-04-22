/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Heiko Blobner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package de.pfke.squeeze.annots.fields

import scala.annotation.StaticAnnotation

/**
  * The annotated field will be encoded as bit field.
  * One after another bit fields will be generated together into 32 bit values
  *
  * @param bits number of bits
  */
case class asBitfield (
  bits: Int
) extends StaticAnnotation
