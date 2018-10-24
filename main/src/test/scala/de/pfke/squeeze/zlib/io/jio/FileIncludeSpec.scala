/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Heiko Blobner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.pfke.squeeze.zlib.io.jio

import java.io.File

import org.scalatest.{Matchers, WordSpecLike}

class FileIncludeSpec
  extends WordSpecLike with Matchers {
  "calling method 'basename'" when {
    "using a file w/ extension" should {
      "return the correct name (w/ path)" in {
        new FileIncludes.FileFromFileOps(new File("path/file.ext")).basename should be ("file")
      }

      "return the correct name (w/o path)" in {
        new FileIncludes.FileFromFileOps(new File("file.ext")).basename should be ("file")
      }
    }

    "using a file w/o extension" should {
      "return the correct name (w/ path)" in {
        new FileIncludes.FileFromFileOps(new File("path/file")).basename should be ("file")
      }

      "return the correct name (w/o path)" in {
        new FileIncludes.FileFromFileOps(new File("file")).basename should be ("file")
      }
    }
  }

  "calling method 'extension'" when {
    "using a file w/ extension" should {
      "return the correct name (w/ path)" in {
        new FileIncludes.FileFromFileOps(new File("path/file.ext")).extension should be ("ext")
      }

      "return the correct name (w/o path)" in {
        new FileIncludes.FileFromFileOps(new File("file.ext")).extension should be ("ext")
      }
    }

    "using a file w/o extension" should {
      "return the correct name (w/ path)" in {
        new FileIncludes.FileFromFileOps(new File("path/file")).extension should be ("")
      }

      "return the correct name (w/o path)" in {
        new FileIncludes.FileFromFileOps(new File("file")).extension should be ("")
      }
    }
  }
}
