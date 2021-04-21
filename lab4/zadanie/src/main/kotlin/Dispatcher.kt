import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props
import akka.pattern.Patterns.ask
import java.time.Instant
import java.util.concurrent.CompletableFuture

class Dispatcher(
        private val satellites: List<ActorRef>
) : AbstractActor() {

    private val map = HashMap<ActorRef, QueryWithResponses>()

    override fun createReceive(): Receive {
        return receiveBuilder().match(StationQuery::class.java) {
            map[it.station] = QueryWithResponses(it.toInternalQuery())
            this.ask(it.toInternalQuery())
        }
        .match(SatelliteResponse::class.java) {response ->
            handleSatelliteResponse(response)?.let{
                val finalResponse = handleFullResponseFromSatellites(it)
                response.internalQuery.station.tell(finalResponse, self)
            }
        }
        .build()
    }

    private fun ask(internalQuery: InternalQuery) {
        var satId = internalQuery.query.firstStaId
        while (satId < internalQuery.query.firstStaId + internalQuery.query.range) {
            askSatellite(satId, internalQuery)
            satId++
        }
    }

    private fun askSatellite(id: Int, internalQuery: InternalQuery) {
        satellites[id - 101].tell(internalQuery.toSatelliteAsk(id, context.self), context.self)
    }

    private fun handleSatelliteResponse(response: SatelliteResponse): QueryWithResponses? {
        map[response.internalQuery.station]?.let{
            it.list.add(response)
            if(it.list.size == it.query.query.range){
                return it
            }
        }
        return null
    }

    private fun handleFullResponseFromSatellites(elem: QueryWithResponses): Response {
        val map = HashMap<Int, SatelliteAPI.Status>()
        var counter = 0.0
        elem.list.forEach {
            if(isInTime(it) && it.status != SatelliteAPI.Status.OK)
                map[it.satelliteId] = it.status
            if(isInTime(it)){
                counter++
            }
        }
        return Response(
                elem.query.query.queryId,
                map,
                counter / elem.query.query.range
        )
    }

    private fun isInTime(it: SatelliteResponse): Boolean {
        val time =
            it.end.toEpochMilli() - it.start.toEpochMilli()
        return time < it.internalQuery.query.timeout
    }

    companion object {
        fun props(satellites: List<ActorRef>): Props {
            return Props.create(Dispatcher::class.java, satellites)
        }
    }
}

private data class QueryWithResponses(
        val query: InternalQuery,
        val list: MutableList<SatelliteResponse> = mutableListOf()
)