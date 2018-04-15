package de.pfke.squeeze.serialize.serializerHints

import akka.util.ByteStringBuilder

case class ByteStringBuilderHint (
  builder: ByteStringBuilder
) extends StringBuilderHint
