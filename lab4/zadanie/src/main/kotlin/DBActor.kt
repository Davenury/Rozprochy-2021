import akka.actor.AbstractActor
import akka.actor.Props
import java.sql.Connection

class DBActor(
        private val connection: Connection?
): AbstractActor(){

    override fun createReceive(): Receive {
        return receiveBuilder()
                .match(DBActorUpdate::class.java){
                    DB.addNumberOfMistakes(it.map, this.connection)
                }
                .match(DBQuery::class.java){
                    val result = this.getNumberOfMistakes(it.satelliteId)
                    sender.tell(DBActorResponse(result, it.satelliteId), self)
                }
                .build()
    }

    private fun getNumberOfMistakes(satelliteId: Int): Int?{
        return DB.getNumberOfMistakes(satelliteId, this.connection)
    }

    companion object{
        fun props(connection: Connection?): Props {
            return Props.create(DBActor::class.java, connection)
        }
    }
}