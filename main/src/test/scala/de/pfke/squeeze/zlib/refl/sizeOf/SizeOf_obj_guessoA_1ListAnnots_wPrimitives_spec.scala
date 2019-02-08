package de.pfke.squeeze.zlib.refl.sizeOf

import de.pfke.squeeze.annots.asBitfield
import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.refl.{FieldHelper, SizeOf}
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

case class SizeOf_obj_guessoA_spec_BooleanMock (
  @asBitfield(bits = 4) _4Bits    : Boolean,
  @asBitfield(bits = 8) _8Bits    : Boolean,
  @asBitfield(bits = 9) _9Bits    : Boolean,
)
case class SizeOf_obj_guessoA_spec_javalangBooleanMock (
  @asBitfield(bits = 4) _4Bits    : java.lang.Boolean,
  @asBitfield(bits = 8) _8Bits    : java.lang.Boolean,
  @asBitfield(bits = 9) _9Bits    : java.lang.Boolean,
)

case class SizeOf_obj_guessoA_spec_ByteMock (
  @asBitfield(bits = 4) _4Bits    : Byte,
  @asBitfield(bits = 8) _8Bits    : Byte,
  @asBitfield(bits = 9) _9Bits    : Byte,
)
case class SizeOf_obj_guessoA_spec_javalangByteMock (
  @asBitfield(bits = 4) _4Bits    : java.lang.Byte,
  @asBitfield(bits = 8) _8Bits    : java.lang.Byte,
  @asBitfield(bits = 9) _9Bits    : java.lang.Byte,
)

case class SizeOf_obj_guessoA_spec_ShortMock (
  @asBitfield(bits = 4) _4Bits    : Short,
  @asBitfield(bits = 8) _8Bits    : Short,
  @asBitfield(bits = 9) _9Bits    : Short,
)
case class SizeOf_obj_guessoA_spec_javalangShortMock (
  @asBitfield(bits = 4) _4Bits    : java.lang.Short,
  @asBitfield(bits = 8) _8Bits    : java.lang.Short,
  @asBitfield(bits = 9) _9Bits    : java.lang.Short,
)

case class SizeOf_obj_guessoA_spec_IntMock (
  @asBitfield(bits = 4) _4Bits    : Int,
  @asBitfield(bits = 8) _8Bits    : Int,
  @asBitfield(bits = 9) _9Bits    : Int,
)
case class SizeOf_obj_guessoA_spec_IntegerMock (
  @asBitfield(bits = 4) _4Bits    : Integer,
  @asBitfield(bits = 8) _8Bits    : Integer,
  @asBitfield(bits = 9) _9Bits    : Integer,
)

case class SizeOf_obj_guessoA_spec_LongMock (
  @asBitfield(bits = 4) _4Bits      : Long,
  @asBitfield(bits = 8) _8Bits      : Long,
  @asBitfield(bits = 9) _9Bits      : Long,
)
case class SizeOf_obj_guessoA_spec_javalangLongMock (
  @asBitfield(bits = 4) _4Bits      : java.lang.Long,
  @asBitfield(bits = 8) _8Bits      : java.lang.Long,
  @asBitfield(bits = 9) _9Bits      : java.lang.Long,
)

case class SizeOf_obj_guessoA_spec_FloatMock (
  @asBitfield(bits = 4) _4Bits      : Float,
  @asBitfield(bits = 8) _8Bits      : Float,
  @asBitfield(bits = 9) _9Bits      : Float,
)
case class SizeOf_obj_guessoA_spec_javalangFloatMock (
  @asBitfield(bits = 4) _4Bits      : java.lang.Float,
  @asBitfield(bits = 8) _8Bits      : java.lang.Float,
  @asBitfield(bits = 9) _9Bits      : java.lang.Float,
)

case class SizeOf_obj_guessoA_spec_DoubleMock (
  @asBitfield(bits = 4) _4Bits      : Double,
  @asBitfield(bits = 8) _8Bits      : Double,
  @asBitfield(bits = 9) _9Bits      : Double,
)
case class SizeOf_obj_guessoA_spec_javalangDoubleMock (
  @asBitfield(bits = 4) _4Bits      : java.lang.Double,
  @asBitfield(bits = 8) _8Bits      : java.lang.Double,
  @asBitfield(bits = 9) _9Bits      : java.lang.Double,
)

case class SizeOf_obj_guessoA_spec_CharMock (
  @asBitfield(bits = 4) _4Bits    : Char,
  @asBitfield(bits = 8) _8Bits    : Char,
  @asBitfield(bits = 9) _9Bits    : Char,
)
case class SizeOf_obj_guessoA_spec_javalangCharMock (
  @asBitfield(bits = 4) _4Bits    : java.lang.Character,
  @asBitfield(bits = 8) _8Bits    : java.lang.Character,
  @asBitfield(bits = 9) _9Bits    : java.lang.Character,
)

