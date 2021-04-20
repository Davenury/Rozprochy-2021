import org.apache.zookeeper.WatchedEvent
import org.apache.zookeeper.Watcher
import org.apache.zookeeper.ZooKeeper
import org.barfuin.texttree.api.DefaultNode
import org.barfuin.texttree.api.TextTree
import java.io.*
import java.util.stream.Collectors
import kotlin.concurrent.thread
import kotlin.system.exitProcess


class Executor(
        hostPort: String,
        zNode: String,
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
        child = Runtime.getRuntime().exec(exec)
    }

    override fun killExec(){
        child?.destroy()
    }
}

fun makeTree(map: Map<String, Any>){
    var list = map
            .toSortedMap(compareBy { it })
            .map { it.key }
            .map { it.substring(1) }
            .map { it.split("/") }
            .toList()
    val root = DefaultNode(list[0][0])
    list = list.subList(1, list.lastIndex + 1)
    for(elem in list){
        addElemToTree(root, elem)
    }
    val rendered = TextTree.newInstance().render(root)
    println(rendered)
}

fun addElemToTree(root: DefaultNode, elem: List<String>){
    val listWithoutRoot = elem.subList(1, elem.lastIndex + 1)
    if(listWithoutRoot.size == 1){
        root.addChild(DefaultNode(listWithoutRoot[0]))
        return
    }
    for(node in root.children){
        if(node.text == listWithoutRoot[0]){
            addElemToTree(node, listWithoutRoot)
        }
    }
}

fun main(args: Array<String>){
    val exec = args.copyOfRange(0, args.size)
    try{
        val executor = Executor("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", "/z", exec)
        thread {
            while(true){
                val command = readLine()!!
                if(command == "get"){
                    println(executor.getFamilyNumber())
                }
                if(command == "tree"){
                    makeTree(executor.getMap())
                }
            }
        }.run()
        executor.run()
    } catch (e: Exception){
        e.printStackTrace()
    }
}
