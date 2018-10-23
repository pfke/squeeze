package de.pfke.squeeze.zlib.data.byTypes.simple

import scala.concurrent.duration._
import scala.concurrent.{duration => sDur}

object DoubleIncludes
  extends DoubleIncludes

trait DoubleIncludes {
  /**
    * Format byte value into a human readable string.
    */
  implicit class ByteFormatter (
    bytes: Double
  ) {
    def asHumanReadableByte = format(value = bytes)
    def asHumanReadableSiByte = format(value = bytes, isSI = true)

    /**
      * Formats the given byte count into a human readable string.
      *
      *                              SI     BINARY
      *                   0:        0 B        0 B
      *                  27:       27 B       27 B
      *                 999:      999 B      999 B
      *                1000:     1.0 kB     1000 B
      *                1023:     1.0 kB     1023 B
      *                1024:     1.0 kB    1.0 KiB
      *                1728:     1.7 kB    1.7 KiB
      *              110592:   110.6 kB  108.0 KiB
      *             7077888:     7.1 MB    6.8 MiB
      *           452984832:   453.0 MB  432.0 MiB
      *         28991029248:    29.0 GB   27.0 GiB
      *       1855425871872:     1.9 TB    1.7 TiB
      * 9223372036854775807:     9.2 EB    8.0 EiB   (Long.MAX_VALUE)
      */
    private def format(
      value: Double,
      isSI: Boolean = false
    ): String = {
      val unit = if(isSI) 1000 else 1024

      if (value < unit) return "%.0f B".format(value)

      val exp = (math.log(value) / math.log(unit)).toInt
      val pre = (if(isSI) "kMGTPEZY" else "KMGTPEZY").charAt(exp-1) + (if(isSI) "" else "i")

      "%.3f %sB".format(value / math.pow(unit, exp), pre)
    }
  }

  /**
    * Format time value into a human readable string.
    */
  implicit class TimeFormatterFromDouble (
    v: Double
  ) {
    def asHumanReadableTime = v match {
      case _ if v / 1d > 1.0           => asHumanReadableTime_s
      case _ if v / 0.001d > 1.0       => asHumanReadableTime_ms
      case _ if v / 0.000001d > 1.0    => asHumanReadableTime_µs
      case _ if v / 0.000000001d > 1.0 => asHumanReadableTime_ns
      case _                           => asHumanReadableTime_s
    }

    def asHumanReadableTime_s  = "%.3fs".format(v)
    def asHumanReadableTime_ms = "%.3fms".format(v * 1000)
    def asHumanReadableTime_µs = "%.3fµs".format(v * 1000000)
    def asHumanReadableTime_ns = "%.3fns".format(v * 1000000000)
  }

  /**
    * Format byte value into a human readable string.
    */
  implicit class SpeedFormatterFromDouble (
    bytes: Double
  ) {
    def asSpeed(duration: Duration): String = {
      val r1 = duration.toNanos
      val r2 = (1 second).toNanos
      val r3 = bytes * r2 / r1

      s"${r3.asHumanReadableSiByte}/s"
    }

    def asSpeed(duration: Double): String = asSpeed(sDur.Duration.apply(duration, sDur.MILLISECONDS))
  }
}
