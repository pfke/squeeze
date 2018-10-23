package de.pfke.squeeze.zlib.data.byTypes.simple.doubleIncludes

import de.pfke.squeeze.zlib.data.byTypes.simple.DoubleIncludes
import org.scalatest.{Matchers, WordSpecLike}

import scala.concurrent.duration._

class SpeedFormatterFromDoubleSpec
  extends WordSpecLike
  with Matchers {
  "on implicit class StringIncludes.SpeedFormatter(Long)" when {
    "using method 'asSpeed(Duration)'" should {
      "convert to B/s" in {
        new DoubleIncludes.SpeedFormatterFromDouble(563d).asSpeed(1 second) should be ("563 B/s")
      }

      "convert to kB/s" in {
        new DoubleIncludes.SpeedFormatterFromDouble(5630d).asSpeed(1 second) should be ("5,630 kB/s")
      }

      "convert to MB/s" in {
        new DoubleIncludes.SpeedFormatterFromDouble(563001000d).asSpeed(1 second) should be ("563,001 MB/s")
      }

      "convert to GB/s" in {
        new DoubleIncludes.SpeedFormatterFromDouble(563001000000d).asSpeed(1 second) should be ("563,001 GB/s")
      }

      "convert to TB/s" in {
        new DoubleIncludes.SpeedFormatterFromDouble(563001000000000d).asSpeed(1 second) should be ("563,001 TB/s")
      }

      "convert to PB/s" in {
        new DoubleIncludes.SpeedFormatterFromDouble(563001000000000000d).asSpeed(1 second) should be ("563,001 PB/s")
      }

      "convert from milli duration)" in {
        new DoubleIncludes.SpeedFormatterFromDouble(563d).asSpeed(1 milli) should be ("563,000 kB/s")
      }
    }
  }
}
