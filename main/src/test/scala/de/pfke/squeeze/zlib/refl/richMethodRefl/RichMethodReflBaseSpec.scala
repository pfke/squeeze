package de.pintono.grind.refl.core.richMethodRefl

import org.scalatest.{Matchers, WordSpec}

class RichMethodReflBaseSpec
  extends WordSpec
    with Matchers {
  val namespace = "de.pintono.grind.refl.core.richMethodRefl.mocks"

  protected def buildModClassName(className: String) = s"${buildRealClassName(className)}$$"
  protected def buildRealClassName(className: String) = s"$namespace.$className"
}
