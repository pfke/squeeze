package de.pfke.squeeze.zlib.refl

import de.pfke.squeeze.annots.AnnotationHelperIncludes._
import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.data.collection.bitString.BitStringAlignment

import scala.collection.mutable
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
    * Group all field by bitfields and non-bitfields and keep track of BitStringAlignment.
    */
  def groupByBitfields(
    tpe: ru.Type
  ): List[List[FieldDescr]] = {
    val bitsToAlign = tpe
      .typeSymbol
      .annotations
      .getAlignBitfieldsBy
      .matchTo(_.bits, BitStringAlignment.bitWidth(BitStringAlignment._32Bit))
    val allFields = getFields(tpe = tpe)

    def bothHasOrBothHasnt(a: FieldDescr, b: FieldDescr): Boolean = !(a.annos.hasAsBitfield ^ b.annos.hasAsBitfield)
    def countBits(in: List[FieldDescr]): Int = in.foldLeft(0)((sum,iter) => sum + getBits(iter))
    def getBits(in: FieldDescr): Int = in.annos.getAsBitfield.matchTo(_.bits, default = 0)
    def groupBy(
      resultList : List[List[FieldDescr]],
      currentList: List[FieldDescr],
      in         : List[FieldDescr]
    ): List[List[FieldDescr]] = {
      in match {
        case Nil => resultList ++ List(currentList)

        case head :: tail if (countBits(currentList) + getBits(head)) > bitsToAlign => groupBy(resultList ++ List(currentList), List(head), tail)
        case head :: tail if bothHasOrBothHasnt(currentList.last, head)             => groupBy(resultList, currentList ++ List(head), tail)

        case head :: tail => groupBy(resultList ++ List(currentList), List(head), tail)
      }
    }

    groupBy(List.empty, List(allFields.head), allFields.drop(1))
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
