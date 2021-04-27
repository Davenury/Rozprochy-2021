import akka.actor.AbstractActor
import akka.actor.Props
import java.time.Instant

class Satellite(
        private val id: Int
): AbstractActor(){

    override fun createReceive(): Receive {
        return receiveBuilder()
                .match(SatelliteAsk::class.java) {
                    val start = Instant.now()
                    val status = ask()
                    sender.tell(
                            SatelliteResponse(it.satelliteId, status, start, Instant.now(), it.internalQuery),
                            self
                    )
                }
                .build()
    }

    private fun ask(): SatelliteAPI.Status {
        return SatelliteAPI.getStatus(this.id)
    }

    companion object{
        fun props(id: Int): Props {
            return Props.create(Satellite::class.java, id)
        }
    }
}