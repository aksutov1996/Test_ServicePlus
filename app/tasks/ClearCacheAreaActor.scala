package tasks

import org.apache.pekko.actor.Actor

import scala.collection.mutable.ArrayBuffer

class ClearCacheAreaActor extends Actor {
  def receive = {
    case cacheArea: ArrayBuffer[(BigDecimal, BigDecimal, BigDecimal)] => cacheArea.clear()
    case _ =>
  }
}