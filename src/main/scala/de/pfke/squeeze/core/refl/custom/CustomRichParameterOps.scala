package de.pfke.squeeze.core.refl.custom

import de.pfke.squeeze.annots.fields.asBitfield
import de.pfke.squeeze.core.refl.generic.{RichMethod, RichMethodParameter}

import scala.collection.mutable.ArrayBuffer
import scala.reflect.runtime.{universe => ru}

object CustomRichParameterOps {
  /**
    * Group all field of the passed class by bitfields and non-bitfields
    */
  def groupByBitfields (
    tpe: ru.Type
  ) (
    implicit
    classLoader: ClassLoader = getClass.getClassLoader
  ): List[List[RichMethodParameter]] = {
    RichMethod(tpe = tpe, methodName = RichMethod.TERMNAME_CTOR)
      .headOption match {
      case Some(x) => groupByBitfields(parameters = x.parameter)
      case None => throw new IllegalArgumentException(s"no ctor found for type $tpe")
    }
  }

  /**
    * Group all field of the passed class by bitfields and non-bitfields
    */
  def groupByBitfields (
    parameters: List[RichMethodParameter]
  ): List[List[RichMethodParameter]] = {
    val result = new ArrayBuffer[ArrayBuffer[RichMethodParameter]]()
    
    var isBitfieldRun = false
    parameters.foreach { i =>
      if (AnnotationOps.hasAnnot[asBitfield](i.annotations)) {
        if (!isBitfieldRun) {
          result += new ArrayBuffer[RichMethodParameter]()
          isBitfieldRun = true
        }

        result.last += i
      } else {
        if (isBitfieldRun) {
          result += new ArrayBuffer[RichMethodParameter]()
        }

        result.last += i
        isBitfieldRun = false
      }
    }

    result.map(_.toList).toList
  }
}
