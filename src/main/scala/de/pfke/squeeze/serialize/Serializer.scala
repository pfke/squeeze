package de.pfke.squeeze.serialize

import java.nio.ByteOrder
import java.nio.charset.{Charset, StandardCharsets}

import akka.util.ByteStringBuilder
import de.pfke.squeeze.core.PatchLevelVersion
import de.pfke.squeeze.core.data.byTypes.complex.OptionOpsIncludes._
import de.pfke.squeeze.core.data.collection.AnythingIterator
import de.pfke.squeeze.core.data.length.digital.{BitLength, ByteLength, DigitalLength}
import de.pfke.squeeze.core.refl.generic.GenericOps.TypeInfo
import de.pfke.squeeze.serialize.serializerHints._

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

/**
  * Trait to describe a serializer and deserializer for a specific object
  *
  * @tparam A is the type of the object to serialize / deserialize
  */
trait Serializer[A] {
  /**
    * Return type info for the serialized/deserialized object
    *
    * @return classtag and type tag
    */
  def objectTypeInfo: TypeInfo[A]

  /**
    * Read from given input and deserialize to an object A
    *
    * @param hints how to deserialize (e.g. read bits or bytes, pass an iface type)
    * @param byteOrder BigEndian or LittleEndian
    * @param serializerContainer can be asked for sub types
    * @param version only imported for ifaces
    * @return deserialzed object
    */
  def read(
    iter: AnythingIterator,
    hints: SerializerHint*
  )(implicit
    byteOrder: ByteOrder,
    serializerContainer: SerializerContainer,
    version: Option[PatchLevelVersion]
  ): A = readForMe(iter = iter, hints = hints)

  /**
    * Serialize the input
    *
    * @param data is the input data
    * @param hints how to deserialize (e.g. read bits or bytes, pass an iface type)
    * @param byteOrder BigEndian or LittleEndian
    * @param serializerContainer is the object which can call write code on complex sub objects
    * @param version only imported for ifaces
    */
  def write(
    data: A,
    hints: SerializerHint*
  )(
    implicit
    byteOrder: ByteOrder,
    serializerContainer: SerializerContainer,
    version: Option[PatchLevelVersion]
  ): Unit = writeForMe(value = data, hints = hints)


  /**
    * Default Gröesse die der Serizalizer lesen möchte.
    */
  protected def defaultSize: Option[DigitalLength] = None

  /**
    * Default op um einen Wert in einen ByteStringBuilder zu schreiben.
    * Diese Methode sollte nur von einem Basis-Typen überschrieben werden (Boolean, oder so. Also einem
    * primitiven Typen, der am Ende wirklich schreibt)
    */
  protected def byteStringWriteOp(implicit byteOrder: ByteOrder): Option[(ByteStringBuilder, A) => Unit] = None

  /**
    * Find exact one or none hint of the given type
    *
    * @param hints search in this list
    * @return the found or none
    */
  protected def findIfaceTypeHint(
    hints: Seq[SerializerHint]
  ): Option[Int] = findOneHint[IfaceTypeHint](hints = hints).execAndLift(_.value)

  /**
    * Find exact one or none hint of the given type
    *
    * @param hints search in this list
    * @param classTag hint classtag
    * @tparam B hint type
    * @return the found or none
    */
  protected def findOneHint[B <: SerializerHint](
    hints: Seq[SerializerHint]
  )(
    implicit
    classTag: ClassTag[B]
  ): Option[B] = hints.collectFirst { case t: B => t }

  /**
    * Find size hint and pack into DigitalLength option
    */
  protected def findSizeHint (
    hints: Seq[SerializerHint]
  ): Option[DigitalLength] = {
    findOneHint[SizeHint](hints = hints) match {
      case Some(x: SizeInBitHint) => Some(BitLength(x.value))
      case Some(x: SizeInByteHint) => Some(ByteLength(x.value))

      case _ => None
    }
  }

  /**
    * Find size hint and pack into DigitalLength option
    */
  protected def lenToRead (
    hints: Seq[SerializerHint]
  ): Option[DigitalLength] = findSizeHint(hints = hints).orElse(defaultSize)

  /**
    * Find size hint and pack into DigitalLength option
    */
  protected def lenToWrite(
    hints: Seq[SerializerHint]
  ): Option[DigitalLength] = lenToRead(hints = hints)

  /**
    * Helper method to read a value (bit or bytes from iter)
    */
  protected def readForMe(
    iter: AnythingIterator,
    hints: Seq[SerializerHint]
  )(
    implicit
    byteOrder: ByteOrder,
    serializerContainer: SerializerContainer,
    version: Option[PatchLevelVersion]
  ): A = iter.read[A](lenToRead(hints = hints))(objectTypeInfo.classTag, objectTypeInfo.typeTag)

  /**
    * Helper method to read a value (bit or bytes from iter)
    */
  protected def writeForMe(
    value: A,
    hints: Seq[SerializerHint]
  )(
    implicit
    byteOrder: ByteOrder,
    serializerContainer: SerializerContainer,
    version: Option[PatchLevelVersion],
    classTag: ClassTag[A] = objectTypeInfo.classTag,
    typeTag: ru.TypeTag[A] = objectTypeInfo.typeTag
  ): Unit = {
    (findOneHint[StringBuilderHint](hints = hints), byteStringWriteOp, value) match {
      case (Some(x: BitStringBuilderHint), _, _) => x.builder.appendBits(lenToWrite(hints = hints).getOrElse(ByteLength(0)).toBits.toInt, value)(objectTypeInfo.classTag, objectTypeInfo.typeTag)
      case (Some(x: ByteStringBuilderHint), Some(writeOp), _) => writeOp(x.builder, value)
      case (Some(x: ByteStringBuilderHint), None, t: String) => x.builder.putBytes(encodeString(t, lenToWrite(hints = hints).execAndLift(_.toByte.toInt)))
      case (Some(x: ByteStringBuilderHint), None, _) => throw new SerializerRunException("want to write into a ByteStringBuilder, but no write op defined")

      case _ => throw new SerializerRunException("no ...StringBuilderHint given, don't know how to write data")
    }
  }

  /**
    * Raises an exception with object type as prefix
    */
  protected def throwException(msg: String) = throw new SerializerRunException(s"[${objectTypeInfo.typeTag.toString()}] $msg")

  /**
   * Encode a string
   *
   * @param in to encode
   * @param length is the length of the string to encode (missing bytes will be filled up with \0)
   *
   * @return encoded bytes
   */
  private def encodeString(
    in: String,
    length: Option[Int] = None
  )(
    implicit
    charset: Charset = StandardCharsets.ISO_8859_1
  ): Array[Byte] = {
    def getLength = length.getOrElse(in.length)

    in
      .take(getLength)
      .padTo(getLength, '\u0000')
      .getBytes(charset.displayName())
  }

}
