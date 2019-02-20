package de.pfke.squeeze.squeezer.complex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.complex.Squeezer_caseClass_w_enumeratum_spec.{caseClass_w_enumeration, scwesEnumItem}
import enumeratum.EnumEntry
import enumeratum.values.{IntEnum, IntEnumEntry}

import scala.collection.immutable

object Squeezer_caseClass_w_enumeratum_spec {
  EnumEntry
  sealed abstract class scwesEnumItem(val value: Int, val name: String) extends IntEnumEntry
  case object scwesEnumItem extends IntEnum[scwesEnumItem] {
    case class _1st() extends scwesEnumItem(1, "_1st")
    case class _2nd() extends scwesEnumItem(2, "_2nd")
    case class _3rd() extends scwesEnumItem(3, "_3rd")

    val values: immutable.IndexedSeq[scwesEnumItem] = findValues
  }

  case class caseClass_w_enumeration(
    param1: Long,
    param2: scwesEnumItem,
    param3: Short,
  )
}

class Squeezer_caseClass_w_enumeratum_spec
  extends BaseSqueezerSpec {
  scwesEnumItem._2nd

  private val pojo = caseClass_w_enumeration(
    param1 = 17433124l,
    param2 = scwesEnumItem._2nd(),
    param3 = 1234.toShort,
  )
  private val beBinaryData = ByteString(
    0x00, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x24,

    0x00, 0x00, 0x00, 0x02,

    0x04, 0xd2,
  )
  private val leBinaryData = ByteString(
    0x24, 0x02, 0x0a, 0x01, 0x00, 0x00, 0x00, 0x00,

    0x02, 0x00, 0x00, 0x00,

    0xd2, 0x04,
  )

  "using w/ big endian byte order" when {
    runSqueezerTests[caseClass_w_enumeration](ByteOrder.BIG_ENDIAN, pojo, beBinaryData)
  }

  "using w/ little endian byte order" when {
    runSqueezerTests[caseClass_w_enumeration](ByteOrder.LITTLE_ENDIAN, pojo, leBinaryData)
  }
}
