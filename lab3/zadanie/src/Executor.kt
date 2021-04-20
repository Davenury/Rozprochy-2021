import org.apache.zookeeper.WatchedEvent
import org.apache.zookeeper.Watcher
import org.apache.zookeeper.ZooKeeper
import java.io.*
import java.util.stream.Collectors
import kotlin.concurrent.thread
import kotlin.system.exitProcess


class Executor(
        hostPort: String,
        zNode: String,
        private var filename: String,
        private var exec: Array<String>) : Watcher, Runnable, DataMonitorListener{

    private var dm: DataMonitor
    private var zooKeeper: ZooKeeper = ZooKeeper(hostPort, 3000, this)
    private var child: Process? = null

    init {
        this.dm = DataMonitor(zooKeeper, zNode, null, this)
    }

    override fun process(event: WatchedEvent?) {
        dm.process(event)
    }

    fun getFamilyNumber(): Int{
        return dm.getFamilyNumber()
    }

    fun getMap(): Map<String, Int>{
        return dm.getMap()
    }

    private val lock = Object()

    override fun run() {
        try{
            synchronized(lock){
                while(!dm.dead){
                    lock.wait()
                }
            }
        } catch (e: InterruptedException){
            e.printStackTrace()
        }
    }

    override fun runExec() {
        println("hello")
    }

    override fun killExec(){
        println("goodbye")
    }
}

fun main(args: Array<String>){
    if(args.size < 4){
        System.err.println("Args length must be larger than three")
        exitProcess(2)
    }
    val hostPort = args[0]
    val zNode = args[1]
    val filename = args[2]
    val exec = args.copyOfRange(3, args.size)
    try{
        val executor = Executor(hostPort, zNode, filename, exec)
        thread {
            while(true){
                val command = readLine()!!
                if(command == "get"){
                    println(executor.getFamilyNumber())
                    println(executor.getMap())
                }
            }
        }.run()
        executor.run()
    } catch (e: Exception){
        e.printStackTrace()
    }
}
