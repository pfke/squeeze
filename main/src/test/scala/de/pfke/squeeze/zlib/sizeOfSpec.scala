package de.pfke.squeeze.zlib

import de.pfke.squeeze.zlib.refl.sizeOf
import org.scalatest.{Matchers, WordSpec}

class sizeOfSpec
  extends WordSpec
    with Matchers {
  "testing sizeOf usage" when {
    "nothing special" should {
      "'Boolean' should have correct size" in {
        sizeOf.Boolean should be (1)
      }

      "'Byte' should have correct size" in {
        sizeOf.Byte should be (1)
      }

      "'Char' should have correct size" in {
        sizeOf.Char should be (2)
      }

      "'Double' should have correct size" in {
        sizeOf.Double should be (8)
      }

      "'Float' should have correct size" in {
        sizeOf.Float should be (4)
      }

      "'Int' should have correct size" in {
        sizeOf.Int should be (4)
      }

      "'Long' should have correct size" in {
        sizeOf.Long should be (8)
      }

      "'Short' should have correct size" in {
        sizeOf.Short should be (2)
      }
    }
  }
}
