package com.apifortress.afthem.actors

import akka.actor.SupervisorStrategy.{Restart, Resume}
import akka.actor.{OneForOneStrategy, Props, Actor}
import akka.routing.FromConfig
import com.apifortress.afthem.exceptions.AfthemFlowException
import com.apifortress.afthem.messages.{ExceptionMessage, StartActorsCommand}
import org.slf4j.LoggerFactory
import scala.concurrent.duration._

class GenericSupervisorActor(val id : String) extends Actor {

  val log = LoggerFactory.getLogger(classOf[GenericSupervisorActor])

  log.info("Initializing supervisor "+self.path.toStringWithoutAddress+" - "+context.dispatcher)

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case exception : AfthemFlowException =>
        new ExceptionMessage(exception,500,exception.message).respond()
        Resume
      case exception : Exception     => Restart
    }
  override def receive: Receive = {
    case cmd : StartActorsCommand =>
      cmd.implementers.foreach{ implementer =>
        val theClass = Class.forName(implementer.className)
        context.actorOf(FromConfig.getInstance.props(Props.create(theClass,id+"/"+implementer.id)), implementer.id)
      }
  }
}