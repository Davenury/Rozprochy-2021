import akka.actor.ActorRef
import java.time.Instant

data class NotFullQuery(
        val firstStaId: Int,
        val range: Int,
        val timeout: Long
)
fun NotFullQuery.toQuery(queryId: Int) = Query(
        queryId,
        this.firstStaId,
        this.range,
        this.timeout
)

data class Query(
    val queryId: Int,
    val firstStaId: Int,
    val range: Int,
    val timeout: Long
)

data class StationQuery(
        val query: Query,
        val station: ActorRef
)
fun Query.toStationQuery(station: ActorRef) = StationQuery(
        this,
        station
)

data class InternalQuery(
        val query: Query,
        val station: ActorRef
)

fun InternalQuery.toSatelliteAsk(satelliteId: Int, self: ActorRef) = SatelliteAsk(
        satelliteId,
        self,
        this
)

fun StationQuery.toInternalQuery() = InternalQuery(
        this.query,
        this.station
)


data class Response(
    val queryId: Int,
    val map: Map<Int, SatelliteAPI.Status>,
    val percentage: Double
)

data class SatelliteAsk(
        val satelliteId: Int,
        val sender: ActorRef,
        val internalQuery: InternalQuery
)

data class SatelliteResponse(
        val satelliteId: Int,
        val status: SatelliteAPI.Status,
        var start: Instant,
        var end: Instant,
        val internalQuery: InternalQuery
)

data class StationWithQueryId(
        val station: ActorRef,
        val queryId: Int
)

data class DBQuery(
        val satelliteId: Int
)

data class AskToDB(
        val satelliteId: Int
)

data class DBActorUpdate(
        val map: Map<Int, SatelliteAPI.Status>
)

data class DBActorResponse(
        val result: Int?,
        val satelliteId: Int
)