package de.pfke.squeeze.zlib.data.byTypes.simple

import java.nio.charset.{Charset, StandardCharsets}
import java.nio.{ByteBuffer, CharBuffer}

object StringIncludes
  extends StringIncludes

trait StringIncludes {
  /**
    * Convert string to byte buffer
    */
  implicit def StringToByteBuffer (
    in: String
  ) (
    implicit
    charset: Charset = StandardCharsets.UTF_8
  ): ByteBuffer = new StringToByteBuffer(in).asByteBuffer

  implicit class StringToByteBuffer (
    in: String
  ) {
    def asByteBuffer_iso8859_1: ByteBuffer = asByteBuffer(StandardCharsets.ISO_8859_1)
    def asByteBuffer_utf8: ByteBuffer = asByteBuffer(StandardCharsets.UTF_8)

    def asByteBuffer(implicit charset: Charset = StandardCharsets.UTF_8): ByteBuffer = {
      try {
        charset
          .newEncoder()
          .encode(CharBuffer.wrap(in))
      } catch {
        case e: Exception => ByteBuffer.allocateDirect(0)
      }
    }
  }

  /**
    * Utils for string value.
    */
  implicit class StringUtils (
    value: String
  ) {
    def asBoolean(): Option[Boolean] = {
      value.toLowerCase match {
        case "true" => Some(true)
        case "false" => Some(false)

        case _ => None
      }
    }

    /**
      * Try to convert from string to double and return as Option[Double]:
      *
      * toDouble("10.2").getOrElse(23.0)
      */
    def asDouble(): Option[Double] = {
      try {
        Some(java.lang.Double.parseDouble(value.replace(',', '.')))
      } catch {
        case e: NumberFormatException => None
      }
    }

    /**
      * Try to convert from string to int and return as Option[Int]:
      *
      * toInt("102").getOrElse(23)
      */
    def asInt(radix: Int = 10): Option[Int] = {
      try {
        Some(Integer.parseInt(value, radix))
      } catch {
        case e: NumberFormatException => None
      }
    }

    /**
      * Try to convert from string to int and return as Option[Int]:
      *
      * toInt("102").getOrElse(23)
      */
    def asLong(radix: Int = 10): Option[Long] = {
      try {
        Some(java.lang.Long.parseLong(value, radix))
      } catch {
        case e: NumberFormatException => None
      }
    }

    /**
      * Try to convert from string to unsigned int and return as Option[Int]:
      */
    def asUInt(radix: Int = 10): Option[Int] = {
      try {
        Some(Integer.parseUnsignedInt(value, radix))
      } catch {
        case e: NumberFormatException => None
      }
    }

    /**
      * Try to convert from string to int and return as Option[Int]:
      *
      * toInt("102").getOrElse(23)
      */
    def asULong(radix: Int = 10): Option[Long] = {
      try {
        Some(java.lang.Long.parseUnsignedLong(value, radix))
      } catch {
        case e: NumberFormatException => None
      }
    }

    /**
      * Comfort-Funktion um den übergebenen String in eine Option zu packen, oder halt None zurückzugeben,
      * wenn der String leer ist.
      */
    def asOption: Option[String] = if(value.isEmpty) None else Some(value)

    /**
      * Diese Funktion gibt die Anzahl der gleichen Zeichen von String 1 und String 2 zurück.
      */
    def countMatches(s2: String): Int = {
      if (value.isEmpty || s2.isEmpty)
        0
      else if(value(0) == s2(0))
        1 + value.drop(1).countMatches(s2.drop(1))
      else
        0
    }

    /**
      * Returns true if the given string can be converted into an integer.
      */
    def isInt(radix: Int = 10): Boolean = {
      try {
        Integer.parseInt(value, radix)
        true
      } catch {
        case e: NumberFormatException => false
      }
    }

    /**
      * Returns true if the given string can be converted into an unsigned integer.
      */
    def isUInt(radix: Int = 10): Boolean = {
      try {
        Integer.parseUnsignedInt(value, radix)
        true
      } catch {
        case e: NumberFormatException => false
      }
    }

    /**
      * Returns true if the given string can be converted into an unsigned integer.
      */
    def isULong(radix: Int = 10): Boolean = {
      try {
        java.lang.Long.parseUnsignedLong(value, radix)
        true
      } catch {
        case e: NumberFormatException => false
      }
    }
  }

  implicit class StringFormatter(in: String) {
    /**
      * Einruecken mit defaults
      */
    def indent: String = indentBy()

    /**
      * Den uebergebenen String einruecken
      *
      * @param by um diese Anzahl Spaces einruecken
      * @param indent1stLine die erste Zeile auch einruecken?
      * @return formatierter String
      */
    def indentBy(
      by: Int = 2,
      indent1stLine: Boolean = false
    ): String = trimSpaces(in.replaceAll("\n", "\n".padTo(by + 1, ' ')))

    /**
      * Trim spaces from lines
      */
    private def trimSpaces(
      that: String
    ): String = that.replaceAll("\n\\s+\n", "\n\n")
  }
}
