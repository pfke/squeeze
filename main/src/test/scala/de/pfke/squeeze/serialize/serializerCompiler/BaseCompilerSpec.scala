package de.pfke.squeeze.serialize.serializerCompiler

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.zlib.version.PatchLevelVersion
import de.pfke.squeeze.zlib.data.collection.anythingString.AnythingIterator
import de.pfke.squeeze.zlib.data.collection.bitString.{BitStringAlignment, BitStringBuilder}
import de.pfke.squeeze.zlib.data.collection.bitString.BitStringAlignment.BitStringAlignment
import de.pfke.squeeze.zlib.data.length.digital.BitLength
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.serialize.{SerializerContainer, WrappedSerializer}
import de.pfke.squeeze.serialize.serializerBuilder.BuildByReflection
import de.pfke.squeeze.serialize.serializerHints.{BitStringBuilderHint, ByteStringBuilderHint, SizeInBitHint}
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

trait BaseCompilerSpec
  extends WordSpec
    with Matchers
{
  protected def createTTO[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): WrappedSerializer[A] = SerializerCompiler.compile[A](BuildByReflection().build())

  /**
    * Read bits
    */
  protected def readBitString[A](
    serializer: WrappedSerializer[A],
    bits: Int,
    value: Long
  )(
    implicit
    byteOrder: ByteOrder,
    alignment: BitStringAlignment = BitStringAlignment._32Bit,
    serializerContainer: Option[SerializerContainer] = None,
    version: Option[PatchLevelVersion] = None
  ): A = {
    implicit val realSerializerContainer: SerializerContainer = serializerContainer.getOrElse(Squeezer())

    val bsb = BitStringBuilder.newBuilder(alignment = alignment)

    bsb.appendBits(bits, value)

    // der bitstring wird von unten geschrieben, aber von oben gelesen -> deshalb erstmal das padding weglesen
    val iter = AnythingIterator(bsb.result(), bitAlignment = alignment)
    iter.read[Long](Some(BitLength(alignment.id - bits))) //

    serializer.compiledSerializer.read(iter, SizeInBitHint(bits))
  }

  /**
    * Read Bytes
    */
  protected def readByteString[A](
    serializer: WrappedSerializer[A],
    data: Int*
  )(
    implicit
    byteOrder: ByteOrder,
    serializerContainer: Option[SerializerContainer] = None,
    version: Option[PatchLevelVersion] = None,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): A = {
    implicit val realSerializerContainer: SerializerContainer = serializerContainer.getOrElse(Squeezer())

    serializer.compiledSerializer.read(AnythingIterator(ByteString(data:_*), bitAlignment = BitStringAlignment._32Bit))
  }

  /**
    * Write bites
    */
  protected def writeBitString[A](
    serializer: WrappedSerializer[A],
    bits: Int,
    value: A
  )(
    implicit
    byteOrder: ByteOrder,
    alignment: BitStringAlignment = BitStringAlignment._32Bit,
    serializerContainer: Option[SerializerContainer] = None,
    version: Option[PatchLevelVersion] = None,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): ByteString = {
    implicit val realSerializerContainer: SerializerContainer = serializerContainer.getOrElse(Squeezer())

    val bsb = BitStringBuilder.newBuilder(alignment = alignment)

    serializer.compiledSerializer.write(value, BitStringBuilderHint(bsb), SizeInBitHint(bits))

    bsb.result()
  }

  /**
    * Write bytes
    */
  protected def writeByteString[A](
    serializer: WrappedSerializer[A],
    data: A
  )(
    implicit
    byteOrder: ByteOrder,
    serializerContainer: Option[SerializerContainer] = None,
    version: Option[PatchLevelVersion] = None,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): ByteString = {
    implicit val realSerializerContainer: SerializerContainer = serializerContainer.getOrElse(Squeezer())

    val bsb = ByteString.newBuilder

    serializer.compiledSerializer.write(data, ByteStringBuilderHint(bsb))

    bsb.result()
  }
}
