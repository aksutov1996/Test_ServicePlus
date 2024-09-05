package tasks

import org.apache.pekko.actor.{ActorRef, ActorSystem}

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

class ClearCacheArea @Inject()(actorSystem: ActorSystem)//, actor: ActorRef)
                              (implicit executionContext: ExecutionContext) {
  /*actorSystem.scheduler.scheduleAtFixedRate(
    initialDelay = 0.seconds
    , interval = 5.seconds
    , receiver = actor
    , message = "actor-clear-cache-area"
  )*/
}