class SizeOf_obj_guessoA_1ListAnnots_wPrimitives_spec
  extends WordSpec
    with Matchers {
  private def gettoFieldo[A] (
    fieldName: String
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): List[ru.Annotation] = {
    FieldHelper
      .getFields[A]()
      .find(_.name == fieldName).get.annos
  }

  "testing w/ primitve types" when {
    "Boolean" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_BooleanMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[Boolean]() should be (1 byte) }
      "4Bits" in { SizeOf.guesso[Boolean](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[Boolean](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[Boolean](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[Boolean](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[Boolean](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[Boolean](getField("_5Byte")) should be (5 byte) }
    }

    "java.lang.Boolean" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_javalangBooleanMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[java.lang.Boolean]() should be (1 byte) }
      "4Bits" in { SizeOf.guesso[java.lang.Boolean](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[java.lang.Boolean](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[java.lang.Boolean](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[java.lang.Boolean](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[java.lang.Boolean](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[java.lang.Boolean](getField("_5Byte")) should be (5 byte) }
    }

    "Byte" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_ByteMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[Byte]() should be (1 byte) }
      "4Bits" in { SizeOf.guesso[Byte](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[Byte](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[Byte](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[Byte](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[Byte](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[Byte](getField("_5Byte")) should be (5 byte) }
    }

    "java.lang.Byte" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_javalangByteMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[java.lang.Byte]() should be (1 byte) }
      "4Bits" in { SizeOf.guesso[java.lang.Byte](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[java.lang.Byte](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[java.lang.Byte](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[java.lang.Byte](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[java.lang.Byte](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[java.lang.Byte](getField("_5Byte")) should be (5 byte) }
    }

    "Short" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_ShortMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[Short]() should be (2 byte) }
      "4Bits" in { SizeOf.guesso[Short](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[Short](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[Short](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[Short](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[Short](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[Short](getField("_5Byte")) should be (5 byte) }
    }

    "java.lang.Short" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_javalangShortMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[java.lang.Short]() should be (2 byte) }
      "4Bits" in { SizeOf.guesso[java.lang.Short](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[java.lang.Short](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[java.lang.Short](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[java.lang.Short](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[java.lang.Short](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[java.lang.Short](getField("_5Byte")) should be (5 byte) }
    }

    "Int" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_IntMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[Int]() should be (4 byte) }
      "4Bits" in { SizeOf.guesso[Int](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[Int](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[Int](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[Int](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[Int](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[Int](getField("_5Byte")) should be (5 byte) }
    }

    "Integer" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_IntegerMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[Integer]() should be (4 byte) }
      "4Bits" in { SizeOf.guesso[Integer](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[Integer](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[Integer](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[Integer](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[Integer](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[Integer](getField("_5Byte")) should be (5 byte) }
    }

    "Long" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_LongMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[Long]() should be (8 byte) }
      "4Bits" in { SizeOf.guesso[Long](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[Long](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[Long](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[Long](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[Long](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[Long](getField("_5Byte")) should be (5 byte) }
      "17Byte" in { SizeOf.guesso[Long](getField("_17Byte")) should be (17 byte) }
    }

    "java.lang.Long" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_javalangLongMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[java.lang.Long]() should be (8 byte) }
      "4Bits" in { SizeOf.guesso[java.lang.Long](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[java.lang.Long](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[java.lang.Long](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[java.lang.Long](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[java.lang.Long](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[java.lang.Long](getField("_5Byte")) should be (5 byte) }
      "17Byte" in { SizeOf.guesso[java.lang.Long](getField("_17Byte")) should be (17 byte) }
    }

    "Float" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_FloatMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[Float]() should be (4 byte) }
      "4Bits" in { SizeOf.guesso[Float](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[Float](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[Float](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[Float](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[Float](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[Float](getField("_5Byte")) should be (5 byte) }
      "17Byte" in { SizeOf.guesso[Float](getField("_17Byte")) should be (17 byte) }
    }

    "java.lang.Float" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_javalangFloatMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[java.lang.Float]() should be (4 byte) }
      "4Bits" in { SizeOf.guesso[java.lang.Float](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[java.lang.Float](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[java.lang.Float](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[java.lang.Float](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[java.lang.Float](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[java.lang.Float](getField("_5Byte")) should be (5 byte) }
      "17Byte" in { SizeOf.guesso[java.lang.Float](getField("_17Byte")) should be (17 byte) }
    }

    "Double" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_DoubleMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[Double]() should be (8 byte) }
      "4Bits" in { SizeOf.guesso[Double](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[Double](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[Double](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[Double](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[Double](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[Double](getField("_5Byte")) should be (5 byte) }
      "17Byte" in { SizeOf.guesso[Double](getField("_17Byte")) should be (17 byte) }
    }

    "java.lang.Double" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_javalangDoubleMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[java.lang.Double]() should be (8 byte) }
      "4Bits" in { SizeOf.guesso[java.lang.Double](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[java.lang.Double](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[java.lang.Double](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[java.lang.Double](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[java.lang.Double](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[java.lang.Double](getField("_5Byte")) should be (5 byte) }
      "17Byte" in { SizeOf.guesso[java.lang.Double](getField("_17Byte")) should be (17 byte) }
    }

    "Char" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_CharMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[Char]() should be (2 byte) }
      "4Bits" in { SizeOf.guesso[Char](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[Char](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[Char](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[Char](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[Char](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[Char](getField("_5Byte")) should be (5 byte) }
    }

    "java.lang.Char" should {
      def getField(name: String): List[ru.Annotation] = gettoFieldo[SizeOf_obj_guessoA_spec_javalangCharMock](fieldName = name)

      "no annotation" in { SizeOf.guesso[java.lang.Character]() should be (2 byte) }
      "4Bits" in { SizeOf.guesso[java.lang.Character](getField("_4Bits")) should be (4 bit) }
      "8Bits" in { SizeOf.guesso[java.lang.Character](getField("_8Bits")) should be (8 bit) }
      "9Bits" in { SizeOf.guesso[java.lang.Character](getField("_9Bits")) should be (9 bit) }
      "1Byte" in { SizeOf.guesso[java.lang.Character](getField("_1Byte")) should be (1 byte) }
      "4Byte" in { SizeOf.guesso[java.lang.Character](getField("_4Byte")) should be (4 byte) }
      "5Byte" in { SizeOf.guesso[java.lang.Character](getField("_5Byte")) should be (5 byte) }
    }
  }
}
