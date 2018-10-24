package de.pfke.squeeze.zlib.io.jnio.pathOps

import de.pfke.squeeze.testing.FileCleanAfterAll
import de.pfke.squeeze.zlib.io.jnio.PathOps
import de.pfke.squeeze.zlib.io.jnio._
import org.scalatest.{Matchers, WordSpec}

class PathOps_class_flatFileTree_Spec
  extends WordSpec
    with Matchers
    with FileCleanAfterAll {
  "testing method 'flatFileTree'" when {
    "on empty dir" should {
      val path = cleanFileAfter(PathOps.createTempDirectory("emptyDir"))
      val tto = PathOps.flatFileTree(path)

      "should return 0 childs" in {
        tto.size should be (0)
      }
    }

    "on non-empty dir (files only)" should {
      val path = cleanFileAfter(PathOps.createTempDirectory("emptyDir"))

      val file01 = path.resolve("file01").createFile()
      val file02 = path.resolve("file02").createFile()
      val file03 = path.resolve("file03").createFile()
      val file04 = path.resolve("file04").createFile()
      val file05 = path.resolve("file05").createFile()

      val tto = PathOps.flatFileTree(path)

      "should return 5 childs" in {
        tto.size should be (5)
      }

      "file 1 should exist" in {
        tto.contains(file01) should be (right = true)
      }

      "file 2 should exist" in {
        tto.contains(file02) should be (right = true)
      }

      "file 3 should exist" in {
        tto.contains(file03) should be (right = true)
      }

      "file 4 should exist" in {
        tto.contains(file04) should be (right = true)
      }

      "file 5 should exist" in {
        tto.contains(file05) should be (right = true)
      }
    }
  }
}
