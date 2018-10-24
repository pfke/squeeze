package de.pfke.squeeze.zlib.io.simpleFile

import de.pfke.squeeze.zlib.io.simpleFile.dataProvider.DataProvider

import scala.collection.{JavaConverters, mutable}
import scala.util.matching.Regex

object SimpleRegexMatchFile {
  /**
    * Easy access func.
    */
  def apply(
    dataProvider: DataProvider,
    pattern: List[String]
  ) = new SimpleRegexMatchFile(dataProvider, pattern.map { s=> new Regex(s) })

  /**
    * Easy access func for java.
    */
  def create(
    dataProvider: DataProvider,
    pattern: java.util.List[String]
  ): SimpleRegexMatchFile = {
    new SimpleRegexMatchFile(
      dataProvider,
      JavaConverters
        .asScalaIterator(pattern.iterator())
        .map { s => new Regex(s) }
        .toList
    )
  }
}

class SimpleRegexMatchFile(
  dataProvider: DataProvider,
  pattern: List[Regex]
) {
  // fields
  private val _values = new mutable.HashMap[String, String]()
  private val _failed = new mutable.ArrayBuffer[String]()

  /**
    * Check if the xml file contains this key
    */
  def contains(key: String): Boolean = _values.contains(key)

  /**
    * Return the read values
    */
  def values: Map[String, String] = _values.toMap

  /**
    * Return the failed lines
    */
  def failed: List[String] = _failed.toList

  /**
    * Read a xml node value
    */
  def get(key: String): Option[String] = _values.get(key)

  /**
    * Is xml file empty?
    */
  def isEmpty: Boolean = _values.isEmpty

  /**
    * Read complete content of data provider.
    * This must be called from user code, because the xml data could be invalid, so an exception is thrown.
    * And the user must be able to detect.
    */
  def read(): SimpleRegexMatchFile = doRead()

  /**
    * Write a xml node value
    *
    * TODO: decouple doWrite()
    */
  def set(
    key: String,
    value: String
  ): SimpleRegexMatchFile = {
    _values.synchronized {
      _values += (key -> value)
    }
    doWrite()
    this
  }

  /**
    * Called with read content
    */
  protected def doRead(): SimpleRegexMatchFile = {
    _values.synchronized {
      _values.clear()

      dataProvider
        .read()
        .foreach { i =>
          pattern
            .find(_.findFirstIn(i).isDefined) match {
            case Some(x) =>
              x.findFirstMatchIn(i) match {
                case Some(xx) if xx.groupCount != 2 => throw new IllegalArgumentException(s"Illegal defined regex on data source: ${dataProvider.toString}. The regex must define 2 groups '(key)=(value)'. This is ur regex: ${x.pattern}")
                case Some(xx)                       => _values.put(xx.group(1), xx.group(2).trim)
                case None                           => _failed += i
              }
            case None => _failed += i
          }
        }
    }
    this
  }

  /**
    * Called when content can be written
    */
  protected def doWrite() {
    val txt = _values.synchronized {
      dataProvider
        .read()
        .map {l =>
          pattern
            .find(_.findFirstIn(l).isDefined) match {
            case Some(x) =>
              x.findFirstMatchIn(l) match {
                case Some(xx) =>
                  val sb = new StringBuilder()

                  sb.append(l.substring(0, xx.start(1)))
                  sb.append(xx.group(1))
                  sb.append(l.substring(xx.end(1), xx.start(2)))
                  sb.append(get(xx.group(1)).get)
                  sb.append(l.substring(xx.end(2), l.length()))
                  sb.result()

                case None => l
              }
            case None => l
          }
        }
    }
    dataProvider.write(txt.mkString("\n"))
  }
}
