import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props
import java.time.Instant

class Station(
    private val dispatcherRef: ActorRef
) : AbstractActor() {

    private val name = "xd"
    private lateinit var start: Instant
    private lateinit var end: Instant

    private fun ask(query: Query){
        this.dispatcherRef.tell(query, context.self)
    }

    override fun createReceive(): Receive {
        return receiveBuilder()
            .match(Query::class.java){
                start = Instant.now()
                this.ask(it)
            }
            .match(Response::class.java){
                end = Instant.now()
                val delta = end.toEpochMilli() - start.toEpochMilli()
                println("Station: $name, Answer came after: $delta, Errors: ${it.map.size}, percentage: ${it.percentage}")
                it.map.forEach {
                    println("${it.key}: ${it.value}")
                }
            }
            .build()
    }

    companion object{
        fun props(dispatcherRef: ActorRef): Props{
            return Props.create(Station::class.java, dispatcherRef)
        }

        private val id: Int = 0
    }
}