import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.typed.DispatcherSelector
import com.typesafe.config.ConfigFactory
import java.io.File
import kotlin.random.Random

fun main(){
    val configFile = File("src/main/resources/dispatcher.conf")
    val config = ConfigFactory.parseFile(configFile)
    println(config.toString())
    val system = ActorSystem.create("satellite-system", config)

    val satellites = mutableListOf<ActorRef>()
    for(i in 100..199){
        satellites.add(system.actorOf(Satellite.props(i).withDispatcher("my-dispatcher"), "satellite$i"))
    }

    val dispatcher = system.actorOf(Dispatcher.props(satellites), "dispatcher")

    val station1 = system.actorOf(Station.props(dispatcher, "station-1"), "station-1")
    val station2 = system.actorOf(Station.props(dispatcher, "station-2"), "station-2")
    val station3 = system.actorOf(Station.props(dispatcher, "station-3"), "station-3")

    station1.tell(NotFullQuery( 100 + Random.nextInt(50), 50, 300), ActorRef.noSender())
    station1.tell(NotFullQuery( 100 + Random.nextInt(50), 50, 300), ActorRef.noSender())
    station2.tell(NotFullQuery( 100 + Random.nextInt(50), 50, 300), ActorRef.noSender())
    station2.tell(NotFullQuery( 100 + Random.nextInt(50), 50, 300), ActorRef.noSender())
    station3.tell(NotFullQuery( 100 + Random.nextInt(50), 50, 300), ActorRef.noSender())
    station3.tell(NotFullQuery( 100 + Random.nextInt(50), 50, 300), ActorRef.noSender())
}