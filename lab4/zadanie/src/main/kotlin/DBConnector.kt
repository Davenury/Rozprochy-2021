import java.sql.Connection
import java.sql.DriverManager

object DB {

    fun connect(): Connection? {
        var connection: Connection? = null
        val connectionUrl = "jdbc:sqlite:database.db"
        return DriverManager.getConnection(connectionUrl)
    }

    fun closeConnection(connection: Connection?) {
        connection?.close()
    }

    fun initDatabase(connection: Connection?){
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
        val sql = """"""
    }

}