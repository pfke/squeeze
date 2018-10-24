package de.pfke.squeeze.zlib.io.jnio.pathOps

import java.nio.file.{Files, Paths}

import de.pfke.squeeze.testing.FileCleanAfterAll
import de.pfke.squeeze.zlib.io.jnio.PathOps
import org.scalatest.{Matchers, WordSpec}

class PathOps_class_createTempFile_Spec
  extends WordSpec
    with Matchers
    with FileCleanAfterAll {
  "testing method 'createTempFile'" when {
    "passing empty string" should {
      val tto = cleanFileAfter(PathOps.createTempFile(""))

      "should return correct path" in {
        Files.exists(tto) should be (right = true)
      }

      "should be a File" in {
        Files.isRegularFile(tto) should be (right = true)
      }

      "should create into '/tmp/'" in {
        tto.getParent should be (Paths.get("/tmp"))
      }
    }

    "passing 'filename01'" should {
      val tto = cleanFileAfter(PathOps.createTempFile("filename01"))

      "should return correct path" in {
        Files.exists(tto) should be (right = true)
      }

      "should be a file" in {
        Files.isRegularFile(tto) should be (right = true)
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
      val tto = cleanFileAfter(PathOps.createTempFile("filename02.jpg"))

      "should return correct path" in {
        Files.exists(tto) should be (right = true)
      }

      "should be a file" in {
        Files.isRegularFile(tto) should be (right = true)
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
      val tto = cleanFileAfter(PathOps.createTempFile("/jklsdfjkld/jlk/filename03"))

      "should return correct path" in {
        Files.exists(tto) should be (right = true)
      }

      "should be a file" in {
        Files.isRegularFile(tto) should be (right = true)
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
