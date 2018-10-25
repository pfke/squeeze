package de.pfke.squeeze.serialize.serializerBuilder.buildByReflection

import de.pfke.squeeze.testing.FileCleanAfterAll
import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.io._
import de.pfke.squeeze.zlib.refl.GeneralRefl
import de.pfke.squeeze.serialize.serializerBuilder.BuildByReflection
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}
import scala.sys.process._

abstract class BaseSpec
  extends WordSpec
    with Matchers
    with FileCleanAfterAll {
  /**
    * Testing the serializer for the given type
    */
  protected def checkThis[A](
    prefix: Option[String] = None,
    code: String
  )(implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Unit = {
    val tto = BuildByReflection().build[A]()
    val typeInfo = GeneralRefl.generateTypeInfo[A]
    val typeName = typeInfo.typeTag.tpe.toString

    s"testing ${prefix.getOrElse("")}$typeName type" should {
      "should return correct classTag" in {
        tto.classTag should be (typeInfo.classTag)
      }

      "should return correct typeTag" in {
        tto.typeTag should be (typeInfo.typeTag)
      }

      "should return correct code" in {
        val errorPatch = if (tto.code != code) {
          val resFile = cleanFileAfter("cmp_res".createTempFile())

          Seq(s"diff", "-du",
            s"${cleanFileAfter("cmp_filea".createTempFile()).write(tto.code.asByteBuffer_iso8859_1)}",
            s"${cleanFileAfter("cmp_fileb".createTempFile()).write(code.asByteBuffer_iso8859_1)}"
          ) #> resFile.toFile !

          resFile.read().asString_iso8859_1
        } else "no error detected"

        withClue(errorPatch) {
          tto.code should be(code)
        }
      }
    }
  }
}
