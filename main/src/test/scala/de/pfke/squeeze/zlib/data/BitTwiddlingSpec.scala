package de.pfke.squeeze.zlib.data

import org.scalatest.{Matchers, WordSpecLike}

import scala.collection.immutable.BitSet

class BitTwiddlingSpec
  extends WordSpecLike with Matchers {
  "method 'getMostSignificantBit'" when {
    "pass an enum" should {
      "identify no bit set" in {
        object enum extends Enumeration {
          type enum = Value
        }
        BitTwiddling.getMostSignificantBit(enum) should be (None)
      }

      "identify bit 0" in {
        object enum extends Enumeration {
          type enum = Value
          val e1: enum.Value = Value
        }
        BitTwiddling.getMostSignificantBit(enum) should be (Some(0))
      }

      "identify bit 127" in {
        object enum extends Enumeration {
          type enum = Value
          val e1: enum.Value = Value
          val e2: enum.Value = Value(127)
        }
        BitTwiddling.getMostSignificantBit(enum) should be (Some(127))
      }
    }

    "pass a bit set" should {
      "identify no bit set" in {
        BitTwiddling.getMostSignificantBit(BitSet()) should be (None)
      }

      "identify bit 0" in {
        BitTwiddling.getMostSignificantBit(BitSet(0)) should be (Some(0))
      }

      "identify bit 127" in {
        BitTwiddling.getMostSignificantBit(BitSet(127)) should be (Some(127))
      }
    }

    "pass an array of long" should {
      "identify no bit set" in {
        BitTwiddling.getMostSignificantBit(Array(0l, 0l)) should be (None)
      }

      "identify bit 0" in {
        BitTwiddling.getMostSignificantBit(Array((1 << 0).toLong)) should be (Some(0))
      }

      "identify bit 63" in {
        BitTwiddling.getMostSignificantBit(Array((1<<63).toLong, 0l)) should be (Some(63))
      }

      "identify bit 127" in {
        BitTwiddling.getMostSignificantBit(Array(0l, (1<<63).toLong)) should be (Some(127))
      }
    }

    "pass a long" should {
      "identify no bit set" in {
        BitTwiddling.getMostSignificantBit(0x0l) should be (None)
      }

      "identify bit 0" in {
        BitTwiddling.getMostSignificantBit(1l << 0) should be (Some(0))
      }

      "identify bit 63" in {
        BitTwiddling.getMostSignificantBit(1l << 63) should be (Some(63))
      }
    }

    "pass an int" should {
      "identify no bit set" in {
        BitTwiddling.getMostSignificantBit(0x0) should be (None)
      }

      "identify bit 0" in {
        BitTwiddling.getMostSignificantBit(1 << 0) should be (Some(0))
      }

      "identify bit 31" in {
        BitTwiddling.getMostSignificantBit(1<<31) should be (Some(31))
      }
    }
  }
}
