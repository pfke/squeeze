package de.pfke.squeeze.serialize.serializerBuilder

import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.data.collection.bitString.BitStringAlignment
import de.pfke.squeeze.zlib.refl.ClassFinder.ClassInfo
import de.pfke.squeeze.zlib.refl._
import de.pfke.squeeze.annots._
import de.pfke.squeeze.annots.classAnnots.{fromVersion, typeForIface}
import de.pfke.squeeze.zlib._
import de.pfke.squeeze.zlib.FieldDescrIncludes._
import de.pfke.squeeze.zlib.data.length.digital.BitLength
import de.pfke.squeeze.zlib.refl.{FieldDescr, FieldHelper, SizeOf}

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object BuildByReflection {
  def apply() = new BuildByReflection()
}

class BuildByReflection
  extends SerializerBuilder {
  /**
    * Build the serializer
    */
  def build[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): BuiltSerializer[A] = {
    val namespaceClassName_r = """(.*)\.(.*)$""".r

    val (ns, name) = typeTag.tpe.toString match {
      case namespaceClassName_r(_1, _2) => (Some(s".${_1}"), _2)

      case t => (None, t)
    }

    val content =  s"""
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
                      |
                      |class ${name}Serializer
                      |  extends CompiledSerializer[${typeTag.tpe.toString}] {
                      |  override def objectTypeInfo = GeneralRefl.generateTypeInfo[${typeTag.tpe.toString}]
                      |
                      |  ${genCode().indent}
                      |}
                      |new ${name}Serializer()
                      |""".stripMargin

    BuiltSerializer(classTag = classTag, typeTag = typeTag, fullClassName = s"${if (ns.nonEmpty) s"$ns." else ""}$name", code = content)
  }

  /**
    * Code generieren, hier noch für alle Typen
    */
  private def genCode[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]): String = {
    typeTag.tpe match {
      case t if GeneralRefl.isComplexType(t) => genCodeForComplex()

      case t if GeneralRefl.isEnum(t) => genCodeForEnum()

      case t if GeneralRefl.isPrimitive(t) => genCodeForPrimitives()
      case t if GeneralRefl.isString(t)    => genCodeForString()

      case _ => "empty"
    }
  }

  /**
    * Code generieren fuer Enums
    */
  private def genCodeForComplex[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): String = {
    def makeInstanceCodeCodeLine(in: FieldDescr): String = s"${in.name} = ${in.name}"
    def makeInstanceCodeForComplex(): String = {
      val code = FieldHelper.getFields(typeTag.tpe)
        .map(makeInstanceCodeCodeLine)
        .mkString(",\n")

      s"""${typeTag.tpe}(
         |  ${code.indent}
         |)""".stripMargin
    }
    def makeInstanceCode(): String = {
      typeTag.tpe match {
        case t if GeneralRefl.isAbstract(t) => "// no its a trait"

        case _ => makeInstanceCodeForComplex()
      }
    }

    def makeIterCodeForComplex(): String = {
      // code for iter
      val groupedFields = FieldHelper.groupByBitfields(tpe = typeTag.tpe)
      val funcCurried = read_buildIterCode_forComplex(upperClassType = typeTag.tpe, allSubFields = FieldHelper.getFields(typeTag.tpe), groupedSubFields = groupedFields) _

      groupedFields
        .map(funcCurried)
        .mkString("\n")
    }
    def makeIterCode(): String = {
      typeTag.tpe match {
        case t if GeneralRefl.isAbstract(t) => read_buildIterCode_forTraits(tpe = t)
        case _                              => makeIterCodeForComplex()
      }
    }

    def makeWriteCode(): String = if (GeneralRefl.isAbstract(typeTag.tpe)) make_writerCode_forTrait(tpe = typeTag.tpe) else make_writerCode_forComplex(tpe = typeTag.tpe)

    s"""override def read(
       |  iter: AnythingIterator,
       |  hints: SerializerHint*
       |)(
       |  implicit
       |  byteOrder: ByteOrder,
       |  serializerContainer: SerializerContainer,
       |  version: Option[PatchLevelVersion]
       |): ${typeTag.tpe} = {
       |  require(iter.len.toByte >= ${SizeOf.guesso[A]().toByte}, s"[${typeTag.tpe.toString}] given input has only $${iter.len} left, but we need ${SizeOf.guesso[A]().toByte.toInt} byte")
       |  // read iter
       |  ${makeIterCode().indent}
       |  // create object
       |  ${makeInstanceCode().indent}
       |}
       |
       |override def write(
       |  data: ${typeTag.tpe},
       |  hints: SerializerHint*
       |)(
       |  implicit
       |  byteOrder: ByteOrder,
       |  serializerContainer: SerializerContainer,
       |  version: Option[PatchLevelVersion]
       |): Unit = {
       |  require(findOneHint[ByteStringBuilderHint](hints = hints).nonEmpty, s"[${typeTag.tpe.toString}] given input has no ByteStringBuilderHint")
       |  ${makeWriteCode().indent}
       |}""".stripMargin
  }

  /**
    * Code generieren fuer Enums
    */
  private def genCodeForEnum[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): String = {
    def makeCaseLine(in: String) = s"case t if t == $in.id => $in"
    def makeCases() = {
      EnumRefl
        .getChildrenNames()
        .map(makeCaseLine)
        .mkString("\n")
    }

    // Test auf Duplikate Ids: diese fallen sonst nur zur Compile-Zeit auf und geben dann keine eindeutige Fehlermeldung mehr.
    EnumRefl
      .hasDuplicateIds(typeTag.tpe) match {
      case Some(x) => throw new SerializerBuildException(s"Error while generating a serializer for '${typeTag.tpe}'. Duplicate ids detected. (msg: '$x')")
      case None => // do nothing
    }

    s"""override def read(
       |  iter: AnythingIterator,
       |  hints: SerializerHint*
       |)(
       |  implicit
       |  byteOrder: ByteOrder,
       |  serializerContainer: SerializerContainer,
       |  version: Option[PatchLevelVersion]
       |): ${typeTag.tpe} = {
       |  serializerContainer
       |    .read[Int](iter, hints = hints:_*) match {
       |    ${makeCases().indentBy(4)}
       |
       |    case t => throwException(s"no enum type found with this value ($$t)")
       |  }
       |}
       |
       |override def write(
       |  data: ${typeTag.tpe},
       |  hints: SerializerHint*
       |)(
       |  implicit
       |  byteOrder: ByteOrder,
       |  serializerContainer: SerializerContainer,
       |  version: Option[PatchLevelVersion]
       |): Unit = {
       |  serializerContainer.write[Int](data.id, hints = hints:_*)
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
  private def genCodeForPrimitives[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]): String = {
    val writerOpCode = writerOpCodeMap.get(GeneralRefl.unifyType(typeTag.tpe)).matchToException(_.toString, new SerializerBuildException(s"method called with complex type: '${typeTag.tpe}'"))

    s"""override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.put$writerOpCode })
       |override protected def defaultSize = Some(ByteLength(${SizeOf.guesso[A]().toByte.toInt}))""".stripMargin
  }

  /**
    * Code generieren, für primitive Typen
    */
  private def genCodeForString[A]()(
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]): String = {
    s"""override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = None
       |override protected def defaultSize = None""".stripMargin
  }

  /**
    * build iter code for string type
    */
  private def read_buildIterCode_forTraits(
    tpe: ru.Type
  ): String = {
    //---
    implicit val classLoader: ClassLoader = ClassFinder.defaultClassLoader

    case class FoundAnnotsAsOpt(ifaceOpt: Option[typeForIface], versionOpt: Option[fromVersion])
    case class FoundAnnots(iface: typeForIface, versionOpt: Option[fromVersion])
    case class TypeToFoundAnnotsOpts(clazz: ClassInfo[_], foundAnnots: FoundAnnotsAsOpt)
    case class TypeToFoundAnnots(clazz: ClassInfo[_], foundAnnots: FoundAnnots)

    val allSubClasses: List[TypeToFoundAnnotsOpts] = ClassFinder
      .findAllClassesDerivedFrom(tpe, packageName = "")
      .filterNot(_.classSymbol.isAbstract)
      .map { i =>
        try {
          TypeToFoundAnnotsOpts(i, FoundAnnotsAsOpt(i.annotations.getAnnot[typeForIface], i.annotations.getAnnot[fromVersion]))
        } catch {
          case e: IllegalArgumentException => throw new SerializerBuildException(s"unable to reflect annotation for '${i.tpe}' (${e.getMessage})")
        }
      }

    def versionOptToString(
      annot: Option[fromVersion]
    ): String = annot.matchTo(i => s"Some(PatchLevelVersion(${i.major}, ${i.minor}, ${i.level}))", default = "None")

    def ifaceOptToString(
      annot: Option[typeForIface]
    ): String = annot.matchTo(i => s"Some(${i.value})", default = "None")

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

  private def read_buildIterCode_forComplex(
    upperClassType: ru.Type,
    allSubFields: List[FieldDescr],
    groupedSubFields: List[List[FieldDescr]]
  )(
    fields: List[FieldDescr]
  ): String = {
    fields match {
      case t if t.hasAsBitfield => read_buildIterCode_forComplex_bitfields(upperClassType = upperClassType, allSubFields = allSubFields, groupedSubFields = groupedSubFields)(fields = fields)
      case _ => read_buildIterCode_forComplex_noBitfields(allSubFields = allSubFields, fields = fields, upperType = upperClassType)
    }
  }

  private def read_buildIterCode_forComplex_noBitfields(
    allSubFields: List[FieldDescr],
    fields: List[FieldDescr],
    upperType: ru.Type
  ): String = {
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

    def makeList(
      field: FieldDescr
    ): String = {
      val code = field.tpe.typeArgs.headOption.matchToException( i => makeLine(thisTpe = i), new SerializerBuildException(s"unknown sub type of this list: ${field.tpe}"))

      // annots auswerten
      val foundInjectCountAnnot = allSubFields.getInjectCountAnnot(field.name).matchToOption(_._2.name)
      val foundWithFixedSizeAnnot = field.getWithFixedSize.matchToOption(_.size.toString)

      val sizeOfOneListElement = SizeOf.guesso(field).toByte
      require(sizeOfOneListElement > 0, s"could not determine size of $field")
      val count = foundInjectCountAnnot orElse foundWithFixedSizeAnnot orDefault s"iter.len.toByte / $sizeOfOneListElement" // at least use rest of the iterator

      s"(0 until $count).map { _ => $code }.to${if (field.isListType) "List" else "Array" }"
    }

    // code for iter
    fields
      .map {
        case t if t.isArray => s"val ${t.name} = ${makeList(t)}"
        case t if t.isListType => s"val ${t.name} = ${makeList(t)}"
        case t => s"val ${t.name} = ${makeLine(thisTpe = t.tpe, field = t)}"
      }
      .mkString("\n")
  }

  private def read_buildIterCode_forComplex_bitfields(
    upperClassType: ru.Type,
    allSubFields: List[FieldDescr],
    groupedSubFields: List[List[FieldDescr]]
  )(
    fields: List[FieldDescr]
  ): String = {
    require(fields.nonEmpty, "given field list is empty")
    require(!fields.hasOneWithoutAsBitfield, "given chunk contains a field w/o 'asBitfield' annot")
//    require(upperClassType.typeSymbol.annotations.hasAlignBitfieldsBy, "upper class does not have 'alignBitfieldsBy' annotation")

    def buildName(field: FieldDescr) = s"${field.name}"
    def buildPrefix(field: FieldDescr) = s"${buildName(field)}_"

    val upperClassAnnots = upperClassType.typeSymbol.annotations
    val bitAlignment = upperClassAnnots.getAlignBitfieldsBy.matchTo(_.bits, BitLength.apply(32).toBits)

    val bitfieldIterName = if (!fields.hasAsBitfield) "" else {
          val idxToString = Map(
            0 -> "st",
            1 -> "nd",
            2 -> "rd"
          )

          val idx = groupedSubFields.filter(_.hasAsBitfield).indexOf(fields)

          s"_${idx + 1}${ idxToString.get(idx).matchTo(i => i, "th")}BitIter"

    }

    def groupBitfieldsByAlignment(): List[List[FieldDescr]] = {
      val r1 = new ArrayBuffer[ArrayBuffer[FieldDescr]]()
      r1 += new ArrayBuffer[FieldDescr]()
      var currentSize = 0
      fields
        .foreach { i =>
          val sizeInBits = i.getAsBitfield.matchToException(_.bits, new SerializerBuildException(s"field ${i.name} has no 'asBitfield' annotation"))

          currentSize += sizeInBits
          r1.last += i

          if (currentSize >= bitAlignment) {
            currentSize = currentSize - bitAlignment
            r1 += new ArrayBuffer[FieldDescr]()
          }
        }

      r1.map(_.toList).toList
    }

    val sepFields = groupBitfieldsByAlignment()
//    val bitAlignment = upperClassType.typeSymbol.annotations.getAlignBitfieldsBy.matchToException(_.bits, new SerializerBuildException(s"class has no 'alignBitfieldsBy' annotation"))

    // code for iter
    val iterCode = fields
      .map {
        case t if t.hasAsBitfield => s"val ${buildName(t)} = serializerContainer.read[${t.tpe}]($bitfieldIterName, hints = SizeInBitHint(value = ${t.getAsBitfield.matchToException(_.bits, new SerializerBuildException(s"missing bitfield annotation on field ${t.name}"))}))"
        case t => throw new SerializerBuildException(s"field '${t.name}' w/o 'asBitfield' annotation")
      }.mkString("\n")

//    val upperClassAnnots = upperClassType.typeSymbol.annotations
//    val alignBitfieldsBy = upperClassAnnots.getAlignBitfieldsBy.matchTo(_.bits, 1)
    // wir brauchen hier die reine bit size
    val bitFieldSize = SizeOf.guesso(fields = fields).toBits.toInt

    val prefix = s"val $bitfieldIterName = iter.iterator(bitAlignment = BitStringAlignment.${BitStringAlignment.enumFromWidth(bitAlignment)})"
    val padding = if ((bitFieldSize % bitAlignment) == 0) "" else s"$bitfieldIterName.read[Long](BitLength(${bitAlignment - (bitFieldSize % bitAlignment)})) // read padding bits"

    s"$prefix\n$padding\n$iterCode"
  }

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
    field: List[FieldDescr]
  ): String = {
    field match {
      case t if t.hasAsBitfield => make_writerCode_forBitfields(upperClassType = upperClassType, allSubFields = allSubFields, groupedSubFields = groupedSubFields, paramName = paramName, fields = field)
      case _                    => make_writerCode_forField(upperClassType = upperClassType, allSubFields = allSubFields, groupedSubFields = groupedSubFields, paramName = paramName, fields = field)
    }
  }

  private def make_writerCode_forBitfields(
    upperClassType: ru.Type,
    allSubFields: List[FieldDescr],
    groupedSubFields: List[List[FieldDescr]],
    paramName: String,
    fields: List[FieldDescr]
  ): String = {
    require(!fields.hasOneWithoutAsBitfield, "given chunk contains a field w/o 'asBitfield' annot")
//    require(upperClassType.typeSymbol.annotations.hasAlignBitfieldsBy, "upper class does not have 'alignBitfieldsBy' annotation")

    val upperClassAnnots = upperClassType.typeSymbol.annotations
    val alignBitfieldsBy = upperClassAnnots.getAlignBitfieldsBy.matchTo(_.bits, default = 32)

    val bitfieldIterName = if (!fields.hasAsBitfield) "" else {
//      val upperClassAnnots = upperClassType.typeSymbol.annotations

//      alignBitfieldsBy match {
//        case Some(_) =>
          val idxToString = Map(
            0 -> "st",
            1 -> "nd",
            2 -> "rd"
          )

          val idx = groupedSubFields.filter(_.hasAsBitfield).indexOf(fields)

          s"_${idx + 1}${ idxToString.get(idx).matchTo(i => i, "th")}BitBuilder"

//        case None => ""
//      }
    }

    // get all fields
    val code = fields.map {
      case t if t.hasAsBitfield => s"serializerContainer.write[${t.tpe}]($paramName.${t.name}, hints = BitStringBuilderHint($bitfieldIterName), SizeInBitHint(value = ${t.getAsBitfield.matchToException(_.bits, new SerializerBuildException(s"missing bitfield annotation on field ${t.name}"))}))"
      case t => throw new SerializerBuildException(s"field '${t.name}' w/o 'asBitfield' annotation")
    }.mkString("\n")

//    val bitFieldSize = SizeOf.guesso(fields = fields).toBits

    val prefix = s"val $bitfieldIterName = BitStringBuilder.newBuilder(alignment = BitStringAlignment.${BitStringAlignment.enumFromWidth(alignBitfieldsBy)})\n"
    val suffix = s"findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append($bitfieldIterName.result())"
    s"$prefix$code\n$suffix"
  }

  /**
    * Build serializer object read method
    */
  private def make_writerCode_forField(
    upperClassType: ru.Type,
    allSubFields: List[FieldDescr],
    groupedSubFields: List[List[FieldDescr]],
    paramName: String,
    fields: List[FieldDescr]
  ): String = {
    lazy val generate_writerCode_forList_fn = generate_writerCode_forList(upperClassType, paramName, allSubFields) _
    lazy val generate_writerCode_forPrimitive_fn = generate_writerCode_forPrimitive(upperClassType, paramName, allSubFields) _
    lazy val generate_writerCode_forString_fn = generate_writerCode_forString(upperClassType, paramName, allSubFields) _

    // get all fields
    fields
      .map {
        case t if t.isArray || t.isListType => generate_writerCode_forList_fn(t)
        case t if t.isPrimitiveType => generate_writerCode_forPrimitive_fn(t)
        case t if t.isString => generate_writerCode_forString_fn(t)

        case t => generate_writerCall(tpe = t.tpe, valueToConvert = s"$paramName.${t.name}")
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
    nameOfTheDataObjWithinWriterCode: String,
    allFields: List[FieldDescr]
  ) (
    field: FieldDescr
  ): String = {
    require(field.isArray || field.isListType, s"this func is only for primitives ($field)")

    def readWithFixedSize: withFixedSize = field.getWithFixedSize.matchToException(i => i, new SerializerBuildException(s"$field ${withFixedSize.getClass} expected"))

    val code = field
      .tpe
      .typeArgs
      .headOption
      .matchToException ( i => generate_writerCall(i, "i"), new SerializerBuildException(s"unknown sub type of this list: $upperClassType"))

    val paddedList = if (field.hasAnnot[withFixedSize]) s"""require($nameOfTheDataObjWithinWriterCode.${field.name}.size >= ${readWithFixedSize.size}, s"$nameOfTheDataObjWithinWriterCode.${field.name} is annotated $readWithFixedSize, but passed list size is less")\n""" else ""
    val trimList = if (field.hasAnnot[withFixedSize]) s".take(${readWithFixedSize.size})" else ""

    s"$paddedList$nameOfTheDataObjWithinWriterCode.${field.name}$trimList.foreach { i => $code }"
  }

  private def generate_writerCode_forPrimitive (
    upperClassType: ru.Type,
    nameOfTheDataObjWithinWriterCode: String,
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
        case (Some(_1), Some(_2))               => s"${_2.size.toString}"
        case (Some(_1), None) if _1.from == "." => s"SizeOf.guesso[$upperClassType](data).toByte.to${field.tpe}"
        case (Some(_1), None)                   => s"$nameOfTheDataObjWithinWriterCode.${_1.from}.size.to${field.tpe}"

        case _                                  => throw new SerializerBuildException(s"should not happen. No injectSize annot found on field $field")
      }
    } else {
      s"$nameOfTheDataObjWithinWriterCode.${field.name}"
    }

    generate_writerCall(field.tpe, valueToConvert)
  }

  private def generate_writerCode_forString (
    upperClassType: ru.Type,
    nameOfTheDataObjWithinWriterCode: String,
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

    generate_writerCall(ru.typeOf[String], s"$nameOfTheDataObjWithinWriterCode.${field.name}", hints = hints)
  }
}
