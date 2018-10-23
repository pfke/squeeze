package de.pintono.grind.refl.core.richRuntimeMirror

import de.pintono.grind.refl.core.RichRuntimeMirror
import org.scalatest.{Matchers, WordSpec}

class RichRuntimerMirror_obj_apply_Spec
  extends WordSpec
    with Matchers {
  "testing w/ no arg" when {
    "have no implicit defined" should {
      "should return an instance" in {
        RichRuntimeMirror() should not be null
      }

      "classLoader should not be null" in {
        RichRuntimeMirror().classLoader should not be null
      }

      "runtimeMirror should not be null" in {
        RichRuntimeMirror().runtimeMirror should not be null
      }
    }
  }
}
