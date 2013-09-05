package akkaexercises.zbay.basicActorWithAuctionEnding

import akka.actor.Actor
import org.joda.time.DateTime
import org.scala_tools.time.Imports._

class Auction(timeService: TimeService, endTime: DateTime) extends Actor {
  import Auction.Protocol._

  var currentHighestBid = BigDecimal(0)

  def receive = {
    case StatusRequest => sender ! StatusResponse(currentHighestBid, currentStatus())
    case Bid(value)    => currentHighestBid = currentHighestBid max value
  }

  def currentStatus() = if (timeService.currentTime() > endTime) Ended
                        else                                     Running
}

object Auction {
  object Protocol {
    case object StatusRequest
    case class StatusResponse(currentHighestBid: BigDecimal, state: State)
    case class Bid(value: BigDecimal)

    sealed trait State
    case object Running extends State
    case object Ended extends State
  }
}

trait TimeService {
  def currentTime(): DateTime
}