import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props

fun main(){
    val system = ActorSystem.create("satellite-system")

    val dispatcher = system.actorOf(Props.create(Dispatcher::class.java), "dispatcher")

    val station1 = system.actorOf(Station.props(dispatcher), "station-1")
    station1.tell(Query(1, 120, 60, 300), ActorRef.noSender())
}