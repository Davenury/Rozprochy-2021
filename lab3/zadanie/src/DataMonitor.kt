import org.apache.zookeeper.*
import org.apache.zookeeper.data.Stat
import kotlin.collections.HashMap

class DataMonitor : Watcher, AsyncCallback.ChildrenCallback, AsyncCallback.StatCallback {

    private var familyNum: Int = 0
    private var childNum: Int = 0
    private var zooKeeper: ZooKeeper
    private var zNode: String
    private var watcher: Watcher? = null
    var dead: Boolean = false
    private var listener: DataMonitorListener
    private var map: HashMap<String, Int> = HashMap()

    constructor(zk: ZooKeeper, zNode: String, watcher: Watcher?, listener: DataMonitorListener){
        this.zooKeeper = zk
        this.zNode = zNode
        this.watcher = watcher
        this.listener = listener
    }

    override fun process(event: WatchedEvent?) {
        if(event?.type == Watcher.Event.EventType.NodeCreated){
            map[event.path] = 1
            if(event.path == "/z")
                this.listener.runExec()
        }
        if(event?.type == Watcher.Event.EventType.NodeDeleted){
            map.remove(event.path)
            if(event.path == "/z")
                this.listener.killExec()
        }
        zooKeeper.getChildren(zNode, true, this, null)
        zooKeeper.exists(zNode, true, this, null)
        watcher?.process(event)
    }

    override fun processResult(rc: Int, path: String?, ctx: Any?, children: MutableList<String>?) {
        path?.let{ map[it] = 1 }
        children?.forEach {
            zooKeeper.getChildren("$path/$it", true, this, null)
        }
    }

    fun getMap(): Map<String, Int>{
        return this.map
    }

    fun getFamilyNumber(): Int{
        return this.map.size - 1
    }

    override fun processResult(rc: Int, path: String?, ctx: Any?, children: Stat?) {

    }
}

interface DataMonitorListener {
    fun runExec()
    fun killExec()
}