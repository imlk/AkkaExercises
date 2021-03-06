package akkaexercises.zbay.ex7_routers_multiple

import akka.actor.Actor
import Auction.Protocol.{Bid, StatusRequest}
import zBay.Protocol._

class API extends Actor {

  def receive = {
    case AuctionBidRequest(auctionId, userId, value) =>
      auctionActorFor(auctionId).tell(Bid(value, userActorFor(userId)), sender)
    case AuctionStatusRequest(auctionId) =>
      auctionActorFor(auctionId).tell(StatusRequest, sender)
  }

  def userActorFor(userId: Long) = context.actorFor(s"../../user$userId")
  def auctionActorFor(auctionId: Long) = context.actorSelection(s"../../auction$auctionId")
}
