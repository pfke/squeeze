package de.pfke.squeeze.core.refl.custom

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object FieldHelper {
  // fields
  private val _cache = new mutable.HashMap[ru.Type, List[FieldDescr]]()

  /**
   * Return all ctor params of this given type.
   *
   * @return reflected ctor fields
   */
  def getFields[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): List[FieldDescr] = getFields(typeTag.tpe)

  /**
   * Return all ctor params of this given type.
   *
   * @param tpe type to return
   * @return reflected ctor fields
   */
  def getFields(
    tpe: ru.Type
  ): List[FieldDescr] = {
    _cache.synchronized {
      _cache.get(tpe) match {
        case Some(x) => return x
        case None =>
      }
    }

    val discovered = reflThis(tpe)

    _cache.synchronized {
      _cache.getOrElseUpdate(tpe, discovered)
    }
  }

  /**
    * Group all field of the passed class by bitfields and non-bitfields.
    * Group by alignment.
    */
  def groupByBitfields(
    tpe: ru.Type
  ): List[List[FieldDescr]] = {
// TODO: mit squeeze entkoppeln
//    val bitsToAlign = tpe
//      .typeSymbol
//      .annotations
//      .getAlignBitfieldsBy
//      .matchTo(_.bits, BitStringAlignment.bitWidth(BitStringAlignment._32Bit))

    val r1 = getFields(tpe = tpe)

    val sss = new ArrayBuffer[ArrayBuffer[FieldDescr]]()
    var isBitfieldRun = false
    r1.foreach { i =>
// TODO: mit squeeze entkoppeln
//      if (i.annos.hasAnnot[asBitfield]) {
//        if (!isBitfieldRun) {
//          sss += new ArrayBuffer[FieldDescr]()
//          isBitfieldRun = true
//        }
//
//        sss.last += i
//      } else {
        if (isBitfieldRun || sss.isEmpty) {
          sss += new ArrayBuffer[FieldDescr]()
        }

        sss.last += i
        isBitfieldRun = false
//      }
    }

    sss.map(_.toList).toList
  }

  /**
   * Reflect all ctor args of the given type and return result.
   *
   * @param tpe type to reflect
   * @return reflected ctor fields
   */
  private def reflThis(
    tpe: ru.Type
  ): List[FieldDescr] = {
    if (tpe.typeSymbol.isAbstract) {
      List.empty
    } else {

      val constructor = tpe.decl(ru.termNames.CONSTRUCTOR).asMethod
      val args = constructor
        .asMethod
        .paramLists
        .head map { p =>
        FieldDescr(
          p.name.decodedName.toString,
          p.typeSignature,
          p.annotations
        )
      }

      args
    }
  }
}
