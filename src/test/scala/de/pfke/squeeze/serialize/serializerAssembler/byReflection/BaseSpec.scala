package de.pfke.squeeze.serialize.serializerAssembler.byReflection

import de.pfke.squeeze.core._
import de.pfke.squeeze.core.refl.generic.GenericOps
import de.pfke.squeeze.testing.CleanFilesAfterAll
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}
import scala.sys.process._

abstract class BaseSpec
  extends WordSpec
    with Matchers
    with CleanFilesAfterAll {
  /**
    * Testing the serializer for the given type
    */
  protected def checkThis[A](
    prefix: Option[String] = None,
    code: String
  ) (
    implicit
    classTag: ClassTag[A],
    typeTag: ru.TypeTag[A]
  ): Unit = {
    val tto = ByReflectionAssembler().assemble[A]()
    val typeInfo = GenericOps.getTypeInfo[A]
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
          val resFile = "cmp_res".createTempFile().cleanFileAfter

          Seq(s"diff", "-du",
            s"${ "cmp_filea".createTempFile().cleanFileAfter.write(tto.code.asByteBuffer_iso8859_1) }",
            s"${ "cmp_fileb".createTempFile().cleanFileAfter.write(code.asByteBuffer_iso8859_1) }"
          ) #> resFile.toFile !

          resFile.read.asString_iso8859_1
        } else "no error detected"

        withClue(errorPatch) {
          tto.code should be(code)
        }
      }
    }
  }
}
