import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props
import java.sql.Connection
import java.time.Instant

class Station(
    private val dispatcherRef: ActorRef,
    private val name: String,
    private val connection: Connection?
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
                println(start.toEpochMilli())
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
                DB.addNumberOfMistakes(it.map, connection)
            }
            .match(DBQuery::class.java){
                val result = DB.getNumberOfMistakes(it.satelliteId, connection)
                result?.let {result ->
                    if (result > 0)
                        println("DB result for ${it.satelliteId}: $result")
                }
            }
            .build()
    }

    companion object{
        fun props(dispatcherRef: ActorRef, name: String, connection: Connection?): Props{
            return Props.create(Station::class.java, dispatcherRef, name, connection)
        }
    }
}