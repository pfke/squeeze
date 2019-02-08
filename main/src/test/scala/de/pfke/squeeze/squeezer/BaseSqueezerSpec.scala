package de.pfke.squeeze.squeezer

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

abstract class BaseSqueezerSpec
  extends WordSpec
    with Matchers {

  protected def runSqueezerTests[A](
    byteOrder : ByteOrder,
    inPojo    : A,
    binaryData: ByteString,
    outPojo   : Option[A] = None,
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Unit = {
    implicit val byteOrderToUse: ByteOrder = byteOrder

    val squeezer = Squeezer()

    "convert correct to binary" should {
      "should return a ByteString with correct length" in {
        squeezer
          .toBinary(in = inPojo)
          .length should be(binaryData.size)
      }

      "should return the correct ByteString" in {
        squeezer
          .toBinary(in = inPojo) should be(binaryData)
      }
    }

    "convert correct from binary" should {
      "should return the correct object" in {
        squeezer
          .deSerialize[A](binaryData) should be (outPojo.getOrElse(inPojo))
      }
    }
  }

  protected def runBE_n_LE[A](
    descr: String,
    inPojo: A,
    beBinaryData: ByteString,
    leBinaryData: ByteString,
    outPojo: Option[A] = None,
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Unit = {
    s"[$descr] using w/ big endian byte order" when {
      runSqueezerTests[A](ByteOrder.BIG_ENDIAN, inPojo, beBinaryData, outPojo = outPojo)
    }

    s"[$descr] using w/ little endian byte order" when {
      runSqueezerTests[A](ByteOrder.LITTLE_ENDIAN, inPojo, leBinaryData, outPojo = outPojo)
    }
  }
}
