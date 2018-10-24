package de.pfke.squeeze.zlib.io.jnio.implicitOps_fromString

import java.nio.file.{Files, Paths}

import de.pfke.squeeze.testing.FileCleanAfterAll
import de.pfke.squeeze.zlib.io.jnio.ImplicitOps_fromString
import org.scalatest.{Matchers, WordSpec}

class ImplicitOps_fromString_class_createTempDirectory_Spec
  extends WordSpec
    with Matchers
    with FileCleanAfterAll {
  "testing method 'name'" when {
    "passing empty string" should {
      val tto = cleanFileAfter(new ImplicitOps_fromString.ImplicitOps_fromString_class("").createTempDirectory())

      "should return correct path" in {
        Files.exists(tto) should be (right = true)
      }

      "should be a dir" in {
        Files.isDirectory(tto) should be (right = true)
      }

      "should create into '/tmp/'" in {
        tto.getParent should be (Paths.get("/tmp"))
      }
    }

    "passing 'filename01'" should {
      val tto = cleanFileAfter(new ImplicitOps_fromString.ImplicitOps_fromString_class("filename01").createTempDirectory())

      "should return correct path" in {
        Files.exists(tto) should be (right = true)
      }

      "should be a dir" in {
        Files.isDirectory(tto) should be (right = true)
      }

      "should return correct name" in {
        withClue(tto.toAbsolutePath) {
          tto.toAbsolutePath.toString.startsWith("/tmp/filename01_") should be(right = true)
        }
      }

      "should create into '/tmp/'" in {
        tto.getParent should be (Paths.get("/tmp"))
      }
    }

    "passing 'filename02.jpg'" should {
      val tto = cleanFileAfter(new ImplicitOps_fromString.ImplicitOps_fromString_class("filename02.jpg").createTempDirectory())

      "should return correct path" in {
        Files.exists(tto) should be (right = true)
      }

      "should be a dir" in {
        Files.isDirectory(tto) should be (right = true)
      }

      "should return correct name" in {
        withClue(tto.toAbsolutePath) {
          tto.toAbsolutePath.toString.startsWith("/tmp/filename02.jpg_") should be(right = true)
        }
      }

      "should create into '/tmp/'" in {
        tto.getParent should be (Paths.get("/tmp"))
      }
    }

    "passing '/jklsdfjkld/jlk/filename03'" should {
      val tto = cleanFileAfter(new ImplicitOps_fromString.ImplicitOps_fromString_class("/jklsdfjkld/jlk/filename03").createTempDirectory())

      "should return correct path" in {
        Files.exists(tto) should be (right = true)
      }

      "should be a dir" in {
        Files.isDirectory(tto) should be (right = true)
      }

      "should return correct name" in {
        withClue(tto.toAbsolutePath) {
          tto.toAbsolutePath.toString.startsWith("/tmp/jklsdfjkld/jlk/filename03_") should be(right = true)
        }
      }

      "should create into '/tmp/'" in {
        tto.getParent should be (Paths.get("/tmp/jklsdfjkld/jlk"))
      }
    }
  }
}
