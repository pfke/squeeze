package de.pfke.squeeze.serialize.serializerBuilder

import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.data.collection.bitString.BitStringAlignment
import de.pfke.squeeze.zlib.refl.ClassFinder.ClassInfo
import de.pfke.squeeze.zlib.refl._
import de.pfke.squeeze.annots._
import de.pfke.squeeze.annots.classAnnots.{alignBitfieldsBy, fromVersion, typeForIface}
import de.pfke.squeeze.annots.fieldAnnots.{injectSize, withFixedSize}
import de.pfke.squeeze.zlib._
import de.pfke.squeeze.zlib.FieldDescrIncludes._
import de.pfke.squeeze.zlib.refl.{FieldDescr, FieldHelper, SizeOf}
import enumeratum.values._

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object BuildByReflection {
  def apply() = new BuildByReflection()
}

class BuildByReflection
  extends SerializerBuilder {
  implicit val classLoader: ClassLoader = getClass.getClassLoader

  /**
    * Build the serializer
    */
  def build[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): BuiltSerializer[A] = {
    def allFieldsAnnotatedWAlignBitfieldsBy_n_genErrorOutput(): String = {
      FieldHelper
        .getFields(tpe = typeTag.tpe)
        .filter(_.annos.hasAlignBitfieldsBy)
        .map(i => s"${i.name}: ${i.tpe}")
        .mkString(", ")
    }
    require(
      !FieldHelper
        .getFields(tpe = typeTag.tpe)
        .exists(_.annos.hasAlignBitfieldsBy),
      s"These field(s) '${allFieldsAnnotatedWAlignBitfieldsBy_n_genErrorOutput()}' are annotated w/ ${alignBitfieldsBy.getClass} and this is vorbidden"
    )

    val namespaceClassName_r = """(.*)\.(.*)$""".r

    val name = typeTag.tpe.toString match {
      case namespaceClassName_r(_, t) => t
      case t => t
    }

    val code =  s"""
                   |import de.pfke.squeeze.zlib.version.PatchLevelVersion
                   |import de.pfke.squeeze.zlib.data.collection.anythingString.AnythingIterator
                   |import de.pfke.squeeze.zlib.data.collection.bitString.{BitStringAlignment, BitStringBuilder}
                   |import de.pfke.squeeze.zlib.data.length.digital.{BitLength, ByteLength}
                   |import de.pfke.squeeze.zlib.refl._
                   |import de.pfke.squeeze.serialize.SerializerContainer
                   |import de.pfke.squeeze.serialize.serializerCompiler.CompiledSerializer
                   |import de.pfke.squeeze.serialize.serializerHints._
                   |import de.pfke.squeeze.zlib._
                   |import java.nio.ByteOrder
                   |import scala.reflect.runtime.{universe => ru}
                   |
                   |class ${name}Serializer
                   |  extends CompiledSerializer[${typeTag.tpe.toString}] {
                   |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[${typeTag.tpe.toString}]
                   |
                   |  ${makeCode().indent}
                   |}
                   |new ${name}Serializer()
                   |""".stripMargin

    BuiltSerializer (
      classTag = classTag,
      typeTag = typeTag,
      fullClassName = name,
      code = code
    )
  }

  /**
    * Generate reader and writer code.
    */
  private def makeCode[A]() (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): String = {
    typeTag.tpe match {
      case t if GeneralRefl.isComplex(t)    => makeCode_forComplex()
      case t if GeneralRefl.isEnumeratum(t) => makeCode_forEnumeratum()
      case t if GeneralRefl.isPrimitive(t)  => makeCode_forPrimitives()
      case t if GeneralRefl.isString(t)     => makeCode_forString()

      case _ => throw new SerializerBuildException(s"you want me to generate reader and writer code for $typeTag, but this type is neither complex, a primitove nor a string")
    }
  }

  /**
    * Make code for complex
    */
  private def makeCode_forComplex[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): String = {
    def make_readerCode_instantiation_paramLine(in: FieldDescr): String = s"${in.name} = ${in.name}"
    def make_readerCode_instantiation_forComplex (
      tpe: ru.Type
    ): String = {
      val code = FieldHelper.getFields(tpe)
        .map(make_readerCode_instantiation_paramLine)
        .mkString(",\n")

      s"""$tpe(
         |  ${code.indent}
         |)""".stripMargin
    }
    def make_readerCode_instantiation (
      tpe: ru.Type
    ): String = {
      tpe match {
        case t if GeneralRefl.isAbstract(t) => "// no its a trait"
        case t                              => make_readerCode_instantiation_forComplex(t)
      }
    }

    val tpe = typeTag.tpe
    s"""override def read(
       |  iter: AnythingIterator,
       |  hints: SerializerHint*
       |)(
       |  implicit
       |  byteOrder: ByteOrder,
       |  serializerContainer: SerializerContainer,
       |  version: Option[PatchLevelVersion]
       |): $tpe = {
       |  require(iter.len.toByte >= ${SizeOf.guesso[A]().toByte}, s"[${tpe.toString}] given input has only $${iter.len} left, but we need ${SizeOf.guesso[A]().toByte} byte")
       |  // read iter
       |  ${make_readerCode(tpe).indent}
       |  // create object
       |  ${make_readerCode_instantiation(tpe).indent}
       |}
       |
       |override def write(
       |  data: $tpe,
       |  hints: SerializerHint*
       |)(
       |  implicit
       |  byteOrder: ByteOrder,
       |  serializerContainer: SerializerContainer,
       |  version: Option[PatchLevelVersion]
       |): Unit = {
       |  require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[${tpe.toString}] given input has no ByteStringBuilderHint")
       |  ${make_writerCode(tpe).indent}
       |}""".stripMargin
  }

  /**
    * Make code for enumeratum
    */
  private def makeCode_forEnumeratum[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): String = {
    val tpe = typeTag.tpe
    lazy val tpeToGen = tpe match {
      case t if t <:< ru.typeOf[IntEnumEntry] => ru.typeOf[Int]
      case t if t <:< ru.typeOf[LongEnumEntry] => ru.typeOf[Long]
      case t if t <:< ru.typeOf[ShortEnumEntry] => ru.typeOf[Short]
      case t if t <:< ru.typeOf[StringEnumEntry] => ru.typeOf[String]
      case t if t <:< ru.typeOf[ByteEnumEntry] => ru.typeOf[Byte]
      case t if t <:< ru.typeOf[CharEnumEntry] => ru.typeOf[Char]

      case t => throw new SerializerBuildException(s"$t is no a supported enueratum type. Please use Byte, Char, Short, Long, Int or String.")
    }

    def make_readerCode_forEnumeratum (
      tpe: ru.Type
    ): String = {
      def toRichClass(in: ClassInfo[_]): RichClassMirror = RichClassMirror(in.classSymbol)

      // find all classes derived from
      val allSubClasses = ClassFinder
        .findAllClassesDerivedFrom(tpe, packageName = "")
        .filterNot(_.classSymbol.isAbstract)

      case class ObjectCtors(rcm: RichClassMirror, ctors: List[RichMethodRefl])
      val mappedWCtor = allSubClasses
        .map(toRichClass)
        .map(i => ObjectCtors(i, i.ctorMethodRefls))
      require(!mappedWCtor.exists(_.ctors.isEmpty), s"At least one enumeratum type (${typeTag.tpe}) has no ctor: '${mappedWCtor.filter(_.ctors.isEmpty).map(_.rcm.classMirror.symbol.typeSignature)}'")

      case class ObjectCtor(rcm: RichClassMirror, ctor: RichMethodRefl)
      case class InstancedObject(rcm: RichClassMirror, obj: ValueEnumEntry[_])
      case class InstanceValue(rcm: RichClassMirror, value: Any)
      val mappedWValue = mappedWCtor
        .map(i => ObjectCtor(i.rcm, i.ctors.head))
        .map(i => InstancedObject(i.rcm, i.ctor.apply[ValueEnumEntry[_]]()))
        .map(i => InstanceValue(i.rcm, i.obj.value))

      if (tpe <:< ru.typeOf[CharEnumEntry]) {
        s"""serializerContainer.read[$tpeToGen](iter, hints = hints:_*).toLong match {
           |  ${mappedWValue.map(i => s"case ${i.value.asInstanceOf[Char].toLong} => ${i.rcm.classMirror.symbol.fullName}()").mkString("\n").indent}
           |
           |  case t => throw new SerializerRunException(s"Given value $$t does not match to a defined enum of class '$tpe'")
           |}
           """.stripMargin
      } else {
        s"""serializerContainer.read[$tpeToGen](iter, hints = hints:_*) match {
           |  ${mappedWValue.map(i => s"case ${i.value} => ${i.rcm.classMirror.symbol.fullName}()").mkString("\n").indent}
           |
           |  case t => throw new SerializerRunException(s"Given value $$t does not match to a defined enum of class '$tpe'")
           |}
           """.stripMargin
      }
    }

    def make_writerCode_forEnumeratum (
      tpe: ru.Type
    ): String = generate_writerCall(tpeToGen, s"data.value")

    s"""override def read(
       |  iter: AnythingIterator,
       |  hints: SerializerHint*
       |)(
       |  implicit
       |  byteOrder: ByteOrder,
       |  serializerContainer: SerializerContainer,
       |  version: Option[PatchLevelVersion]
       |): $tpe = {
       |  require(iter.len.toByte >= ${SizeOf.guesso[A]().toByte}, s"[${tpe.toString}] given input has only $${iter.len} left, but we need ${SizeOf.guesso[A]().toByte} byte")
       |  // read iter
       |  ${make_readerCode_forEnumeratum(tpe).indent}
       |}
       |
       |override def write(
       |  data: $tpe,
       |  hints: SerializerHint*
       |)(
       |  implicit
       |  byteOrder: ByteOrder,
       |  serializerContainer: SerializerContainer,
       |  version: Option[PatchLevelVersion]
       |): Unit = {
       |  require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[${tpe.toString}] given input has no ByteStringBuilderHint")
       |  ${make_writerCode_forEnumeratum(tpe).indent}
       |}""".stripMargin
  }

  /**
    * Code generieren, für primitive Typen
    */
  private val writerOpCodeMap = Map(
    ru.typeOf[Boolean] -> "Byte(if (value) 1 else 0)",
    ru.typeOf[Byte]    -> "Byte(value)",
    ru.typeOf[Char]    -> "Short(value)",
    ru.typeOf[Double]  -> "Double(value)",
    ru.typeOf[Float]   -> "Float(value)",
    ru.typeOf[Int]     -> "Int(value)",
    ru.typeOf[Long]    -> "Long(value)",
    ru.typeOf[Short]   -> "Short(value)"
  )
  private def makeCode_forPrimitives[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]): String = {
    val writerOpCode = writerOpCodeMap.get(GeneralRefl.unifyType(typeTag.tpe)).matchToException(_.toString, new SerializerBuildException(s"method called with complex type: '${typeTag.tpe}'"))

    s"""override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.put$writerOpCode })
       |override protected def defaultSize = Some(ByteLength(${SizeOf.guesso[A]().toByte}))""".stripMargin
  }

  /**
    * Code generieren, für primitive Typen
    */
  private def makeCode_forString[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]): String = {
    s"""override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = None
       |override protected def defaultSize = None""".stripMargin
  }

