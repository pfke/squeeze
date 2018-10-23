package de.pfke.squeeze.zlib.data.collection

import scala.collection.mutable

class LimitedSizeQueue[A](
  val limit: Int
  )
  extends mutable.Queue[A] {
  override def enqueue(elems: A*): Unit = {
    if(elems.size > limit) {
      super.clear()
    } else if((elems.size + size) > limit) {
      val r1 = limit - math.min(0, size - elems.size)

      (0 until r1).foreach { i => dequeue() }
    }

    super.enqueue(elems.reverse.slice(0, limit).reverse:_*)
  }

//  override def enqueue(elems: A*): Unit = elems.foreach(enqueue) <- um eine 10er Potenz langsamer

  private def enqueue(elem: A): Unit = {
    if(size >= limit)
      dequeue()

    super.enqueue(elem)
  }
}
