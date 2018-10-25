package de.pfke.squeeze.zlib.version

import org.scalatest.{Matchers, WordSpec}

class PatchLevelVersionSpec
  extends WordSpec
  with Matchers {
  "implicit def apply(:String)" should {
    "convert 1.5-13" in {
      val ver: PatchLevelVersion = "1.5-13"

      ver.major should be (1)
      ver.minor should be (5)
      ver.patchLevel should be (13)
    }
    "convert .8-123" in {
      val ver: PatchLevelVersion = ".8-123"

      ver.major should be (0)
      ver.minor should be (8)
      ver.patchLevel should be (123)
    }
    "convert 8" in {
      val ver: PatchLevelVersion = "8"

      ver.major should be (8)
      ver.minor should be (0)
      ver.patchLevel should be (0)
    }
    "convert 07.0034-0123" in {
      val ver: PatchLevelVersion = "07.0034-0123"

      ver.major should be (7)
      ver.minor should be (34)
      ver.patchLevel should be (123)
    }
    "convert 1.5.13" in {
      val ver: PatchLevelVersion = "1.5.13"

      ver.major should be (1)
      ver.minor should be (5)
      ver.patchLevel should be (13)
    }

    "not convert \"\"" in {
      an[IllegalArgumentException] shouldBe thrownBy {
        PatchLevelVersion("")
      }
    }
    "not convert -28.23-456" in {
      an[IllegalArgumentException] shouldBe thrownBy {
        PatchLevelVersion("-28.23-456")
      }
    }
    "not convert -28.-23" in {
      an[IllegalArgumentException] shouldBe thrownBy {
        PatchLevelVersion("-28.-23-564")
      }
    }
    "not convert -28-546" in {
      an[IllegalArgumentException] shouldBe thrownBy {
        PatchLevelVersion("-28")
      }
    }
    "not convert 28.-23-546" in {
      an[IllegalArgumentException] shouldBe thrownBy {
        PatchLevelVersion("28.-23")
      }
    }
    "not convert .-23-89" in {
      an[IllegalArgumentException] shouldBe thrownBy {
        PatchLevelVersion(".-23")
      }
    }
  }

  "comparing two PatchLevelVersions" should {
    "return -1 on 0.1-0 vs. 1.1-0" in {
      PatchLevelVersion(0, 1) compare PatchLevelVersion(1, 1) should be < 0
    }

    "return  1 on 1.1-0 vs. 0.1-0" in {
      PatchLevelVersion(1, 1) compare PatchLevelVersion(0, 1) should be > 0
    }

    "return  0 on 1.1-123 vs. 1.1-123" in {
      PatchLevelVersion(1, 1, 123) compare PatchLevelVersion(1, 1, 123) should be (0)
    }

    "return -1 on 1.0-0 vs. 1.1-0" in {
      PatchLevelVersion(1) compare PatchLevelVersion(1, 1) should be < 0
    }

    "return  1 on 1.1-2 vs. 1.1-1" in {
      PatchLevelVersion(1, 1, 2) compare PatchLevelVersion(1, 1, 1) should be > 0
    }

    "return  0 on 1.3 vs. 1.3" in {
      PatchLevelVersion(1, 3) compare PatchLevelVersion(1, 3) should be (0)
    }
  }

  "comparing TwoNumberVersion and PatchLevelVersion" should {
    "return -1 on 1.1-123 vs. 0.1" in {
      PatchLevelVersion(1, 1, 123) compare TwoNumberVersion(0, 1) should be > 0
    }

    "return  1 on 0.1-0 vs. 1.1" in {
      PatchLevelVersion(0, 1) compare TwoNumberVersion(1, 1) should be < 0
    }

    "return  0 on 1.1-0 vs. 1.1" in {
      PatchLevelVersion(1, 1) compare TwoNumberVersion(1, 1) should be (0)
    }

    "return -1 on 1.1-0 vs. 1.0" in {
      PatchLevelVersion(1, 1) compare TwoNumberVersion(1) should be > 0
    }

    "return  1 on 1.1-9654 vs. 1.2" in {
      PatchLevelVersion(1, 1, 9654) compare TwoNumberVersion(1, 2) should be < 0
    }

    "return  0 on 1.3-1 vs. 1.3" in {
      PatchLevelVersion(1, 3, 1) compare TwoNumberVersion(1, 3) should be > 0
    }
  }
}
