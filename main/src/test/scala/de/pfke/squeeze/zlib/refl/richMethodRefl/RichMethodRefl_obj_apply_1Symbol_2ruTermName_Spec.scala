package de.pfke.squeeze.zlib.refl.richMethodRefl

import de.pfke.squeeze.zlib.refl.{RichMethodRefl, RichRuntimeMirror}
import de.pfke.squeeze.zlib.refl.richMethodRefl.mocks._

import scala.reflect.runtime.{universe => ru}

class RichMethodRefl_obj_apply_1Symbol_2ruTermName_Spec
  extends RichMethodReflBaseSpec {
  "testing case class: 'CaseClassMock0Args'" when {
    val clazz = classOf[CaseClassMock0Args]
    val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)
    val objectSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

    "passing the class" should {
      "should return 0 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (0)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }
    }

    "passing the object" should {
      "should return 1 instances ('apply')" in {
        RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (1)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(objectSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(objectSymbol, ru.TermName("applssy")).size should be (0)
      }
    }

    "passing the compagnion object" should {
      val compagnionSymbol = classSymbol.companion

      "should return 1 instances ('apply')" in {
        RichMethodRefl.apply(compagnionSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (1)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(compagnionSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(compagnionSymbol, ru.TermName("applssy")).size should be (0)
      }
    }
  }

  "testing case class: 'CaseClassMock2Args_wMethods'" when {
    val clazz = classOf[CaseClassMock2Args_wMethods]

    "passing the class" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)

      "should return 0 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (0)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 1 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (1)
      }

      "should return 1 instance, w/ method 'method2'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method2")).size should be (1)
      }
    }

    "passing the object" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

      "should return 2 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (2)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 0 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (0)
      }

      "should return 0 instance, w/ method 'method2'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method2")).size should be (0)
      }
    }
  }

  "testing case class: 'CaseClassMock2Args_woMethods'" when {
    val clazz = classOf[CaseClassMock2Args_woMethods]

    "passing the class" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)

      "should return 0 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (0)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }
    }

    "passing the object" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

      "should return 2 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (2)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }
    }
  }

  "testing case class: 'CaseClassMock2Args_wOverloadedMethods'" when {
    val clazz = classOf[CaseClassMock2Args_wOverloadedMethods]

    "passing the class" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)

      "should return 0 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (0)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 1 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (1)
      }

      "should return 3 instance, w/ method 'method2'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method2")).size should be (3)
      }
    }

    "passing the object" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

      "should return 2 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (2)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 0 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (0)
      }

      "should return 0 instance, w/ method 'method2'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method2")).size should be (0)
      }
    }
  }

  "testing case class: 'CaseClassMock3Args_0Defaults'" when {
    val clazz = classOf[CaseClassMock3Args_0Defaults]

    "passing the class" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)

      "should return 0 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (0)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 0 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (0)
      }
    }

    "passing the object" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

      "should return 2 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (2)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 0 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (0)
      }
    }
  }

  "testing case class: 'CaseClassMock3Args_1Default'" when {
    val clazz = classOf[CaseClassMock3Args_1Default]

    "passing the class" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)

      "should return 0 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (0)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 0 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (0)
      }
    }

    "passing the object" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

      "should return 2 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (2)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 0 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (0)
      }
    }
  }

  "testing case class: 'CaseClassMock3Args_2Defaults'" when {
    val clazz = classOf[CaseClassMock3Args_2Defaults]

    "passing the class" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)

      "should return 0 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (0)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 0 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (0)
      }
    }

    "passing the object" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

      "should return 2 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (2)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 0 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (0)
      }
    }
  }

  "testing case class: 'CaseClassMock3Args_allDefaults'" when {
    val clazz = classOf[CaseClassMock3Args_allDefaults]

    "passing the class" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)

      "should return 0 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (0)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 0 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (0)
      }
    }

    "passing the object" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz.getCanonicalName + "$")

      "should return 2 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (2)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 0 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (0)
      }
    }
  }

  "testing class: 'ClassMock0Args'" when {
    val clazz = classOf[ClassMock0Args]

    "passing the class" should {
      val classSymbol = RichRuntimeMirror().getClassSymbol(clazz)

      "should return 0 instances ('apply')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_APPLY).size should be (0)
      }

      "should return 1 instances ('ctor')" in {
        RichMethodRefl.apply(classSymbol, RichMethodRefl.TERMNAME_CTOR).size should be (1)
      }

      "should return 0 instance, w/ non-existing method" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("applssy")).size should be (0)
      }

      "should return 0 instance, w/ method 'method1'" in {
        RichMethodRefl.apply(classSymbol, ru.TermName("method1")).size should be (0)
      }
    }
  }
}
