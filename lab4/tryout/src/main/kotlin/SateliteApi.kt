import java.util.*

object SatelliteAPI {
    fun getStatus(satelliteIndex: Int): Status {
        val rand = Random()
        try {
            Thread.sleep(100 + rand.nextInt(400).toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val p = rand.nextDouble()
        if (p < 0.8) return Status.OK else if (p < 0.9) return Status.BATTERY_LOW else if (p < 0.95) return Status.NAVIGATION_ERROR
        return Status.PROPULSION_ERROR
    }

    enum class Status {
        OK, BATTERY_LOW, PROPULSION_ERROR, NAVIGATION_ERROR
    }
}