package de.pfke.squeeze.squeezer.simple

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.simple.Squeezer_enumeratum_spec._
import enumeratum.values._

import scala.collection.immutable

object Squeezer_enumeratum_spec {
  sealed abstract class sesByteEnum(val value: Byte, val name: String) extends ByteEnumEntry
  case object sesByteEnum extends ByteEnum[sesByteEnum] {
    case class _1st() extends sesByteEnum(0x13, "_1st")
    case class _2nd() extends sesByteEnum(0x22, "_2nd")
    case class _3rd() extends sesByteEnum(0x1, "_3rd")

    val values: immutable.IndexedSeq[sesByteEnum] = findValues
  }

  sealed abstract class sesCharEnum(val value: Char, val name: String) extends CharEnumEntry
  case object sesCharEnum extends CharEnum[sesCharEnum] {
    case class _1st() extends sesCharEnum(0x1111, "_1st")
    case class _2nd() extends sesCharEnum(0x223, "_2nd")
    case class _3rd() extends sesCharEnum(0x34, "_3rd")

    val values: immutable.IndexedSeq[sesCharEnum] = findValues
  }

  sealed abstract class sesShortEnum(val value: Short, val name: String) extends ShortEnumEntry
  case object sesShortEnum extends ShortEnum[sesShortEnum] {
    case class _1st() extends sesShortEnum(0x1234, "_1st")
    case class _2nd() extends sesShortEnum(0x2345, "_2nd")
    case class _3rd() extends sesShortEnum(0x3456, "_3rd")

    val values: immutable.IndexedSeq[sesShortEnum] = findValues
  }

  sealed abstract class sesIntEnum(val value: Int, val name: String) extends IntEnumEntry
  case object sesIntEnum extends IntEnum[sesIntEnum] {
    case class _1st() extends sesIntEnum(1, "_1st")
    case class _2nd() extends sesIntEnum(2, "_2nd")
    case class _3rd() extends sesIntEnum(3, "_3rd")

    val values: immutable.IndexedSeq[sesIntEnum] = findValues
  }

  sealed abstract class sesLongEnum(val value: Long, val name: String) extends LongEnumEntry
  case object sesLongEnum extends LongEnum[sesLongEnum] {
    case class _1st() extends sesLongEnum(0x1123456, "_1st")
    case class _2nd() extends sesLongEnum(0x246465, "_2nd")
    case class _3rd() extends sesLongEnum(0x3465132, "_3rd")

    val values: immutable.IndexedSeq[sesLongEnum] = findValues
  }
}

class Squeezer_enumeratum_spec
  extends BaseSqueezerSpec {
  private val pojo_byte: sesByteEnum = sesByteEnum._1st()
  private val beBinaryData_byte = ByteString(
    0x13
  )
  private val leBinaryData_byte = ByteString(
    0x13
  )

  "[byte] using w/ big endian byte order" when {
    runSqueezerTests[sesByteEnum](ByteOrder.BIG_ENDIAN, pojo_byte, beBinaryData_byte)
  }

  "[byte] using w/ little endian byte order" when {
    runSqueezerTests[sesByteEnum](ByteOrder.LITTLE_ENDIAN, pojo_byte, leBinaryData_byte)
  }

  private val pojo_char: sesCharEnum = sesCharEnum._3rd()
  private val beBinaryData_char = ByteString(
    0x00, 0x34
  )
  private val leBinaryData_char = ByteString(
    0x34, 0x00
  )

  "[char] using w/ big endian byte order" when {
    runSqueezerTests[sesCharEnum](ByteOrder.BIG_ENDIAN, pojo_char, beBinaryData_char)
  }

  "[char] using w/ little endian byte order" when {
    runSqueezerTests[sesCharEnum](ByteOrder.LITTLE_ENDIAN, pojo_char, leBinaryData_char)
  }

  private val pojo_short: sesShortEnum = sesShortEnum._2nd()
  private val beBinaryData_short = ByteString(
    0x23, 0x45
  )
  private val leBinaryData_short = ByteString(
    0x45, 0x23
  )

  "[short] using w/ big endian byte order" when {
    runSqueezerTests[sesShortEnum](ByteOrder.BIG_ENDIAN, pojo_short, beBinaryData_short)
  }

  "[short] using w/ little endian byte order" when {
    runSqueezerTests[sesShortEnum](ByteOrder.LITTLE_ENDIAN, pojo_short, leBinaryData_short)
  }

  private val pojo_int: sesIntEnum = sesIntEnum._2nd()
  private val beBinaryData_int = ByteString(
    0x00, 0x00, 0x00, 0x02
  )
  private val leBinaryData_int = ByteString(
    0x02, 0x00, 0x00, 0x00
  )

  "[int] using w/ big endian byte order" when {
    runSqueezerTests[sesIntEnum](ByteOrder.BIG_ENDIAN, pojo_int, beBinaryData_int)
  }

  "[int] using w/ little endian byte order" when {
    runSqueezerTests[sesIntEnum](ByteOrder.LITTLE_ENDIAN, pojo_int, leBinaryData_int)
  }

  private val pojo_long: sesLongEnum = sesLongEnum._3rd()
  private val beBinaryData_long = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x03, 0x46, 0x51, 0x32
  )
  private val leBinaryData_long = ByteString(
    0x32, 0x51, 0x46, 0x03, 0x00, 0x00, 0x00, 0x00
  )

  "[long] using w/ big endian byte order" when {
    runSqueezerTests[sesLongEnum](ByteOrder.BIG_ENDIAN, pojo_long, beBinaryData_long)
  }

  "[long] using w/ little endian byte order" when {
    runSqueezerTests[sesLongEnum](ByteOrder.LITTLE_ENDIAN, pojo_long, leBinaryData_long)
  }
}
