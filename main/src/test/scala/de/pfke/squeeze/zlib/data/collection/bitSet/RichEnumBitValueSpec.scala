package de.pfke.squeeze.zlib.data.collection.bitSet

import org.scalatest.{Matchers, WordSpecLike}

class RichEnumBitValueSpec
  extends WordSpecLike with Matchers {
  "instance created with 'apply'" should {
    "have a shift of 0" in {
      RichEnumBitValue.apply(TestEnum).shift should be (0)
    }

    "have a mask of 0x7" in {
      RichEnumBitValue.apply(TestEnum).mask should be (0x7)
    }
  }

  "instance created with 'applyWithLen'" should {
    "set passed shift value" in {
      RichEnumBitValue.applyWithLen(TestEnum, shift = 8, len = 20).shift should be (8)
    }

    "set passed len as mask" in {
      RichEnumBitValue.applyWithLen(TestEnum, shift = 8, len = 20).mask should be ((1 << 20) - 1)
    }
  }

  "instance created with 'applyWithMask'" should {
    "set passed shift value" in {
      RichEnumBitValue.applyWithMask(TestEnum, shift = 8, mask = 0x3f).shift should be (8)
    }

    "set passed len as mask" in {
      RichEnumBitValue.applyWithMask(TestEnum, shift = 8, mask = 0x3f).mask should be (0x3f)
    }
  }
}
