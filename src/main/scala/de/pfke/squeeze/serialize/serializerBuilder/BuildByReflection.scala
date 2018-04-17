package de.pfke.squeeze.serialize.serializerBuilder

import de.pfke.squeeze.SizeOf
import de.pfke.squeeze.annots.AnnotationHelperIncludes._
import de.pfke.squeeze.annots.classAnnots.{fromVersion, typeForIface}
import de.pfke.squeeze.annots.{injectCount, injectLength, injectTotalLength, withFixedLength}
import de.pfke.squeeze.core.data._
import de.pfke.squeeze.core.data.collection.BitStringAlignment
import de.pfke.squeeze.core.refl.custom.{FieldDescr, FieldHelper}
import de.pfke.squeeze.core.refl.generic.{ClassInfo, ClassOps, EnumOps, GenericOps}
import de.pfke.squeeze.core.refl.generic.GenericOpsIncludes._
import de.pfke.squeeze.zlib.FieldDescrIncludes._
import de.pfke.squeeze.zlib.SerializerBuildException

import scala.annotation.StaticAnnotation
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
                      |import de.pfke.grind.core.version.PatchLevelVersion
                      |import de.pfke.grind.data.collection.anythingString.AnythingIterator
                      |import de.pfke.grind.data.collection.bitString.{BitStringAlignment, BitStringBuilder}
                      |import de.pfke.grind.data.length.digital.{BitLength, ByteLength}
                      |import de.pfke.grind.refl.core.GeneralRefl
                      |import de.pfke.grind.refl.squeeze.serialize.{Serializer, SerializerContainer}
                      |import de.pfke.grind.refl.squeeze.serialize.serializerHints._
                      |import de.pfke.grind.refl.squeeze.zlib._
                      |import java.nio.ByteOrder
                      |
                      |class ${name}Serializer
                      |  extends Serializer[${typeTag.tpe.toString}] {
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
      case t if GenericOps.isComplex(t) => genCodeForComplex()

      case t if GenericOps.isEnum(t) => genCodeForEnum()

      case t if GenericOps.isPrimitive(t) => genCodeForPrimitives()
      case t if GenericOps.isString(t) => genCodeForString()

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
        case t if GenericOps.isAbstract(t) => "// no its a trait"

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
        case t if GenericOps.isAbstract(t) => read_buildIterCode_forTraits(tpe = t)
        case _                 => makeIterCodeForComplex()
      }
    }

    def makeWriteCode(): String = if (GenericOps.isAbstract(typeTag.tpe)) makeWriteCodeForTrait(tpe = typeTag.tpe) else makeWriteCodeForComplex(tpe = typeTag.tpe)

    s"""override def read(
       |  iter: AnythingIterator,
       |  hints: SerializerHint*
       |)(
       |  implicit
       |  byteOrder: ByteOrder,
       |  serializerContainer: SerializerContainer,
       |  version: Option[PatchLevelVersion]
       |): ${typeTag.tpe} = {
       |  require(iter.len.toByte >= ${SizeOf.guess[A]()}, s"[${typeTag.tpe.toString}] given input has only $${iter.len} bytes left, but we need ${SizeOf.guess[A]()} byte")
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
      EnumOps
        .getChildrenNames()
        .map(makeCaseLine)
        .mkString("\n")
    }

    // Test auf Duplikate Ids: diese fallen sonst nur zur Compile-Zeit auf und geben dann keine eindeutige Fehlermeldung mehr.
    EnumOps
      .getDuplicateId(typeTag.tpe) match {
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
    val writerOpCode = writerOpCodeMap.get(GenericOps.toScalaType(typeTag.tpe)).execOrThrow(_.toString, new SerializerBuildException(s"method called with complex type: '${typeTag.tpe}'"))

    s"""override protected def byteStringWriteOp(implicit byteOrder: ByteOrder) = Some({ (bsb,value) => bsb.put$writerOpCode })
       |override protected def defaultSize = Some(ByteLength(${SizeOf.guess[A]}))""".stripMargin
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
    implicit val classLoader = ClassOps.defaultClassLoader

    case class FoundAnnotsAsOpt(ifaceOpt: Option[typeForIface], versionOpt: Option[fromVersion])
    case class FoundAnnots(iface: typeForIface, versionOpt: Option[fromVersion])
    case class TypeToFoundAnnotsOpts(clazz: ClassInfo[_], foundAnnots: FoundAnnotsAsOpt)
    case class TypeToFoundAnnots(clazz: ClassInfo[_], foundAnnots: FoundAnnots)

    val allSubClasses: List[TypeToFoundAnnotsOpts] = ClassOps
      .findClasses_byInheritance(tpe, packageName = "")
      .filterNot(_.classSymbol.isAbstract)
      .map { i =>
        try {
          TypeToFoundAnnotsOpts(i, FoundAnnotsAsOpt(i.annotations.getAnnot[typeForIface], i.annotations.getAnnot[fromVersion]))
        } catch {
          case e: IllegalArgumentException => throw new SerializerBuildException(s"unable to reflect annotation for '${i.tpe}' (${e.getMessage})")
        }
      }

    // any class defined w/o 'typeForIface' annot? -> exception
    allSubClasses
      .find(_.foundAnnots.ifaceOpt.isEmpty) match {
      case Some(x) => throw new SerializerBuildException(s"build iter code for iface '$tpe', but at least one derived class (${x.clazz.tpe}) has no 'typeForIface' annotation")
      case None =>
    }

    def getVersionMatcher(
      annot: Option[fromVersion]
    ): String = {
      annot match {
        case Some(x) => s"Some(PatchLevelVersion(${x.major}, ${x.minor}, ${x.level}))"
        case None => "None"
      }
    }

    implicit def orderingTI[A <: TypeToFoundAnnotsOpts]: Ordering[A] = Ordering.by { i => s"${i.clazz.tpe.toString}${i.foundAnnots.versionOpt.getOrElse(fromVersion(0, 0, 0))}" }

    val code = allSubClasses
      .filterNot(_.foundAnnots.ifaceOpt.isEmpty)
      .sortBy { i => i }
      .map { i => TypeToFoundAnnots(i.clazz, FoundAnnots(i.foundAnnots.ifaceOpt.get, i.foundAnnots.versionOpt)) }
      .map { i => s"case (Some(${i.foundAnnots.iface.value}), ${getVersionMatcher(i.foundAnnots.versionOpt)}) => serializerContainer.read[${i.clazz.tpe}](iter, hints = hints:_*)" }
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
      case t => read_buildIterCode_forComplex_noBitfields(allSubFields = allSubFields, fields = fields, upperType = upperClassType)
    }
  }

  private def read_buildIterCode_forComplex_noBitfields(
    allSubFields: List[FieldDescr],
    fields: List[FieldDescr],
    upperType: ru.Type
  ): String = {
    def makeLine(
      thisTpe: ru.Type,
      fieldName: String = ""
    ): String = {
      val subFields = FieldHelper.getFields(upperType)

      val typeHint = subFields.getInjectTypeAnnot(fieldName) match {
        case Some(x) if x._2.tpe.isEnum => Some(s"IfaceTypeHint(value = ${x._2.name}.id)")
        case Some(x)                    => Some(s"IfaceTypeHint(value = ${x._2.name})")
        case None                       => None
      }

      // Laengen-info
      val foundInjectLengthAnnot = fields.getInjectLengthAnnot(fieldName)
      val foundWithFixedLengthAnnot = fields.getWithFixedLengthAnnot

      val lengthHint = foundInjectLengthAnnot orElse foundWithFixedLengthAnnot match {
          case Some((x: injectLength, field: FieldDescr))    => Some(s"SizeInByteHint(value = ${field.name.replaceAll(field.name, field.name)})")
          case Some((x: withFixedLength, field: FieldDescr)) => Some(s"SizeInByteHint(value = ${x.bytes})")
          case _ => None
      }


      val reallyHints = List(typeHint, lengthHint)
        .filter(_.nonEmpty)
        .map(_.get)
        .mkString(", ")

      val hints = if (reallyHints.isEmpty) "" else s", hints = $reallyHints"

      s"serializerContainer.read[$thisTpe](iter$hints)"
    }

    def makeList(
      field: FieldDescr
    ): String = {
      val code = field.tpe.typeArgs.headOption.execOrThrow( i => makeLine(thisTpe = i), new SerializerBuildException(s"unknown sub type of this list: ${field.tpe}"))

      // annots auswerten
      val foundInjectCountAnnot = allSubFields.getInjectCountAnnot(field.name).execAndLift(_._2.name)
      val foundWithFixedCountAnnot: Option[String] = field.getWithFixedCount.execAndLift(_.count.toString)

      val count = foundInjectCountAnnot orElse foundWithFixedCountAnnot orElse "'unknown count'"

      s"(0 until $count).map { _ => $code }.toList"
    }

    // code for iter
    fields
      .map {
        case t if t.tpe.isList => s"val ${t.name} = ${makeList(t)}"
        case t => s"val ${t.name} = ${makeLine(thisTpe = t.tpe, fieldName = t.name)}"
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
    require(upperClassType.typeSymbol.annotations.hasAlignBitfieldsBy, "upper class does not have 'alignBitfieldsBy' annotation")

    def buildName(field: FieldDescr) = s"${field.name}"
    def buildPrefix(field: FieldDescr) = s"${buildName(field)}_"

    val bitfieldIterName = if (!fields.hasAsBitfield) "" else {
      val upperClassAnnots = upperClassType.typeSymbol.annotations

      upperClassAnnots
        .getAlignBitfieldsBy match {
        case Some(x) =>
          val idxToString = Map(
            0 -> "st",
            1 -> "nd",
            2 -> "rd"
          )

          val idx = groupedSubFields.filter(_.hasAsBitfield).indexOf(fields)

          s"_${idx + 1}${ idxToString.get(idx).execOrDefault(i => i, "th")}BitIter"

        case None => ""
      }
    }

    def groupBitfieldsByAlignment(): List[List[FieldDescr]] = {
      val upperClassAnnots = upperClassType.typeSymbol.annotations

      val alignment = upperClassAnnots.getAlignBitfieldsBy.execOrThrow(_.bits, new SerializerBuildException(s"class has no 'alignBitfieldsBy' annotation"))

      val r1 = new ArrayBuffer[ArrayBuffer[FieldDescr]]()
      r1 += new ArrayBuffer[FieldDescr]()
      var currentSize = 0
      fields
        .foreach { i =>
          val sizeInBits = i.getAsBitfield.execOrThrow(_.bits, new SerializerBuildException(s"field ${i.name} has no 'asBitfield' annotation"))

          currentSize += sizeInBits
          r1.last += i

          if (currentSize >= alignment) {
            currentSize = currentSize - alignment
            r1 += new ArrayBuffer[FieldDescr]()
          }
      }

      r1.map(_.toList).toList
    }

    val sepFields = groupBitfieldsByAlignment()
    val bitAlignment = upperClassType.typeSymbol.annotations.getAlignBitfieldsBy.execOrThrow(_.bits, new SerializerBuildException(s"class has no 'alignBitfieldsBy' annotation"))

    // code for iter
    val iterCode = fields
      .map {
        case t if t.hasAsBitfield => s"val ${buildName(t)} = serializerContainer.read[${t.tpe}]($bitfieldIterName, hints = SizeInBitHint(value = ${t.getAsBitfield.execOrThrow(_.bits, new SerializerBuildException(s"missing bitfield annotation on field ${t.name}"))}))"
        case t => throw new SerializerBuildException(s"field '${t.name}' w/o 'asBitfield' annotation")
      }.mkString("\n")

    val upperClassAnnots = upperClassType.typeSymbol.annotations
    val alignBitfieldsBy = upperClassAnnots.getAlignBitfieldsBy.execOrDefault(_.bits, 1)
    // wir brauchen hier die reine bit size
    val bitFieldSize = SizeOf.guessBitsize(fields = fields, upperClassAnnots = List.empty)

    val prefix = s"val $bitfieldIterName = iter.iterator(bitAlignment = BitStringAlignment.${BitStringAlignment.enumFromWidth(alignBitfieldsBy)})\n"
    val padding = if ((bitFieldSize % bitAlignment) == 0) "" else s"$bitfieldIterName.read[Long](BitLength(${bitAlignment - (bitFieldSize % bitAlignment)})) // read padding bits\n"

    s"$prefix$padding$iterCode"
  }

  /**
    * Build serializer object write method
    */
  private def write_buildCode_callSerializer(
    tpe: ru.Type,
    paramName: String = "data",
    field: Option[FieldDescr] = None
  ): String = {
    // injectType annot?
    def buildValueFromField(
      f: FieldDescr
    ) = {
      f.annos
        .getInjectType match {
        case Some(x) => s"serializerContainer.getIfaceType(${paramName.replaceAll(f.name, x.fromField)}).to$tpe"
        case None => paramName
      }
    }

    GenericOps.toScalaType(tpe) match {
      case _ => s"serializerContainer.write[$tpe](${field.execOrDefault(buildValueFromField, paramName)}, hints = hints:_*)"
    }
  }

  /**
    * Gen write code for complex type (and its fields)
    */
  private def makeWriteCodeForComplex(
    tpe: ru.Type
  ): String = {
    val groupedFields = FieldHelper.groupByBitfields(tpe = tpe)
    val funcCurried = makeWriteCodeForThisGroupedFields(upperClassType = tpe, allSubFields = FieldHelper.getFields(tpe), groupedSubFields = groupedFields, paramName = "data") _

    groupedFields
      .map(funcCurried)
      .mkString("\n")
  }

  /**
    * Gen write code for this grouped fields (grouped by bitfields and none, w/o holes)
    */
  private def makeWriteCodeForThisGroupedFields(
    upperClassType: ru.Type,
    allSubFields: List[FieldDescr],
    groupedSubFields: List[List[FieldDescr]],
    paramName: String
  )(
    field: List[FieldDescr]
  ): String = {
    field match {
      case t if t.hasAsBitfield => write_buildCode_forComplex_bitfields(upperClassType = upperClassType, allSubFields = allSubFields, groupedSubFields = groupedSubFields, paramName = paramName, fields = field)
      case t                    => write_buildCode_forComplex_noBitfields(upperClassType = upperClassType, allSubFields = allSubFields, groupedSubFields = groupedSubFields, paramName = paramName, fields = field)
    }
  }

  /**
    * Build serializer object read method
    */
  private def write_buildCode_forComplex_noBitfields(
    upperClassType: ru.Type,
    allSubFields: List[FieldDescr],
    groupedSubFields: List[List[FieldDescr]],
    paramName: String,
    fields: List[FieldDescr]
  ): String = {
    def readAnnot[A <: StaticAnnotation] (field: FieldDescr)(
      implicit
      classTag: ClassTag[A],
      typeTag: ru.TypeTag[A]
    ): A = field.getAnnot[A] execOrThrow ( i => i, new SerializerBuildException(s"$typeTag annot expected"))
    def readInjectCount (field: FieldDescr): injectCount = readAnnot[injectCount](field)
    def readInjectLength (field: FieldDescr): injectLength = readAnnot[injectLength](field)
    def readWithFixedLength (field: FieldDescr): withFixedLength = readAnnot[withFixedLength](field)

    // get all fields
    fields
      .map {
        case t if t.tpe.isList =>
          val code = t.tpe.typeArgs.headOption.execOrThrow(
            i => write_buildCode_callSerializer(tpe = i, paramName = "i"),
            new SerializerBuildException(s"unknown sub type of this list: $upperClassType")
          )
          s"$paramName.${t.name}.foreach { i => $code }"

        case t if t.tpe.isString && t.hasAnnot[withFixedLength] => s"serializerContainer.write[String]($paramName.${t.name}, hints = hints ++ Seq(SizeInByteHint(value = ${readWithFixedLength(t).bytes})):_*)"

        case t if t.hasAnnot[injectCount] => write_buildCode_callSerializer(tpe = t.tpe, paramName = s"$paramName.${readInjectCount(t).fromField}.size.to${t.tpe}").trim
        case t if t.hasAnnot[injectLength] => write_buildCode_callSerializer(tpe = t.tpe, paramName = s"$paramName.${readInjectLength(t).fromField}.length.to${t.tpe}").trim
        case t if t.hasAnnot[injectTotalLength] => write_buildCode_callSerializer(tpe = t.tpe, paramName = s"${SizeOf.guess(upperClassType)}").trim // TODO: statisch oder dynamisc?

        case t => write_buildCode_callSerializer(tpe = t.tpe, paramName = s"$paramName.${t.name}", field = Some(t)).trim
      }
      .mkString("\n")
  }

  /**
    * Build serializer object read method
    */
  private def write_buildCode_forComplex_bitfields(
    upperClassType: ru.Type,
    allSubFields: List[FieldDescr],
    groupedSubFields: List[List[FieldDescr]],
    paramName: String,
    fields: List[FieldDescr]
  ): String = {
    require(!fields.hasOneWithoutAsBitfield, "given chunk contains a field w/o 'asBitfield' annot")
    require(upperClassType.typeSymbol.annotations.hasAlignBitfieldsBy, "upper class does not have 'alignBitfieldsBy' annotation")

    val bitfieldIterName = if (!fields.hasAsBitfield) "" else {
      val upperClassAnnots = upperClassType.typeSymbol.annotations

      upperClassAnnots
        .getAlignBitfieldsBy match {
        case Some(x) =>
          val idxToString = Map(
            0 -> "st",
            1 -> "nd",
            2 -> "rd"
          )

          val idx = groupedSubFields.filter(_.hasAsBitfield).indexOf(fields)

          s"_${idx + 1}${ idxToString.get(idx).execOrDefault(i => i, "th")}BitBuilder"

        case None => ""
      }
    }

    // get all fields
    val code = fields.map {
      case t if t.hasAsBitfield => s"serializerContainer.write[${t.tpe}]($paramName.${t.name}, hints = BitStringBuilderHint($bitfieldIterName), SizeInBitHint(value = ${t.getAsBitfield.execOrThrow(_.bits, new SerializerBuildException(s"missing bitfield annotation on field ${t.name}"))}))"
      case t => throw new SerializerBuildException(s"field '${t.name}' w/o 'asBitfield' annotation")
    }.mkString("\n")

    val upperClassAnnots = upperClassType.typeSymbol.annotations
    val alignBitfieldsBy = upperClassAnnots.getAlignBitfieldsBy.execOrDefault(_.bits, 1)
    val bitFieldSize = SizeOf.guess(fields = fields, upperClassAnnots = List.empty) * 8

    val prefix = s"val $bitfieldIterName = BitStringBuilder.newBuilder(alignment = BitStringAlignment.${BitStringAlignment.enumFromWidth(alignBitfieldsBy)})\n"
    val suffix = s"findOneHint[ByteStringBuilderHint](hints = hints).get.builder.append($bitfieldIterName.result())"
    s"$prefix$code\n$suffix"
  }

  /**
    * Build serializer object read method for complex sub arguments
    */
  private def makeWriteCodeForTrait(
    tpe: ru.Type
  ): String = {
    //---
    implicit val classLoader = ClassOps.defaultClassLoader

    val derivedClasses = ClassOps
      .findClasses_byInheritance(tpe, packageName = "")
      .map { i => i -> i.annotations.getAnnot[typeForIface] }

    // any class defined w/o 'typeForIface' annot? -> exception
    derivedClasses
      .find(_._2.isEmpty) match {
      case Some(x) => throw new SerializerBuildException(s"build write code for iface '$tpe', but at least one derived class (${x._1.tpe}) has no 'typeForIface' annotation")
      case None =>
    }

    val code = derivedClasses
      .filterNot(_._2.isEmpty)
      .sortBy(_._1.tpe.toString)
      .map { i => i._1 -> i._2.get }
      .map { i => s"case t: ${i._1.tpe} => serializerContainer.write[${i._1.tpe}](t, hints = hints:_*)" }
      .mkString("\n")

    s"""val written = data match {
       |  ${code.indent}
       |
       |  case t => throw new SerializerRunException(s"trying to squeeze a trait ($tpe), but type is unknown")
       |}""".stripMargin//.replaceAll("\n", "\n    ").replaceAll("    \n", "\n")
  }
}
