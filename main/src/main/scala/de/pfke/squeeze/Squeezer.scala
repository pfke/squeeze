package de.pfke.squeeze

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.annots.AnnotationHelper.getAnnot
import de.pfke.squeeze.zlib.version.PatchLevelVersion
import de.pfke.squeeze.zlib.data.collection.anythingString.AnythingIterator
import de.pfke.squeeze.zlib.data.collection.bitString.BitStringAlignment
import de.pfke.squeeze.zlib.refl.{AnnotationRefl, GeneralRefl}
import de.pfke.squeeze.annots._
import de.pfke.squeeze.annots.classAnnots.typeForIface
import de.pfke.squeeze.serialize.serializerHints.{ByteStringBuilderHint, SerializerHint}
import de.pfke.squeeze.serialize.{RichSerializer, SerializerContainer}
import de.pfke.squeeze.zlib.SerializerRunException
import de.pfke.squeeze.zlib.refl.entityRefl.CaseClassRefl

import scala.annotation.StaticAnnotation
import scala.collection.mutable
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}
import scala.util.Try

object Squeezer {
  /**
    * Create w/o PatchLevelVersion
    */
  def apply () (
    implicit
    byteOrder: ByteOrder
  ): Squeezer = new Squeezer(initialFromVersion = None)

  /**
    * Create with parsed PatchLevelVersion
    */
  def apply (
    version: String
  ) (
    implicit
    byteOrder: ByteOrder
  ): Squeezer = apply(PatchLevelVersion(version))

  /**
    * Create with PatchLevelVersion
    */
  def apply (
    version: PatchLevelVersion
  ) (
    implicit
    byteOrder: ByteOrder
  ): Squeezer = new Squeezer(initialFromVersion = Some(version))
}

