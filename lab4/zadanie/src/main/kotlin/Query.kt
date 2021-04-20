data class Query(
    val queryId: Int,
    val firstStaId: Int,
    val range: Int,
    val timeout: Long
)

data class Response(
    val queryId: Int,
    val map: Map<Int, SatelliteAPI.Status>,
    val percentage: Double
)