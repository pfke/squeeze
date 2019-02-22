package de.pfke.squeeze.zlib.refl.richMethodRefl

import de.pfke.squeeze.zlib.refl.richMethodRefl.mocks._
import de.pfke.squeeze.zlib.refl.{RichMethodRefl, RichRuntimeMirror}

import scala.reflect.runtime.{universe => ru}

class RichMethodRefl_class_apply_1xxxAny_Spec
  extends RichMethodReflBaseSpec {
  "testing case class: 'CaseClassMock0Args'" when {
    val clazz = classOf[CaseClassMock0Args]
    val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)
    val objectSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

    "passing the class" should {
      "should return correct instance" in {
        RichMethodRefl
          .apply(classSymbol, RichMethodRefl.TERMNAME_CTOR)
          .head
          .apply()
          .isInstanceOf[CaseClassMock0Args] should be (right = true)
      }
    }

    "passing the object: call 'apply" should {
      val r1 = RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY)
      val method = RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY).head

      "should return correct instance" in {
        method
          .apply()
          .isInstanceOf[CaseClassMock0Args] should be(right = true)
      }
    }

    "passing the object: call 'ctor" should {
      "should return correct instance" in {
        RichMethodRefl
          .apply(objectSymbol, RichMethodRefl.TERMNAME_CTOR)
          .head
          .apply()
          .getClass
          .getCanonicalName should be (right = objectSymbol.fullName)
      }
    }
  }

  "testing case class: 'CaseClassMock2Args_wMethods'" when {
    val clazz = classOf[CaseClassMock2Args_wMethods]
    val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)
    val objectSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

    "passing the class: call 'ctor'" should {
      val method = RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply("klölö", 12)
          .isInstanceOf[CaseClassMock2Args_wMethods] should be (right = true)
      }

      "should return correct instance, w/ correct args: 1st param correct" in {
        method
          .apply("klölö", 12)
          .asInstanceOf[CaseClassMock2Args_wMethods].arg1 should be ("klölö")
      }

      "should return correct instance, w/ correct args: 2nd param correct" in {
        method
          .apply("klölö", 12)
          .asInstanceOf[CaseClassMock2Args_wMethods].arg2 should be (12)
      }

      "should return correct instance, w/ too few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö"))
      }

      "should return correct instance, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö", 12, 45646d))
      }
    }

    "passing the object: call 'apply#1'" should {
      val method = RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply("klölö", 12)
          .isInstanceOf[CaseClassMock2Args_wMethods] should be (right = true)
      }

      "should return correct instance, w/ correct args: 1st param correct" in {
        method
          .apply("klölö", 12)
          .asInstanceOf[CaseClassMock2Args_wMethods].arg1 should be ("klölö")
      }

      "should return correct instance, w/ correct args: 2nd param correct" in {
        method
          .apply("klölö", 12)
          .asInstanceOf[CaseClassMock2Args_wMethods].arg2 should be (12)
      }

      "should return correct instance, w/ too few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö"))
      }

      "should return correct instance, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö", 12, 45646d))
      }
    }

    "passing the object: call 'apply#2' (comes from trait)" should {
      val method = RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY)(1)

      "should throw an exception, w/ correct args: instance correct" in {
        an[IllegalArgumentException] shouldBe  thrownBy(method.apply("klölö", 12))
      }

      "should throw an exception, w/ correct args: 1st param correct" in {
        an[IllegalArgumentException] shouldBe  thrownBy(method.apply("klölö", 12))
      }

      "should throw an exception, w/ correct args: 2nd param correct" in {
        an[IllegalArgumentException] shouldBe  thrownBy(method.apply("klölö", 12))
      }

      "should throw an exception, w/ too few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö"))
      }

      "should throw an exception, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should throw an exception, w/ too much args" in {
        an[IllegalArgumentException] shouldBe  thrownBy(method.apply("klölö", 12, 45646d))
      }
    }
  }

  "testing case class: 'CaseClassMock2Args_woMethods'" when {
    val clazz = classOf[CaseClassMock2Args_woMethods]
    val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)
    val objectSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

    "passing the class: call 'ctor'" should {
      val method = RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply("klölö", 12)
          .isInstanceOf[CaseClassMock2Args_woMethods] should be (right = true)
      }

      "should return correct instance, w/ correct args: 1st param correct" in {
        method
          .apply("klölö", 12)
          .asInstanceOf[CaseClassMock2Args_woMethods].arg1 should be ("klölö")
      }

      "should return correct instance, w/ correct args: 2nd param correct" in {
        method
          .apply("klölö", 12)
          .asInstanceOf[CaseClassMock2Args_woMethods].arg2 should be (12)
      }

      "should return correct instance, w/ zero args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply())
      }

      "should return correct instance, w/ too few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö"))
      }

      "should return correct instance, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö", 12, 45646d))
      }
    }

    "passing the object: call 'apply#1'" should {
      val method = RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply("klölö", 12)
          .isInstanceOf[CaseClassMock2Args_woMethods] should be (right = true)
      }

      "should return correct instance, w/ correct args: 1st param correct" in {
        method
          .apply("klölö", 12)
          .asInstanceOf[CaseClassMock2Args_woMethods].arg1 should be ("klölö")
      }

      "should return correct instance, w/ correct args: 2nd param correct" in {
        method
          .apply("klölö", 12)
          .asInstanceOf[CaseClassMock2Args_woMethods].arg2 should be (12)
      }

      "should return correct instance, w/ too few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö"))
      }

      "should return correct instance, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö", 12, 45646d))
      }
    }

    "passing the object: call 'apply#2' (comes from trait)" should {
      val method = RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY)(1)

      "should throw an exception, w/ correct args: instance correct" in {
        an[IllegalArgumentException] shouldBe  thrownBy(method.apply("klölö", 12))
      }

      "should throw an exception, w/ correct args: 1st param correct" in {
        an[IllegalArgumentException] shouldBe  thrownBy(method.apply("klölö", 12))
      }

      "should throw an exception, w/ correct args: 2nd param correct" in {
        an[IllegalArgumentException] shouldBe  thrownBy(method.apply("klölö", 12))
      }

      "should throw an exception, w/ too few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö"))
      }

      "should throw an exception, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should throw an exception, w/ too much args" in {
        an[IllegalArgumentException] shouldBe  thrownBy(method.apply("klölö", 12, 45646d))
      }
    }
  }

  "testing case class: 'CaseClassMock2Args_wOverloadedMethods'" when {
    val clazz = classOf[CaseClassMock2Args_wOverloadedMethods]
    val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)
    val objectSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

    "passing the class: call 'ctor'" should {
      val method = RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply("klölö", 12)
          .isInstanceOf[CaseClassMock2Args_wOverloadedMethods] should be (right = true)
      }

      "should return correct instance, w/ correct args: 1st param correct" in {
        method
          .apply("klölö", 12)
          .asInstanceOf[CaseClassMock2Args_wOverloadedMethods].arg1 should be ("klölö")
      }

      "should return correct instance, w/ correct args: 2nd param correct" in {
        method
          .apply("klölö", 12)
          .asInstanceOf[CaseClassMock2Args_wOverloadedMethods].arg2 should be (12)
      }

      "should return correct instance, w/ too few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö"))
      }

      "should return correct instance, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö", 12, 45646d))
      }
    }

    "passing the class: call 'method1'" should {
      val method = RichMethodRefl.apply(classSymbol, ru.TermName("method1")).head

      "should throw an exception, w/ correct args: instance correct" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply())
      }

      "should throw an exception, w/ correct args: return correct" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölöfdfd"))
      }

      "should throw an exception, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f))
      }

      "should throw an exception, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("dffrefreklölö"))
      }
    }

    "passing the class: call 'method2(String)'" should {
      val method = RichMethodRefl.apply(classSymbol, ru.TermName("method2")).head

      "should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klö"))
      }
    }

    "passing the class: call 'method2(String, Int)'" should {
      val method = RichMethodRefl.apply(classSymbol, ru.TermName("method2"))(1)

      "should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klö", 23))
      }
    }

    "passing the class: call 'method2(Byte, String, Long)'" should {
      val method = RichMethodRefl.apply(classSymbol, ru.TermName("method2"))(1)

      "should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klö", 23.toByte, "lköklö", 1243l))
      }
    }

    "passing the object: call 'apply#1'" should {
      val method = RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply("klölö", 12)
          .isInstanceOf[CaseClassMock2Args_wOverloadedMethods] should be (right = true)
      }

      "should return correct instance, w/ correct args: 1st param correct" in {
        method
          .apply("klölö", 12)
          .asInstanceOf[CaseClassMock2Args_wOverloadedMethods].arg1 should be ("klölö")
      }

      "should return correct instance, w/ correct args: 2nd param correct" in {
        method
          .apply("klölö", 12)
          .asInstanceOf[CaseClassMock2Args_wOverloadedMethods].arg2 should be (12)
      }

      "should return correct instance, w/ too few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö"))
      }

      "should return correct instance, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö", 12, 45646d))
      }
    }

    "passing the object: call 'apply#2' (comes from trait)" should {
      val method = RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY)(1)

      "should throw an exception, w/ correct args: instance correct" in {
        an[IllegalArgumentException] shouldBe  thrownBy(method.apply("klölö", 12))
      }
    }
  }

  "testing case class: 'CaseClassMock3Args_2Defaults'" when {
    val clazz = classOf[CaseClassMock3Args_2Defaults]
    val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)
    val objectSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

    "passing the class: call 'ctor'" should {
      val method = RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply("klölö", false, 12)
          .isInstanceOf[CaseClassMock3Args_2Defaults] should be (right = true)
      }

      "should return correct instance, w/ correct args: 1st param correct" in {
        method
          .apply("klölö", false, 12)
          .asInstanceOf[CaseClassMock3Args_2Defaults].arg1 should be ("klölö")
      }

      "should return correct instance, w/ correct args: 2nd param correct" in {
        method
          .apply("klölö", true, 12)
          .asInstanceOf[CaseClassMock3Args_2Defaults].arg2 should be (right = true)
      }

      "should return correct instance, w/ correct args: 3rd param correct" in {
        method
          .apply("klölö", true, 12)
          .asInstanceOf[CaseClassMock3Args_2Defaults].arg3 should be (12)
      }

      "should return correct instance, w/ too few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö"))
      }

      "should return correct instance, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö", false, 12, 45646d))
      }
    }

    "passing the object: call 'apply#1'" should {
      val method = RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply("klölö", true, 12)
          .isInstanceOf[CaseClassMock3Args_2Defaults] should be (right = true)
      }

      "should return correct instance, w/ correct args: 1st param correct" in {
        method
          .apply("klölö", true, 12)
          .asInstanceOf[CaseClassMock3Args_2Defaults].arg1 should be ("klölö")
      }

      "should return correct instance, w/ correct args: 2nd param correct" in {
        method
          .apply("klölö", true, 12)
          .asInstanceOf[CaseClassMock3Args_2Defaults].arg2 should be (right = true)
      }

      "should return correct instance, w/ correct args: 3rd param correct" in {
        method
          .apply("klölö", true, 12)
          .asInstanceOf[CaseClassMock3Args_2Defaults].arg3 should be (12)
      }

      "should return correct instance, w/ too few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö"))
      }

      "should return correct instance, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö", false, 12, 45646d))
      }
    }

    "passing the object: call 'apply#2' (comes from trait)" should {
      val method = RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY)(1)

      "should throw an exception, w/ correct args: instance correct" in {
        an[IllegalArgumentException] shouldBe  thrownBy(method.apply("klölö", 12))
      }
    }
  }

  "testing case class: 'CaseClassMock3Args_allDefaults'" when {
    val clazz = classOf[CaseClassMock3Args_allDefaults]
    val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)
    val objectSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

    "passing the class: call 'ctor'" should {
      val method = RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply("klölö", false, 12)
          .isInstanceOf[CaseClassMock3Args_allDefaults] should be (right = true)
      }

      "should return correct instance, w/ correct args: 1st param correct" in {
        method
          .apply("klölö", false, 12)
          .asInstanceOf[CaseClassMock3Args_allDefaults].arg1 should be ("klölö")
      }

      "should return correct instance, w/ correct args: 2nd param correct" in {
        method
          .apply("klölö", true, 12)
          .asInstanceOf[CaseClassMock3Args_allDefaults].arg2 should be (right = true)
      }

      "should return correct instance, w/ correct args: 3rd param correct" in {
        method
          .apply("klölö", true, 12)
          .asInstanceOf[CaseClassMock3Args_allDefaults].arg3 should be (12)
      }

      "wenn zu wenig Arguments mit gegeben werden, soll der Rest mit den defaults aufgefüllt werden" in {
        method.apply[CaseClassMock3Args_allDefaults]("klölö") should be (CaseClassMock3Args_allDefaults("klölö", true, 43212))
      }

      "should return correct instance, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö", false, 12, 45646d))
      }
    }

    "passing the object: call 'apply#1'" should {
      val method = RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply("klölö", true, 12)
          .isInstanceOf[CaseClassMock3Args_allDefaults] should be (right = true)
      }

      "should return correct instance, w/ correct args: 1st param correct" in {
        method
          .apply("klölö", true, 12)
          .asInstanceOf[CaseClassMock3Args_allDefaults].arg1 should be ("klölö")
      }

      "should return correct instance, w/ correct args: 2nd param correct" in {
        method
          .apply("klölö", true, 12)
          .asInstanceOf[CaseClassMock3Args_allDefaults].arg2 should be (right = true)
      }

      "should return correct instance, w/ correct args: 3rd param correct" in {
        method
          .apply("klölö", true, 12)
          .asInstanceOf[CaseClassMock3Args_allDefaults].arg3 should be (12)
      }

      "should return correct instance, w/ zero args" in {
        method
          .apply()
          .asInstanceOf[CaseClassMock3Args_allDefaults] should be (CaseClassMock3Args_allDefaults("hello", arg2 = true, 43212))
      }

      "wenn zu wenig Arguments mit gegeben werden, soll der Rest mit den defaults aufgefüllt werden" in {
        method.apply[CaseClassMock3Args_allDefaults]("klölö") should be (CaseClassMock3Args_allDefaults("klölö", true, 43212))
      }

      "should return correct instance, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö", false, 12, 45646d))
      }
    }

    "passing the object: call 'apply#2' (comes from trait)" should {
      val method = RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY)(1)

      "should throw an exception, w/ correct args: instance correct" in {
        an[IllegalArgumentException] shouldBe  thrownBy(method.apply("klölö", 12))
      }
    }
  }

  "testing class: 'ClassMock0Args'" when {
    val clazz = classOf[ClassMock0Args]
    val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)

    "passing the class: call 'ctor'" should {
      val method = RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply()
          .isInstanceOf[ClassMock0Args] should be (right = true)
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }
    }
  }

  "testing case class: 'ClassMock2Args_wOverloadedMethods'" when {
    val clazz = classOf[ClassMock2Args_wOverloadedMethods]
    val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)

    "passing the class: call 'ctor'" should {
      val method = RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply("kökl", 547)
          .isInstanceOf[ClassMock2Args_wOverloadedMethods] should be (right = true)
      }

      "should return correct instance, w/ correct args: 1st param correct" in {
        method
          .apply("kökl", 547)
          .asInstanceOf[ClassMock2Args_wOverloadedMethods].arg1 should be ("kökl")
      }

      "should return correct instance, w/ correct args: 2nd param correct" in {
        method
          .apply("kökl", 547)
          .asInstanceOf[ClassMock2Args_wOverloadedMethods].arg2 should be (547)
      }

      "should return correct instance, w/ too few args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö"))
      }

      "should return correct instance, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö", 13, false, 12, 45646d))
      }
    }

    "passing the class: call 'method1'" should {
      val method = RichMethodRefl.apply(classSymbol, ru.TermName("method1")).head

      "should throw an exception, w/ correct args: instance correct" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply())
      }
    }

    "passing the class: call 'method2(String)'" should {
      val method = RichMethodRefl.apply(classSymbol, ru.TermName("method2")).head

      "should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klö"))
      }
    }

    "passing the class: call 'method2(String, Int)'" should {
      val method = RichMethodRefl.apply(classSymbol, ru.TermName("method2"))(1)

      "should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klö", 23))
      }
    }

    "passing the class: call 'method2(Byte, String, Long)'" should {
      val method = RichMethodRefl.apply(classSymbol, ru.TermName("method2"))(1)

      "should throw an exception" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klö", 23.toByte, "lköklö", 1243l))
      }
    }
  }

  "testing case class: 'ClassMock2Args_2Defaults'" when {
    val clazz = classOf[ClassMock2Args_2Defaults]
    val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)

    "passing the class: call 'ctor'" should {
      val method = RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).head

      "should return correct instance, w/ correct args: instance correct" in {
        method
          .apply("kökl", 547)
          .isInstanceOf[ClassMock2Args_2Defaults] should be (right = true)
      }

      "should return correct instance, w/ correct args: 1st param correct" in {
        method
          .apply("kökl", 547)
          .asInstanceOf[ClassMock2Args_2Defaults].arg1 should be ("kökl")
      }

      "should return correct instance, w/ correct args: 2nd param correct" in {
        method
          .apply("kökl", 547)
          .asInstanceOf[ClassMock2Args_2Defaults].arg2 should be (547)
      }

      "wenn zu wenig Arguments mit gegeben werden, soll der Rest mit den defaults aufgefüllt werden" in {
        method.apply[ClassMock2Args_2Defaults]("klölö").arg1 should be ("klölö")
        method.apply[ClassMock2Args_2Defaults]("klölö").arg2 should be (198)
      }

      "should return correct instance, w/ wrong types" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply(16f, "klölö"))
      }

      "should return correct instance, w/ too much args" in {
        an[IllegalArgumentException] shouldBe thrownBy(method.apply("klölö", 13, false, 12, 45646d))
      }

      "should return correct instance, w/ zero args: 1st arg is correct" in {
        method
          .apply()
          .asInstanceOf[ClassMock2Args_2Defaults].arg1 should be ("heiko")
      }

      "should return correct instance, w/ zero args: 2nd arg is correct" in {
        method
          .apply()
          .asInstanceOf[ClassMock2Args_2Defaults].arg2 should be (198)
      }
    }
  }
}
