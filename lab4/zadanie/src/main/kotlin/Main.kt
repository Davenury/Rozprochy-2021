import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.typed.DispatcherSelector
import com.typesafe.config.ConfigFactory
import java.io.File
import kotlin.random.Random

fun main(){

    val connection = DB.connect()

    val configFile = File("src/main/resources/dispatcher.conf")
    val config = ConfigFactory.parseFile(configFile)
    println(config.toString())
    val system = ActorSystem.create("satellite-system", config)

    val satellites = mutableListOf<ActorRef>()
    for(i in 100..199){
        satellites.add(system.actorOf(Satellite.props(i).withDispatcher("my-dispatcher"), "satellite$i"))
    }

    val dispatcher = system.actorOf(Dispatcher.props(satellites), "dispatcher")

    val station1 = system.actorOf(Station.props(dispatcher, "station-1", connection), "station-1")
    val station2 = system.actorOf(Station.props(dispatcher, "station-2", connection), "station-2")
    val station3 = system.actorOf(Station.props(dispatcher, "station-3", connection), "station-3")

    station1.tell(NotFullQuery( 100 + Random.nextInt(50), 50, 300), ActorRef.noSender())
    station1.tell(NotFullQuery( 100 + Random.nextInt(50), 50, 300), ActorRef.noSender())
    station2.tell(NotFullQuery( 100 + Random.nextInt(50), 50, 300), ActorRef.noSender())
    station2.tell(NotFullQuery( 100 + Random.nextInt(50), 50, 300), ActorRef.noSender())
    station3.tell(NotFullQuery( 100 + Random.nextInt(50), 50, 300), ActorRef.noSender())
    station3.tell(NotFullQuery( 100 + Random.nextInt(50), 50, 300), ActorRef.noSender())

    Thread.sleep(3000)
    for(i in 100..199){
        station1.tell(DBQuery(i), ActorRef.noSender())
    }
}