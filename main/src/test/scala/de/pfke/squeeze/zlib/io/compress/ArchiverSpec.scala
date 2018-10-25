package de.pfke.squeeze.zlib.io.compress

import java.nio.file.Files

import de.pfke.squeeze.testing.FileCleanAfterAll
import de.pfke.squeeze.zlib.data._
import de.pfke.squeeze.zlib.crypto.ChecksumCalculator
import de.pfke.squeeze.zlib.io.jnio._
import org.scalatest.{Matchers, WordSpecLike}

class ArchiverSpec
  extends WordSpecLike
    with Matchers
    with FileCleanAfterAll {
  "using method 'archive' with a list of src files" when {
    val dir = cleanFileAfter(Files.createTempDirectory("ArchiverSpec"))
    val srcDir = dir.resolve("src").createDirectory()
    val srcFiles = (0 to 10).map { i => srcDir.resolve(s"file$i").write(s"content file $i".asByteBuffer_iso8859_1) }.toList
    val targetBase = dir.resolve("target")

    "algo == tar" should {
      val target = Archiver.archive(
        src = srcFiles,
        target = targetBase,
        algorithm = ArchiveType.TAR
      )

      "archive should exist" in {
        target.exists should be(right = true)
      }

      "archive should end with '.tar" in {
        target.extension should be("tar")
      }

      "return a valid archive" in {
        val outDir = dir.resolve(s"$target.ent")

        val (out, fl) = Archiver.unArchive(target, Some(outDir))

        out should be(outDir)
        fl.size should be(srcFiles.length)
        fl.foreach { i =>
          withClue(s"testing file $i:") {
            srcFiles.find { ii => i.name.endsWith(ii.name) } match {
              case Some(x) => ChecksumCalculator.fromFile()(x.toFile) should be(ChecksumCalculator.fromFile()(i.toFile))
              case None => fail(s"$i not found in src file list")
            }
          }
        }
      }
    }
  }

  "using method 'archive' with a list of src dir" when {
    val dir = cleanFileAfter(Files.createTempDirectory("ArchiverSpec"))
    val srcDir = dir.resolve("src").createDirectory()
    val srcFiles = (0 to 10).map { i => srcDir.resolve(s"file$i").write(s"content file $i".asByteBuffer_iso8859_1) }.toList
    val targetBase = dir.resolve("target")

    "passing a dir" should {
      val target = Archiver.archive(
        src = List(srcDir),
        target = targetBase,
        algorithm = ArchiveType.ZIP
      )

      "archive should exist" in {
        target.exists should be (right = true)
      }

      "archive should end with '.zip" in {
        target.extension should be ("zip")
      }

      "return a valid archive" in {
        val outDir = dir.resolve(s"$target.ent")

        val (out, fl) = Archiver.unArchive(target, Some(outDir))

        out should be (outDir)
        fl.size should be (srcFiles.length)
        fl.foreach { i =>
          withClue(s"testing file $i:") {
            // suche mit endsWith, da der absolute Dateiname ein anderer ist
            srcFiles.find { ii => i.name.endsWith(ii.name) } match {
              case Some(x) => ChecksumCalculator.fromFile()(x.toFile) should be (ChecksumCalculator.fromFile()(i.toFile))
              case None    => fail(s"$i not found in src file list")
            }}}}
    }
  }

  "using method 'archive' with a root dir" when {
    val dir = cleanFileAfter(Files.createTempDirectory("ArchiverSpec"))
    val srcDir = dir.resolve("src").createDirectory()
    val srcFiles = (0 to 10).map { i => srcDir.resolve(s"file$i").write(s"content file $i".asByteBuffer_iso8859_1) }.toList
    val targetBase = dir.resolve("target")

    "passing a dir" should {
      val target = Archiver.archive(
        src = List(srcDir),
        target = targetBase,
        algorithm = ArchiveType.TAR,
        rootDir = Some(srcDir)
      )

      "archive should exist" in {
        target.exists should be (right = true)
      }

      "archive should end with '.tar" in {
        target.extension should be ("tar")
      }

      "return a valid archive" in {
        val outDir = dir.resolve(s"$target.ent")

        val (out, fl) = Archiver.unArchive(target, Some(outDir))

        out should be (outDir)
        fl.size should be (srcFiles.length)
        fl.foreach { i =>
          withClue("testing file $i") {
            srcFiles.find(_.filename == i.filename) match {
              case Some(x) => ChecksumCalculator.fromFile()(x.toFile) should be (ChecksumCalculator.fromFile()(i.toFile))
              case None    => fail(s"$i not found in src file list")
            }}}}
    }
  }
}
