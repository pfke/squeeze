package de.pfke.squeeze.zlib.data.byTypes.simple

import scala.xml.NodeSeq

object XmlIncludes
  extends XmlIncludes

trait XmlIncludes {
  implicit class RichNodeSeq(ns: NodeSeq) {
    def \@(
      attrName: String,
      matcher: String => Boolean
      ): NodeSeq = ns filter { _ \\ ("@" + attrName) exists (s => matcher(s.text)) }
  }
}
