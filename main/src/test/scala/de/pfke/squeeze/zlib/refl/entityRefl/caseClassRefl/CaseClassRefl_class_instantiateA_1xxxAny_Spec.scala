package de.pfke.squeeze.zlib.refl.entityRefl.caseClassRefl

import de.pfke.squeeze.zlib.refl.entityRefl.caseClassRefl.mocks._
import de.pfke.squeeze.zlib.refl.entityRefl.CaseClassRefl
import org.scalatest.{Matchers, WordSpec}

class CaseClassRefl_class_instantiateA_1xxxAny_Spec
  extends WordSpec
    with Matchers {
  "testing case class: 'CaseClassMock0Args'" when {
    "passing the class" should {
      "should instantiate, when passing all args" in {
        CaseClassRefl[CaseClassMock0Args]
          .instantiate[CaseClassMock0Args]() should be(CaseClassMock0Args())
      }

      "should throw an exception, when to much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMock0Args]
            .instantiate[CaseClassMock0Args](456, false)
        )
      }
    }
  }

  "testing case class: 'CaseClassMock2Args_wMethods'" when {
    lazy val tto = CaseClassRefl[CaseClassMock2Args_wMethods]

    "call method 'apply'" should {
      "should instantiate, when passing all args" in {
        tto
          .instantiate[CaseClassMock2Args_wMethods]("sdvdvds", 123) should
          be(CaseClassMock2Args_wMethods("sdvdvds", 123))
      }

      "should throw an exception, when passing incompatible generic" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          tto
            .instantiate[String]("54656")
        )
      }

      "should throw an exception, when to few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          tto
            .instantiate[CaseClassMock2Args_wMethods]("54656")
        )
      }

      "should throw an exception, when passing wrong arg types" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          tto
            .instantiate[CaseClassMock2Args_wMethods]("54656", "sdvdvds")
        )
      }

      "should throw an exception, when to much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          tto
            .instantiate[CaseClassMock2Args_wMethods]("54656", 456, false)
        )
      }
    }
  }

  "testing case class: 'CaseClassMock2Args_woMethods'" when {
    lazy val tto = CaseClassRefl[CaseClassMock2Args_woMethods]

    "call method 'apply'" should {
      "should instantiate, when passing all args" in {
        tto
          .instantiate[CaseClassMock2Args_woMethods](
          false, 123
            .toChar
        ) should be(
          CaseClassMock2Args_woMethods(
            arg1 = false, 123
              .toChar
          )
        )
      }

      "should throw an exception, when passing incompatible generic" in {
        an[Exception] shouldBe thrownBy(
          tto
            .instantiate[String](
            false, 123
              .toChar
          )
        )
      }

      "should throw an exception, when to few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          tto
            .instantiate[CaseClassMock2Args_woMethods](false)
        )
      }

      "should throw an exception, when passing wrong arg types" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          tto
            .instantiate[CaseClassMock2Args_wMethods]("54656", "sdvdvds")
        )
      }

      "should throw an exception, when to much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          tto
            .instantiate[CaseClassMock2Args_woMethods](
            false, 123
              .toChar, false
          )
        )
      }
    }
  }

  "testing case class: 'CaseClassMock3Args_1Default'" when {
    lazy val tto = CaseClassRefl[CaseClassMock3Args_1Default]

    "call method 'apply'" should {
      "should instantiate, when passing all args" in {
        tto
          .instantiate[CaseClassMock3Args_1Default]("asd", false, 1233) should
          be(CaseClassMock3Args_1Default("asd", arg2 = false, 1233))
      }

      "should throw an exception, when passing incompatible generic" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          tto
            .instantiate[String](
            false, 123
              .toChar
          )
        )
      }

      "should instantiate, when to few args" in {
        tto
          .instantiate[CaseClassMock3Args_1Default]("asd", true) should
          be(CaseClassMock3Args_1Default("asd", arg2 = true))
      }

      "should throw an exception, when passing wrong arg types" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          tto
            .instantiate[CaseClassMock2Args_wMethods]("54656", "sdvdvds")
        )
      }

      "should throw an exception, when to much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          tto
            .instantiate[CaseClassMock2Args_woMethods](
            false, 123
              .toChar, false
          )
        )
      }
    }
  }

  "testing case class: 'CaseClassMockGeneric2Args'" when {
    "testing w/ type arg Byte" should {
      "should instantiate, when passing all args" in {
        CaseClassRefl[CaseClassMockGeneric2Args[Byte]]
          .instantiate[CaseClassMockGeneric2Args[Byte]]("asd", 113.toByte) should be(CaseClassMockGeneric2Args[Byte]("asd", 113))
      }

      "should throw an exception, when false type" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMockGeneric2Args[Byte]]
            .instantiate[CaseClassMock2Args_woMethods]("sdadsa", 123.toByte)
        )
      }

      "should throw an exception, when false arg type" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMockGeneric2Args[Byte]]
            .instantiate[CaseClassMockGeneric2Args[Byte]]("sdadsa", 123)
        )
      }
    }

    "testing w/ type arg Char" should {
      "should instantiate, when passing all args" in {
        CaseClassRefl[CaseClassMockGeneric2Args[Char]]
          .instantiate[CaseClassMockGeneric2Args[Char]]("asd", 113.toChar) should be(CaseClassMockGeneric2Args[Char]("asd", 113))
      }

      "should throw an exception, when false type" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMockGeneric2Args[Char]]
            .instantiate[CaseClassMock2Args_woMethods]("sdadsa", 123.toChar)
        )
      }
    }

    "testing w/ type arg Short" should {
      "should instantiate, when passing all args" in {
        CaseClassRefl[CaseClassMockGeneric2Args[Short]]
          .instantiate[CaseClassMockGeneric2Args[Short]]("asd", 11123.toShort) should be(CaseClassMockGeneric2Args[Short]("asd", 11123))
      }

      "should throw an exception, when false type" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMockGeneric2Args[Short]]
            .instantiate[CaseClassMock2Args_woMethods]("sdadsa", 123.toShort)
        )
      }
    }

    "testing w/ type arg Int" should {
      "should instantiate, when passing all args" in {
        println()

        CaseClassRefl[CaseClassMockGeneric2Args[Int]]
          .instantiate[CaseClassMockGeneric2Args[Int]]("asd", 1233) should be(CaseClassMockGeneric2Args[Int]("asd", 1233))
      }

      "should throw an exception, when false type" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMockGeneric2Args[Int]]
            .instantiate[CaseClassMock2Args_woMethods]("sdadsa", 123)
        )
      }
    }

    "testing w/ type arg Long" should {
      "should instantiate, when passing all args" in {
        CaseClassRefl[CaseClassMockGeneric2Args[Long]]
          .instantiate[CaseClassMockGeneric2Args[Long]]("asd", 1112312213l.toLong) should be(CaseClassMockGeneric2Args[Long]("asd", 1112312213l))
      }

      "should throw an exception, when false type" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMockGeneric2Args[Long]]
            .instantiate[CaseClassMock2Args_woMethods]("sdadsa", 123.toLong)
        )
      }
    }

    "testing w/ type arg String" should {
      "should instantiate, when passing all args" in {
        CaseClassRefl[CaseClassMockGeneric2Args[String]]
          .instantiate[CaseClassMockGeneric2Args[String]]("asd", "1112312213l") should be(CaseClassMockGeneric2Args[String]("asd", "1112312213l"))
      }

      "should throw an exception, when false type" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMockGeneric2Args[String]]
            .instantiate[CaseClassMock2Args_woMethods]("sdadsa", "123")
        )
      }
    }

    "testing w/ type arg Option[_]" should {
      "should instantiate, when passing all args" in {
        CaseClassRefl[CaseClassMockGeneric2Args[Option[String]]]
          .instantiate[CaseClassMockGeneric2Args[Option[String]]]("asd", Some("1112312213l")) should be(CaseClassMockGeneric2Args[Option[String]]("asd", Some("1112312213l")))
      }

      "should instantiate, when passing all args, last is none" in {
        CaseClassRefl[CaseClassMockGeneric2Args[Option[String]]]
          .instantiate[CaseClassMockGeneric2Args[Option[String]]]("asd", None) should be(CaseClassMockGeneric2Args[Option[String]]("asd", None))
      }

      "should throw an exception, when false type" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMockGeneric2Args[Option[String]]]
            .instantiate[CaseClassMock2Args_woMethods]("sdadsa", "123")
        )
      }
    }
  }

  "testing case class: 'CaseClassMock_wMap'" when {
    "testing" should {
      "should instantiate, when passing all args" in {
        CaseClassRefl[CaseClassMock_wMap]
          .instantiate[CaseClassMock_wMap](Map(1 -> "a", 2 -> "2"), 113, None) should be(CaseClassMock_wMap(Map(1 -> "a", 2 -> "2"), 113, None))
      }

      "should throw an exception, when false type" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMock_wMap]
            .instantiate[CaseClassMockGeneric2Args[Byte]](Map(1 -> "a", 2 -> "2"), 113, None)
        )
      }

      "should throw an exception, when to few args" in {
        CaseClassRefl[CaseClassMock_wMap]
          .instantiate[CaseClassMock_wMap](Map(1 -> "a", 2 -> "2"), 113) should be(CaseClassMock_wMap(Map(1 -> "a", 2 -> "2"), 113, Some(true)))
      }

      "should throw an exception, when passing wrong arg types" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMock_wMap]
            .instantiate[CaseClassMock_wMap]("54656", "sdvdvds", true)
        )
      }

      "should throw an exception, when to much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMock_wMap]
            .instantiate[CaseClassMock_wMap](Map(1 -> "a", 2 -> "2"), 113, None, "jkljkl")
        )
      }
    }
  }

  "testing case class: 'CaseClassMock_wOption'" when {
    "testing" should {
      "should instantiate, when passing all args" in {
        CaseClassRefl[CaseClassMock_wOption]
          .instantiate[CaseClassMock_wOption](Some("asd"), 113, None) should be(CaseClassMock_wOption(Some("asd"), 113, None))
      }

      "should throw an exception, when false type" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMock_wOption]
            .instantiate[CaseClassMockGeneric2Args[Byte]](Some("asd"), 113, None)
        )
      }

      "should throw an exception, when to few args" in {
        CaseClassRefl[CaseClassMock_wOption]
          .instantiate[CaseClassMock_wOption](Some("asd"), 113) should be(CaseClassMock_wOption(Some("asd"), 113, Some(true)))
      }

      "should throw an exception, when passing wrong arg types" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMock_wOption]
            .instantiate[CaseClassMock_wOption]("54656", "sdvdvds", true)
        )
      }

      "should throw an exception, when to much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMock_wOption]
            .instantiate[CaseClassMock_wOption](Some("asd"), 113, None, "jkljkl")
        )
      }
    }
  }

  "testing case class: 'CaseClassMock_wTrait'" when {
    "testing" should {
      "should instantiate, when passing all args" in {
        CaseClassRefl[CaseClassMock_wTrait]
          .instantiate[CaseClassMock_wTrait](Some("asd"), 113, None) should be(CaseClassMock_wTrait(Some("asd"), 113, None))
      }

      "should instantiate, when passing all args, cast as trait" in {
        println()

        CaseClassRefl[CaseClassMock_wTrait]
          .instantiate[trait_CaseClassMock_wTrait](Some("asd"), 113, None) should not be null
      }

      "should throw an exception, when false type" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMock_wTrait]
            .instantiate[CaseClassMockGeneric2Args[Byte]](Some("asd"), 113, None)
        )
      }

      "should throw an exception, when to few args" in {
        CaseClassRefl[CaseClassMock_wTrait]
          .instantiate[CaseClassMock_wTrait](Some("asd"), 113) should be(CaseClassMock_wTrait(Some("asd"), 113, Some(true)))
      }

      "should throw an exception, when passing wrong arg types" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMock_wTrait]
            .instantiate[CaseClassMock_wTrait]("54656", "sdvdvds", true)
        )
      }

      "should throw an exception, when to much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(
          CaseClassRefl[CaseClassMock_wTrait]
            .instantiate[CaseClassMock_wTrait](Some("asd"), 113, None, "jkljkl")
        )
      }
    }
  }
}
