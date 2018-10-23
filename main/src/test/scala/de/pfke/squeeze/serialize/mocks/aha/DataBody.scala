package de.pfke.squeeze.serialize.mocks.aha

import de.pfke.squeeze.annots.{injectLength, injectType}
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.AhaFunction.AhaFunction
import de.pfke.squeeze.serialize.serializerCompiler.aha.messages.payloads.{AhaFunction, Payload}

case class DataBody(
  @injectType(fromField = "payload")        function: AhaFunction = AhaFunction.UNKNOWN,
  @injectLength(fromField = "payload") payloadLength: Short = 0,
                                                res1: Short = 0,
                                             payload: Payload
)