//  /**
//    * Code generieren fuer Enums
//    */
//  private def genCodeForEnum[A]()(
//    implicit
//    classTag: ClassTag[A],
//    typeTag: ru.TypeTag[A]
//  ): String = {
//    def makeCaseLine(in: String) = s"case t if t == $in.id => $in"
//    def makeCases() = {
//      EnumRefl
//        .getChildrenNames()
//        .map(makeCaseLine)
//        .mkString("\n")
//    }
//
//    // Test auf Duplikate Ids: diese fallen sonst nur zur Compile-Zeit auf und geben dann keine eindeutige Fehlermeldung mehr.
//    EnumRefl
//      .hasDuplicateIds(typeTag.tpe) match {
//      case Some(x) => throw new SerializerBuildException(s"Error while generating a serializer for '${typeTag.tpe}'. Duplicate ids detected. (msg: '$x')")
//      case None => // do nothing
//    }
//
//    s"""override def read (
//       |  iter: AnythingIterator,
//       |  hints: SerializerHint*
//       |) (
//       |  implicit
//       |  byteOrder: ByteOrder,
//       |  serializerContainer: SerializerContainer,
//       |  version: Option[PatchLevelVersion]
//       |): ${typeTag.tpe} = {
//       |  serializerContainer
//       |    .read[Int](iter, hints = hints:_*) match {
//       |    ${makeCases().indentBy(4)}
//       |
//       |    case t => throwException(s"no enum type found with this value ($$t)")
//       |  }
//       |}
//       |
//       |override def write (
//       |  data: ${typeTag.tpe},
//       |  hints: SerializerHint*
//       |) (
//       |  implicit
//       |  byteOrder: ByteOrder,
//       |  serializerContainer: SerializerContainer,
//       |  version: Option[PatchLevelVersion]
//       |): Unit = {
//       |  serializerContainer.write[Int](data.id, hints = hints:_*)
//       |}""".stripMargin
//  }

  private def make_readerCode (
    tpe: ru.Type
  ): String = {
    tpe match {
      case t if GeneralRefl.isAbstract(t) => make_readerCode_forTraits(tpe = t)
      case t                              => make_readerCode_forComplex(t)
    }
  }

  private def make_readerCode_forComplex (
    tpe: ru.Type
  ): String = {
    // code for iter
    val groupedFields = FieldHelper.groupByBitfields(tpe = tpe)
    val funcCurried = make_readerCode_forComplex_groupedFields(upperType = tpe, groupedSubFields = groupedFields) _

    groupedFields
      .map(funcCurried)
      .mkString("\n")
  }

  private def make_readerCode_forComplex_groupedFields(
    upperType: ru.Type,
    groupedSubFields: List[List[FieldDescr]]
  )(
    fields: List[FieldDescr]
  ): String = {
    fields match {
      case t if t.hasAsBitfield => make_readerCode_forBitfields          (upperType = upperType, fields = fields, groupedSubFields = groupedSubFields)
      case _                    => make_readerCode_forComplex_noBitfields(upperType = upperType, fields = fields)
    }
  }

  /**
    * build iter code for string type
    */
  private def make_readerCode_forTraits(
    tpe: ru.Type
  ): String = {
    //---
    case class FoundAnnotsAsOpt(ifaceOpt: Option[typeForIface], versionOpt: Option[fromVersion])
    case class FoundAnnots(iface: typeForIface, versionOpt: Option[fromVersion])
    case class TypeToFoundAnnotsOpts(clazz: ClassInfo[_], foundAnnots: FoundAnnotsAsOpt)
    case class TypeToFoundAnnots(clazz: ClassInfo[_], foundAnnots: FoundAnnots)

    val allSubClasses: List[TypeToFoundAnnotsOpts] = ClassFinder
      .findAllClassesDerivedFrom(tpe, packageName = "")
      .filterNot(_.classSymbol.isAbstract)
      .map { i =>
        try {
          TypeToFoundAnnotsOpts(i, FoundAnnotsAsOpt(i.annotations.getTypeForIface, i.annotations.getAnnot[fromVersion]))
        } catch {
          case e: IllegalArgumentException => throw new SerializerBuildException(s"unable to reflect annotation for '${i.tpe}' (${e.getMessage})")
        }
      }

    def versionOptToString(
      annot: Option[fromVersion]
    ): String = annot.matchTo(i => s"Some(PatchLevelVersion(${i.major}, ${i.minor}, ${i.level}))", default = "None")

    def ifaceOptToString(
      annot: Option[typeForIface]
    ): String = annot.matchTo(i => s"Some(${i.ident})", default = "None")

    implicit def orderingTI[A <: TypeToFoundAnnotsOpts]: Ordering[A] = Ordering.by { i => s"${i.clazz.tpe.toString}${i.foundAnnots.versionOpt.getOrElse(fromVersion(0, 0))}" }

    val code = allSubClasses
      .sortBy { i => i }
      .map { i => s"case (${ifaceOptToString(i.foundAnnots.ifaceOpt)}, ${versionOptToString(i.foundAnnots.versionOpt)}) => serializerContainer.read[${i.clazz.tpe}](iter, hints = hints:_*)" }
      .mkString("\n")

    s"""(findIfaceTypeHint(hints = hints), version) match {
       |  ${code.indent}
       |
       |  case (t1, t2) => throw new SerializerRunException(s"trying to unsqueeze a trait ($tpe), but either iface type ($$t1) or version ($$t2) does not match")
       |}""".stripMargin
  }

  private def make_readerCode_forBitfields(
    upperType: ru.Type,
    fields: List[FieldDescr],
    groupedSubFields: List[List[FieldDescr]]
  ): String = {
    require(fields.nonEmpty, "given field list is empty")
    require(!fields.hasOneWithoutAsBitfield, "given chunk contains a field w/o 'asBitfield' annot")

    def buildName(field: FieldDescr) = s"${field.name}"
    def getBits(field: FieldDescr) = field.getAsBitfield.matchToException(_.bits, new SerializerBuildException(s"missing bitfield annotation on field ${field.name}"))

    val upperClassAnnots = upperType.typeSymbol.annotations
    val bitAlignment = upperClassAnnots.getAlignBitfieldsBy.matchTo(_.bits, default = 32)

    // code for fields
    val iterName = buildBitfieldBuilderIterName(fields, groupedSubFields, "BitIter")
    val codeForFields = fields
      .map { t => s"val ${buildName(t)} = serializerContainer.read[${t.tpe}]($iterName, hints = SizeInBitHint(value = ${getBits(t)}))" }
      .mkString("\n")

    // wir brauchen hier die reine bit size
    val bitFieldSize = SizeOf.guesso(fields = fields).toBits

    val prepareIterator = s"val $iterName = iter.iterator(bitAlignment = BitStringAlignment.${BitStringAlignment.enumFromWidth(bitAlignment)})"
    val codeForPadding = if ((bitFieldSize % bitAlignment) == 0) "" else s"$iterName.read[Long](BitLength(${bitAlignment - (bitFieldSize % bitAlignment)})) // read padding bits"
    val code = if (codeForPadding.nonEmpty) {
      s"""$codeForPadding
         |$codeForFields""".stripMargin
    } else codeForFields

    s"""$prepareIterator
       |$code""".stripMargin
  }

  private def make_readerCode_forComplex_noBitfields(
    upperType: ru.Type,
    fields: List[FieldDescr]
  ): String = {
    val allSubFields = FieldHelper.getFields(upperType)

    def makeLine(
      thisTpe: ru.Type,
      field: Option[FieldDescr] = None
    ): String = {
      val fieldName = field.matchTo(_.name, default = "")
      val typeHint = allSubFields.getInjectTypeAnnot(fieldName) match {
        case Some(x) if x._2.isEnum => Some(s"IfaceTypeHint(value = ${x._2.name}.id)")
        case Some(x)                => Some(s"IfaceTypeHint(value = ${x._2.name})")
        case None                   => None
      }

      // Laengen-info
      val foundInjectSizeAnnot = allSubFields.getInjectSizeAnnot(fieldName)
      val foundWithFixedSizeAnnot = field match {
        case Some(x) => x.getWithFixedSize.matchTo[Option[(withFixedSize, FieldDescr)]](t => Some((t, x)), None)
        case None => None
      }

      val sizeHint = foundInjectSizeAnnot orElse foundWithFixedSizeAnnot match {
        case Some((_: injectSize, field: FieldDescr)) => Some(s"SizeInByteHint(value = ${field.name.replaceAll(field.name, field.name)})")
        case Some((x: withFixedSize, _: FieldDescr))  => Some(s"SizeInByteHint(value = ${x.size})")
        case _                                        => None
      }

      val reallyHints = List(typeHint, sizeHint)
        .filter(_.nonEmpty)
        .map(_.get)
        .mkString(", ")

      val hints = if (reallyHints.isEmpty) "" else s", hints = $reallyHints"

      s"serializerContainer.read[$thisTpe](iter$hints)"
    }

    def makeLists(
      field: FieldDescr
    ): String = {
      val code = field.tpe.typeArgs.headOption.matchToException( i => makeLine(thisTpe = i), new SerializerBuildException(s"unknown sub type of this list: ${field.tpe}"))

      // annots auswerten
      val foundInjectCountAnnot = allSubFields.getInjectCountAnnot(field.name).matchToOption(_._2.name)
      val foundWithFixedSizeAnnot = field.getWithFixedSize.matchToOption(_.size.toString)

      val sizeOfOneListElement = field.tpe.typeArgs.foldLeft(0)((sum,i) => math.max(sum, SizeOf.guesso(tpe = i, annots = List.empty).toByte))
      require(sizeOfOneListElement > 0, s"could not determine size of $field")
      val count = foundInjectCountAnnot orElse foundWithFixedSizeAnnot orDefault s"iter.len.toByte / $sizeOfOneListElement" // at least use rest of the iterator

      s"(0 until $count).map { _ => $code }.to${if (field.isListType) "List" else "Array" }"
    }

    // code for iter
    fields
      .map {
        case t if t.isArray => s"val ${t.name} = ${makeLists(t)}"
        case t if t.isListType => s"val ${t.name} = ${makeLists(t)}"
        case t => s"val ${t.name} = ${makeLine(thisTpe = t.tpe, field = t)}"
      }
      .mkString("\n")
  }

  private def make_writerCode (
    tpe: ru.Type
  ): String = if (GeneralRefl.isAbstract(tpe)) make_writerCode_forTrait(tpe = tpe) else make_writerCode_forComplex(tpe = tpe)

  /**
    * Gen write code for complex type (and its fields)
    */
  private def make_writerCode_forComplex(
    tpe: ru.Type
  ): String = {
    val groupedFields = FieldHelper.groupByBitfields(tpe = tpe)
    val generateGroupedFields_fn = make_writerCode_forGroupedFields(upperClassType = tpe, allSubFields = FieldHelper.getFields(tpe), groupedSubFields = groupedFields, paramName = "data") _

    groupedFields
      .map(generateGroupedFields_fn)
      .mkString("\n")
  }

  /**
    * Build serializer object read method for complex sub arguments
    */
  private def make_writerCode_forTrait(
    tpe: ru.Type
  ): String = {
    //---
    implicit val classLoader: ClassLoader = ClassFinder.defaultClassLoader

    val derivedClasses = ClassFinder
      .findAllClassesDerivedFrom(tpe, packageName = "")

    val code = derivedClasses
      .sortBy(_.tpe.toString)
      .map { i => s"case t: ${i.tpe} => serializerContainer.write[${i.tpe}](t, hints = hints:_*)" }
      .mkString("\n")

    s"""val written = data match {
       |  ${code.indent}
       |
       |  case t => throw new SerializerRunException(s"trying to squeeze a trait ($tpe), but type is unknown")
       |}""".stripMargin
  }

  /**
    * Gen write code for this grouped fields (grouped by bitfields and none, w/o holes)
    */
  private def make_writerCode_forGroupedFields(
    upperClassType: ru.Type,
    allSubFields: List[FieldDescr],
    groupedSubFields: List[List[FieldDescr]],
    paramName: String
  )(
    fields: List[FieldDescr]
  ): String = {
    fields match {
      case t if t.hasAsBitfield => make_writerCode_forBitfields(upperClassType = upperClassType, nameOfTheDataObjWithinCode = paramName, fields = fields, groupedSubFields = groupedSubFields)
      case _                    => make_writerCode_forField    (upperClassType = upperClassType, nameOfTheDataObjWithinCode = paramName, fields = fields, allSubFields = allSubFields)
    }
  }

  /**
    * Build the name for this bitfield group.
    * E.g. a case class has 4 bitfield groups (either separated by no-bitfield fields or by alignment), we
    * need to build 4 unique names, used as iterator (for reading) or as builder (for writing).
    *
    * @param thisGroupedFields is a list w/ all field in this group
    * @param allGroupedFields is a list w/ all fields grouped by its bitfield membership
    * @return
    */
  private def buildBitfieldBuilderIterName(
    thisGroupedFields: List[FieldDescr],
    allGroupedFields: List[List[FieldDescr]],
    suffix: String
  ): String = {
    if (!thisGroupedFields.hasAsBitfield) "" else {
      val idxToString = Map(
        0 -> "st",
        1 -> "nd",
        2 -> "rd"
      )

      val idx = allGroupedFields.filter(_.hasAsBitfield).indexOf(thisGroupedFields)

      s"_${idx + 1}${ idxToString.get(idx).matchTo(i => i, "th")}$suffix"
    }
  }

  private def make_writerCode_forBitfields(
    upperClassType: ru.Type,
    nameOfTheDataObjWithinCode: String,
    fields: List[FieldDescr],
    groupedSubFields: List[List[FieldDescr]]
  ): String = {
    require(fields.nonEmpty, "given field list is empty")
    require(!fields.hasOneWithoutAsBitfield, "given chunk contains a field w/o 'asBitfield' annot")

    def buildName(field: FieldDescr) = s"${field.name}"
    def getBits(field: FieldDescr) = field.getAsBitfield.matchToException(_.bits, new SerializerBuildException(s"missing bitfield annotation on field ${field.name}"))

    val upperClassAnnots = upperClassType.typeSymbol.annotations
    val alignBitfieldsBy = upperClassAnnots.getAlignBitfieldsBy.matchTo(_.bits, default = 32)

    // code for fields
    val builderName = buildBitfieldBuilderIterName(fields, groupedSubFields, "BitBuilder")
    val codeForFields = fields
      .map { t => s"serializerContainer.write[${t.tpe}]($nameOfTheDataObjWithinCode.${buildName(t)}, hints = BitStringBuilderHint($builderName), SizeInBitHint(value = ${getBits(t)}))" }
      .mkString("\n")

    val prepareBuilder = s"val $builderName = BitStringBuilder.newBuilder(alignment = BitStringAlignment.${BitStringAlignment.enumFromWidth(alignBitfieldsBy)})"
    val finishBuilder = s"findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append($builderName.result())"

    s"""$prepareBuilder
       |$codeForFields
       |$finishBuilder""".stripMargin
  }

  /**
    * Build serializer object read method
    */
  private def make_writerCode_forField(
    upperClassType: ru.Type,
    nameOfTheDataObjWithinCode: String,
    fields: List[FieldDescr],
    allSubFields: List[FieldDescr]
  ): String = {
    lazy val generate_writerCode_forList_fn = generate_writerCode_forList(upperClassType, nameOfTheDataObjWithinCode, allSubFields) _
    lazy val generate_writerCode_forPrimitive_fn = generate_writerCode_forPrimitive(upperClassType, nameOfTheDataObjWithinCode, allSubFields) _
    lazy val generate_writerCode_forString_fn = generate_writerCode_forString(upperClassType, nameOfTheDataObjWithinCode, allSubFields) _

    // get all fields
    fields
      .map {
        case t if t.isArray || t.isListType => generate_writerCode_forList_fn(t)
        case t if t.isPrimitiveType => generate_writerCode_forPrimitive_fn(t)
        case t if t.isString => generate_writerCode_forString_fn(t)

        case t => generate_writerCall(tpe = t.tpe, valueToConvert = s"$nameOfTheDataObjWithinCode.${t.name}")
      }
      .mkString("\n")
  }

  private def generate_writerCall (
    tpe: ru.Type,
    valueToConvert: String,
    hints: Seq[String] = Seq.empty
  ): String = {
    val hintsCode = s"hints${if (hints.isEmpty) "" else s" ++ Seq(${hints.mkString(", ")})"}"

    s"serializerContainer.write[$tpe]($valueToConvert, hints = $hintsCode:_*)"
  }

  private def generate_writerCode_forList (
    upperClassType: ru.Type,
    nameOfTheDataObjWithinCode: String,
    allFields: List[FieldDescr]
  ) (
    field: FieldDescr
  ): String = {
    require(field.isArray || field.isListType, s"this func is only for array/lists ($field)")

    def readWithFixedSize: withFixedSize = field.getWithFixedSize.matchToException(i => i, new SerializerBuildException(s"$field ${withFixedSize.getClass} expected"))

    val code = field
      .tpe
      .typeArgs
      .headOption
      .matchToException ( i => generate_writerCall(i, "i"), new SerializerBuildException(s"unknown sub type of this list: $upperClassType"))

    val paddedList = if (field.hasAnnot[withFixedSize]) s"""require($nameOfTheDataObjWithinCode.${field.name}.size >= ${readWithFixedSize.size}, s"$nameOfTheDataObjWithinCode.${field.name} is annotated $readWithFixedSize, but passed list size is less")\n""" else ""
    val trimList = if (field.hasAnnot[withFixedSize]) s".take(${readWithFixedSize.size})" else ""

    s"$paddedList$nameOfTheDataObjWithinCode.${field.name}$trimList.foreach { i => $code }"
  }

  private def generate_writerCode_forPrimitive (
    upperClassType: ru.Type,
    nameOfTheDataObjWithinCode: String,
    allFields: List[FieldDescr]
  ) (
    field: FieldDescr
  ): String = {
    require(field.isPrimitiveType, s"this func is only for primitives ($field)")
    require(!field.hasWithFixedSize, s"field '${field.name}' is annotated w/ ${field.getAnnot[withFixedSize]}, but this is only allowed to string fields")

    def getWithFixedSize_fromInjectField(fieldName: Option[String]): Option[withFixedSize] = {
      fieldName match {
        case Some(x) => allFields.find(_.name == x).matchTo(_.getWithFixedSize, None)
        case None => None
      }
    }

    val valueToConvert = if (field.hasInjectSize) {
      (field.getInjectSize, getWithFixedSize_fromInjectField(field.getInjectSize.matchToOption(_.from))) match {
        case (Some(_), Some(_2))                => s"${_2.size.toString}"
        case (Some(_1), None) if _1.from == "." => s"SizeOf.guesso[$upperClassType](data).toByte.to${field.tpe}"
        case (Some(_1), None)                   => s"$nameOfTheDataObjWithinCode.${_1.from}.size.to${field.tpe}"

        case _                                  => throw new SerializerBuildException(s"should not happen. No injectSize annot found on field $field")
      }
    } else if(field.hasInjectType) {
      field.getInjectType match {
        case Some(x) => s"""serializerContainer.getIfaceType[${field.tpe}](in = data.${x.fromField}, clazz = ru.typeOf[$upperClassType], paramName = "${field.name}")"""
        case _ => throw new SerializerBuildException(s"should not happen. No injectType annot found on field $field")
      }
    } else {
      s"$nameOfTheDataObjWithinCode.${field.name}"
    }

    generate_writerCall(field.tpe, valueToConvert)
  }

  private def generate_writerCode_forString (
    upperClassType: ru.Type,
    nameOfTheDataObjWithinCode: String,
    allFields: List[FieldDescr]
  ) (
    field: FieldDescr
  ): String = {
    require(field.isString, s"this func is only for strings ($field)")

    def hasInjectLengthWFrom: Boolean = {
      allFields
        .filter(_.hasInjectSize)
        .map(_.getInjectSize)
        .filter(_.isDefined)
        .map(_.get)
        .exists(_.from == field.name)
    }
    def isLastField: Boolean = allFields.indexOf(field) == (allFields.size - 1)
    def readWithFixedSize: withFixedSize = field.getWithFixedSize.get

    val hints = if (field.hasWithFixedSize) {
      Seq(s"SizeInByteHint(value = ${readWithFixedSize.size})")
    } else if (isLastField || hasInjectLengthWFrom) {
      Seq.empty
    } else {
      throw new SerializerBuildException(s"found string field '${field.name}' w/o fixed size annotation which is not the last one")
    }

    generate_writerCall(ru.typeOf[String], s"$nameOfTheDataObjWithinCode.${field.name}", hints = hints)
  }
}
