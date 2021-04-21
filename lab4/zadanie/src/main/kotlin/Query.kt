import akka.actor.ActorRef
import java.time.Instant

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