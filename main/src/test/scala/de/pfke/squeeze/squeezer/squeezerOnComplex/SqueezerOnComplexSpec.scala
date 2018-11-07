package de.pfke.squeeze.squeezer.squeezerOnComplex

import java.nio.ByteOrder

import akka.util.ByteString
import de.pfke.squeeze.Squeezer
import de.pfke.squeeze.squeezer.BaseSqueezerSpec
import de.pfke.squeeze.squeezer.squeezerOnComplex.mocks.{Message, MessagePayloadA, MessageType}

class SqueezerOnComplexSpec
  extends BaseSqueezerSpec {
  "testing Squeezer.pack with simple Boolean type" when {
    "testing with ByteOrder.BIG_ENDIAN" should {
      implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN

      val tto = Message(
        messageType = MessageType.MessageTypeA,
        messageLength = 0,
        payload = MessagePayloadA(
          content = 0xabcdef12
        )
      )

      "should return a ByteString with correct length" in {
        Squeezer().serialize(in = tto).length should be (8)
      }

      "should return correct packed ByteString" in {
        Squeezer().serialize(in = tto) should be (
          ByteString(
            0x00, 0x01,
            0x08,
            0xab, 0xcd, 0xef, 0x12
          )
        )
      }
    }
  }
}
