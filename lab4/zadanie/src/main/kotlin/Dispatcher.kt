import akka.actor.AbstractActor
import java.time.Instant

class Dispatcher : AbstractActor() {

    override fun createReceive(): Receive {
        return receiveBuilder().match(Query::class.java) {
            val response = this.ask(it)
            sender.tell(response, self)
        }.build()
    }

    private fun ask(query: Query): Response{
        var count = 0.0
        var satId = query.firstStaId
        val map = HashMap<Int, SatelliteAPI.Status>()
        while(satId < query.firstStaId + query.range){
            val start = Instant.now()
            val status = askSatellite(satId)
            val stop = Instant.now()
            if(status != SatelliteAPI.Status.OK){
                map[satId] = status
            }
            if(stop.toEpochMilli() - start.toEpochMilli() <= query.timeout){
                count++
            }
            satId++
        }
        return Response(query.queryId, map, count / query.range)
    }

    private fun askSatellite(id: Int): SatelliteAPI.Status {
        return SatelliteAPI.getStatus(id)
    }
}