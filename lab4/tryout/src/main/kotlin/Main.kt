import akka.actor.ActorRef
import akka.actor.ActorSystem
import java.lang.Long.parseLong
import com.typesafe.config.ConfigFactory
import java.io.File
import java.lang.NumberFormatException
import kotlin.random.Random
import kotlin.system.exitProcess

fun main(args: Array<String>){
    argsCheck(args)

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

    val dbActor = system.actorOf(DBActor.props(connection),"db-actor")

    val station1 = system.actorOf(Station.props(dispatcher, "station-1", dbActor), "station-1")
    val station2 = system.actorOf(Station.props(dispatcher, "station-2", dbActor), "station-2")
    val station3 = system.actorOf(Station.props(dispatcher, "station-3", dbActor), "station-3")

    ask(station1)
    ask(station1)
    ask(station2)
    ask(station2)
    ask(station3)
    ask(station3)

    Thread.sleep(args[0].toLong() * 1000)
    for(i in 100..199){
        station1.tell(AskToDB(i), ActorRef.noSender())
    }
}

fun argsCheck(args: Array<String>){
    if(args.isEmpty()){
        println("You didn't pass number of seconds to wait!")
    }
    try {
        val number = parseLong(args[0])
    } catch (e: NumberFormatException){
        println("Number of seconds to wait isn't a number!")
        exitProcess(0)
    }
}

fun ask(station: ActorRef){
    station.tell(NotFullQuery(100 + Random.nextInt(50), 50, 300), ActorRef.noSender())
}