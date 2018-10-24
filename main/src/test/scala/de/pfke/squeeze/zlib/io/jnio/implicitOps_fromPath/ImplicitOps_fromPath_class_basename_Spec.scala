package de.pfke.squeeze.zlib.io.jnio.implicitOps_fromPath

import java.nio.file.Paths

import de.pfke.squeeze.zlib.io.jnio.IncludeOps_fromPath
import org.scalatest.{Matchers, WordSpec}

class ImplicitOps_fromPath_class_basename_Spec
  extends WordSpec
  with Matchers {
  "testing method 'basename'" when {
    "passing empty string" should {
      val tto = new IncludeOps_fromPath.IncludeOps_fromPath_class(Paths.get(""))

      "should return correct" in {
        tto.basename should be("")
      }
    }

    "passing 'filename01'" should {
      val tto = new IncludeOps_fromPath.IncludeOps_fromPath_class(Paths.get("filename01"))

      "should return correct" in {
        tto.basename should be("filename01")
      }
    }

    "passing 'filename02.jpg'" should {
      val tto = new IncludeOps_fromPath.IncludeOps_fromPath_class(Paths.get("filename02.jpg"))

      "should return correct" in {
        tto.basename should be("filename02")
      }
    }

    "passing '/jklsdfjkld/jlk/filename03'" should {
      val tto = new IncludeOps_fromPath.IncludeOps_fromPath_class(Paths.get("/jklsdfjkld/jlk/filename03"))

      "should return correct" in {
        tto.basename should be("filename03")
      }
    }

    "passing '/jklsdfjkld/jlk/filename04.klj'" should {
      val tto = new IncludeOps_fromPath.IncludeOps_fromPath_class(Paths.get("/jklsdfjkld/jlk/filename04.klj"))

      "should return correct" in {
        tto.basename should be("filename04")
      }
    }

    "passing 'jklsdfjkld/jlk/filename05.klj'" should {
      val tto = new IncludeOps_fromPath.IncludeOps_fromPath_class(Paths.get("jklsdfjkld/jlk/filename05.klj"))

      "should return correct" in {
        tto.basename should be("filename05")
      }
    }
  }
}
