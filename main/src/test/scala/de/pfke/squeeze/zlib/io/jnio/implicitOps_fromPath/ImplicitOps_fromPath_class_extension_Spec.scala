package de.pfke.squeeze.zlib.io.jnio.implicitOps_fromPath

import java.nio.file.Paths

import de.pfke.squeeze.zlib.io.jnio.IncludeOps_fromPath
import org.scalatest.{Matchers, WordSpec}

class ImplicitOps_fromPath_class_extension_Spec
  extends WordSpec
    with Matchers {
    "testing method 'extension'" when {
      "passing empty string" should {
        val tto = new IncludeOps_fromPath.IncludeOps_fromPath_class(Paths.get(""))

        "should return correct" in {
          tto.extension should be("")
        }
      }

      "passing 'filename01'" should {
        val tto = new IncludeOps_fromPath.IncludeOps_fromPath_class(Paths.get("filename01"))

        "should return correct" in {
          tto.extension should be("")
        }
      }

      "passing 'filename02.jpg'" should {
        val tto = new IncludeOps_fromPath.IncludeOps_fromPath_class(Paths.get("filename02.jpg"))

        "should return correct" in {
          tto.extension should be("jpg")
        }
      }

      "passing '/jklsdfjkld/jlk/filename03'" should {
        val tto = new IncludeOps_fromPath.IncludeOps_fromPath_class(Paths.get("/jklsdfjkld/jlk/filename03"))

        "should return correct" in {
          tto.extension should be("")
        }
      }

      "passing '/jklsdfjkld/jlk/filename04.klj'" should {
        val tto = new IncludeOps_fromPath.IncludeOps_fromPath_class(Paths.get("/jklsdfjkld/jlk/filename04.klj"))

        "should return correct" in {
          tto.extension should be("klj")
        }
      }

      "passing 'jklsdfjkld/jlk/filename05.klj'" should {
        val tto = new IncludeOps_fromPath.IncludeOps_fromPath_class(Paths.get("jklsdfjkld/jlk/filename05.klj"))

        "should return correct" in {
          tto.extension should be("klj")
        }
      }
    }
  }
