package de.pfke.squeeze.testing

import org.scalatest.{BeforeAndAfterAll, Suite}

import scala.collection.mutable

trait StopCallAfterAll
  extends BeforeAndAfterAll { this: Suite =>
  // fields
  val _objsToStop = new mutable.HashSet[{ def stop(): Any }]()

  override protected def afterAll (): Unit = {
    super.afterAll()

    _objsToStop.foreach(_.stop())
  }

  protected def stopAfter[A <: { def stop(): Any }](toStop: A): A = {
    synchronized {
      _objsToStop += toStop
      toStop
    }}
}
