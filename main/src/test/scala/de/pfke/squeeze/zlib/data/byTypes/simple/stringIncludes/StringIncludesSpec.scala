package de.pfke.squeeze.zlib.data.byTypes.simple.stringIncludes

import de.pfke.squeeze.zlib.data.byTypes.simple.StringIncludes
import org.scalatest.{Matchers, WordSpecLike}

class StringIncludesSpec
  extends WordSpecLike
  with Matchers {
  "on implicit class StringUtils" when {
    "using method 'countMatches'" should {
      "count 0" in {
        new StringIncludes.StringUtils("so").countMatches("os") should be (0)
      }
      "return 0, if s1 is empty" in {
        new StringIncludes.StringUtils("").countMatches("os") should be (0)
      }
      "return 0, if s2 is empty" in {
        new StringIncludes.StringUtils("frfrrf").countMatches("") should be (0)
      }
      "return correct, if s1+s2 are equal" in {
        new StringIncludes.StringUtils("sdfres").countMatches("sdfres") should be (6)
      }
      "return correct, if s1.length < s2.length" in {
        new StringIncludes.StringUtils("sdf").countMatches("sdfres") should be (3)
      }
      "return correct, if s1.length > s2.length" in {
        new StringIncludes.StringUtils("sdfdesde").countMatches("sdf") should be (3)
      }
      "return correct, if s1(x) != s2(x) " in {
        new StringIncludes.StringUtils("sdfdesde").countMatches("sdfeffrfr") should be (3)
      }
    }
  }
}
