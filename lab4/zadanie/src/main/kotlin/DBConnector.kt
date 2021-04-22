import java.sql.Connection
import java.sql.DriverManager

object DB {

    fun connect(): Connection? {
        var connection: Connection? = null
        val connectionUrl = "jdbc:sqlite:database.db"
        connection = DriverManager.getConnection(connectionUrl)
        initDatabase(connection)
        return connection
    }

    fun closeConnection(connection: Connection?) {
        connection?.close()
    }

    private fun initDatabase(connection: Connection?){
        deleteTable(connection)
        createTable(connection)
        makeZeroDatabase(connection)
    }

    private fun createTable(connection: Connection?){
        val sql = """CREATE TABLE IF NOT EXISTS satellites (
                        id integer PRIMARY KEY,
                        satelliteId integer,
                        numberOfErrors integer
                    );"""
        connection?.createStatement()?.execute(sql)
    }

    private fun deleteTable(connection: Connection?){
        val sql = """DROP TABLE IF EXISTS satellites"""
        connection?.createStatement()?.execute(sql)
    }

    private fun makeZeroDatabase(connection: Connection?){
        val sql = """INSERT INTO satellites(satelliteId,numberOfErrors) VALUES(?,?)"""
        for (i in 100..199){
            val statement = connection?.prepareStatement(sql)
            statement?.setInt(1, i)
            statement?.setInt(2, 0)
            statement?.executeUpdate()
        }
    }

    fun addNumberOfMistakes(map: Map<Int, SatelliteAPI.Status>, connection: Connection?){
        val sqlUpdate = ("UPDATE satellites SET numberOfErrors = ? "
                + "WHERE satelliteId = ?")
        val sqlQuery = ("SELECT numberOfErrors from satellites where satelliteId = ?")
        map.keys.forEach {
            val queryStatement = connection?.prepareStatement(sqlQuery)
            queryStatement?.setInt(1, it)
            val result = queryStatement?.executeQuery()
            val updateStatement = connection?.prepareStatement(sqlUpdate)
            updateStatement?.setInt(1, result!!.getInt("numberOfErrors") + 1)
            updateStatement?.setInt(2, it)
            updateStatement?.executeUpdate()
        }
    }

    fun getNumberOfMistakes(satelliteId: Int, connection: Connection?): Int? {
        val sqlQuery = ("SELECT numberOfErrors from satellites where satelliteId = ?")
        val statement = connection?.prepareStatement(sqlQuery)
        statement?.setInt(1, satelliteId)
        val result = statement?.executeQuery()
        return result?.getInt("numberOfErrors")
    }

}