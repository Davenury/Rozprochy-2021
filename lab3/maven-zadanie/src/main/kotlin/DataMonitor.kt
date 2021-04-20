import org.apache.zookeeper.*
import org.apache.zookeeper.data.Stat
import kotlin.collections.HashMap
import kotlin.concurrent.thread

class DataMonitor(zk: ZooKeeper, private var zNode: String, private var watcher: Watcher?, private var listener: DataMonitorListener) : Watcher, AsyncCallback.ChildrenCallback, AsyncCallback.StatCallback {

    private var zooKeeper: ZooKeeper = zk
    var dead: Boolean = false
    private var map: HashMap<String, Int> = HashMap()

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
//        if(event?.type == Watcher.Event.EventType.NodeChildrenChanged) {
//            thread {
//                Thread.sleep(2000)
//                println(getFamilyNumber())
//                println(getMap())
//            }.run()
//        }
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