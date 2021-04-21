import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props

fun main(){
    val system = ActorSystem.create("satellite-system")

    val satellites = mutableListOf<ActorRef>()
    for(i in 100..199){
        satellites.add(system.actorOf(Satellite.props(i), "satellite$i"))
    }

    val dispatcher = system.actorOf(Dispatcher.props(satellites), "dispatcher")

    val station1 = system.actorOf(Station.props(dispatcher), "station-1")
    station1.tell(Query(1, 120, 60, 300), ActorRef.noSender())
}