import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props
import java.time.Instant

class Station(
    private val dispatcherRef: ActorRef,
    private val name: String,
    private val dbActor: ActorRef
) : AbstractActor() {

    private var id = 0
    private lateinit var start: Instant
    private lateinit var end: Instant

    private fun ask(query: Query){
        this.dispatcherRef.tell(query.toStationQuery(self), self)
    }

    override fun createReceive(): Receive {
        return receiveBuilder()
            .match(NotFullQuery::class.java){
                start = Instant.now()
                id++
                this.ask(it.toQuery(id))
            }
            .match(Response::class.java){
                end = Instant.now()
                val delta = end.toEpochMilli() - start.toEpochMilli()
                println("Station: $name, time: $delta, number of errors: ${it.map.size}, percentage of not timeouted: ${it.percentage}")
                it.map.forEach {
                    println("${it.key}: ${it.value}")
                }
                dbActor.tell(DBActorUpdate(it.map), self)
            }
            .match(AskToDB::class.java){
                dbActor.tell(DBQuery(it.satelliteId), self)
            }
            .match(DBActorResponse::class.java){
                val result = it.result
                result?.let {r ->
                    if (r > 0)
                        println("DB result for ${it.satelliteId}: $r")
                }
            }
            .build()
    }

    companion object{
        fun props(dispatcherRef: ActorRef, name: String, dbActor: ActorRef): Props{
            return Props.create(Station::class.java, dispatcherRef, name, dbActor)
        }
    }
}