package de.pfke.squeeze.serialize.serializerHints

import de.pfke.squeeze.zlib.data.length.digital.{BitLength, ByteLength, DigitalLength}

import scala.reflect.ClassTag

trait ReadFrom {
  /**
    * Find size hint
    */
  protected def findSizeHint(
    hints: Seq[SerializerHint]
  ): Option[DigitalLength] = {
    findOneHint[SizeHint](hints = hints) match {
      case Some(SizeInBitHint(x)) => Some(BitLength(x))
      case Some(SizeInByteHint(x)) => Some(ByteLength(x))

      case _ => None
    }
  }

  /**
    * Find exact one or none hint of the given type
    *
    * @param hints search in this list
    * @param classTag hint classtag
    * @tparam A hint type
    * @return the found or none
    */
  protected def findOneHint[A <: SerializerHint](
    hints: Seq[SerializerHint]
  )(implicit
    classTag: ClassTag[A]
  ): Option[A] = {
    hints
      .collect { case t: A => t }
      .headOption
  }
}