class Squeezer (
  initialFromVersion: Option[PatchLevelVersion]
) (
  implicit
  byteOrder: ByteOrder
) extends SerializerContainer {
  private implicit val _serialzierContainerToUse: Squeezer = this

  // fields
  private val _cachedSerializers = new mutable.HashMap[GeneralRefl.TypeInfo[_], RichSerializer[_]]()

  /**
    * Return the iface type of the given data (if its class is described with an annotation)
    */
  override def getIfaceType[A] (
    in: AnyRef,
    clazz: ru.Type,
    paramName: String
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A],
  ): A = {
//    val r1 = GeneralRefl
//      .typeOf(in)
//      .typeSymbol
//      .annotations
//    val r1_1 = r1.head
////    val r1_2 = GeneralRefl.typeOf(r1_1)
//    val r1_3 = r1_1.tree
//    val r1_4 = r1_3.tpe
//    require(r1_4 <:< ru.typeOf[typeForIface[_]], s"passed object is not annotated w/ ${ru.typeOf[typeForIface[_]]}")
////    val r1_5 = r1_4.typeSymbol.typeSignature
//    val r1_6 = r1_4.typeArgs
//    val r1_7 = r1_6.head
//    val r1_8 = GeneralRefl.unifyType(r1_7) =:= GeneralRefl.unifyType(typeTag.tpe)
//
//    val a1 = ru.typeOf[typeForIface[typeTag.type]]
//    implicit val classLoader = in.getClass.getClassLoader
//    val a2 = getAnnot[typeForIface[_]](r1)
//
//    val v1 = r1_3.children
//    val v2 = v1(1)
//
//    val annotType = r1_4
//    val args = r1_3.children.tail                                                                 // retrieve the args. These are returned as a list of Tree.
//      .collect{                                                                           // convert list of Tree to list of argument values
//      case ru.Literal(ru.Constant(m)) => m
//    }
//
//
//    val k1 = new CaseClassRefl(annotType.typeSymbol.asClass, dynamicTypeArgs = r1_6)
//    val k2 = k1.instantiate[typeForIface[Byte]](args:_*)
//
//    val jj1 = GeneralRefl.typeOf(in)
//    val jj2 = jj1.typeSymbol
//    val jj3 = jj2.typeSignature
//    val jj4 = jj3.typeArgs
//    val jj5 = jj2.asClass.typeParams
//
//    val k3 = Try(k1.instantiate[typeForIface[_]](args:_*))
//
//    ru.typeOf[Integer]
//
//    val b1 = ru.typeOf[Boolean]
//    val b2 = ru.typeOf[Int]
//    val b3 = b1 <:< b2
//    val b4 = b2 <:< b1
//
//    val m0 = classOf[Any]
//    val m1 = classOf[Byte]
//    val m9_1 = m0.isAssignableFrom(m1)
//    val m9_2 = m1.isAssignableFrom(m0)
//    val m2 = ru.typeOf[Any]
//    val m2_1 = m2.typeSymbol
//    val m2_2 = m2_1.asClass
//    val j1 = GeneralRefl.generateTypeInfo[Any].classTag.runtimeClass
//
//
//
//    val t1 = Try(GeneralRefl
//      .typeOf(in)
//      .typeSymbol
//      .annotations
//      .getTypeForIface match {
//      case Some(x) if GeneralRefl.toScalaType(GeneralRefl.typeOf(x.ident)) =:= GeneralRefl.toScalaType(typeTag.tpe) => x.ident.asInstanceOf[A]
//      case Some(x) => throw new SerializerRunException(s"${ru.typeOf[typeForIface[_]]}.ident has type ${GeneralRefl.typeOf(x.ident)}, but serializer wanted my to convert to $typeTag. Please change field type ($clazz.$paramName)")
//      case None => throw new SerializerRunException(s"could not find ${ru.typeOf[typeForIface[_]]} annot for ${GeneralRefl.typeOf(in)}")
//    })
    GeneralRefl
      .typeOf(in)
      .typeSymbol
      .annotations
      .getTypeForIface match {
      case Some(x) if GeneralRefl.toScalaType(GeneralRefl.typeOf(x.ident)) =:= GeneralRefl.toScalaType(typeTag.tpe) => x.ident.asInstanceOf[A]
      case Some(x) => throw new SerializerRunException(s"${ru.typeOf[typeForIface]}.ident has type ${GeneralRefl.typeOf(x)}, but serializer wanted my to convert to $typeTag. Please change field type ($clazz.$paramName)")
      case None => throw new SerializerRunException(s"could not find ${ru.typeOf[typeForIface]} annot for ${GeneralRefl.typeOf(in)}")
    }
  }

  /**
    * Read from given input and deserialize to an object A
    *
    * @param hints how to deserialize (e.g. read bits or bytes, pass an iface type)
    * @param byteOrder BigEndian or LittleEndian
    * @param version only imported for ifaces
    * @return deserialzed object
    */
  def read[A](
    iter: AnythingIterator,
    hints: SerializerHint*
  )(implicit
    byteOrder: ByteOrder,
    version: Option[PatchLevelVersion] = initialFromVersion,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): A = getSerializer[A]().read(iter = iter, hints = hints:_*)

  /**
    * Serialize the input
    *
    * @param data is the input data
    * @param hints how to deserialize (e.g. read bits or bytes, pass an iface type)
    * @param byteOrder BigEndian or LittleEndian
    * @param version only important for ifaces
    */
  def write[A](
    data: A,
    hints: SerializerHint*
  )(implicit
    byteOrder: ByteOrder,
    version: Option[PatchLevelVersion] = initialFromVersion,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Unit = getSerializer[A]().write(data = data, hints = hints:_*)

  /**
    * Read from given input and deserialize to an object A
    *
    * @param byteOrder BigEndian or LittleEndian
    * @param version only imported for ifaces
    * @return deserialzed object
    */
  def deSerialize[A](
    in: ByteString
  )(implicit
    byteOrder: ByteOrder,
    version: Option[PatchLevelVersion] = initialFromVersion,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): A = read[A](iter = AnythingIterator(in, bitAlignment = BitStringAlignment._32Bit))

  /**
    * Serialize the input
    *
    * @param in is the input data
    * @param byteOrder BigEndian or LittleEndian
    * @param version only imported for ifaces
    */
  def toBinary[A](
    in: A
  )(implicit
    byteOrder: ByteOrder,
    version: Option[PatchLevelVersion] = initialFromVersion,
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): ByteString = {
    val result = ByteString.newBuilder

    write[A](data = in, hints = ByteStringBuilderHint(result))

    result.result()
  }

  /**
    * Return a serializer for the given type
    */
  private def getSerializer[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): RichSerializer[A] = {
    _cachedSerializers
      .getOrElseUpdate(
        GeneralRefl.TypeInfo(classTag = classTag, typeTag = typeTag),
        new RichSerializer[A]()
      ).asInstanceOf[RichSerializer[A]]
  }
}
