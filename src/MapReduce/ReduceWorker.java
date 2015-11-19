package MapReduce;

/**
 * Created by teodor on 20.11.2015.
 */
public class ReduceWorker extends Thread {

    private MapWorkPool workPool;


    public ReduceWorker(MapWorkPool workPool ) {
        this.workPool = workPool;
    }

    public void processMapTask( MapTask mapTask) {

    }

    public void run() {
        System.out.println("Thread-ul worker " + this.getName() + " a pornit...");
        while (true) {
            MapTask ps = workPool.getWork();
            if (ps == null)
                break;
            processMapTask(ps);
        }
        System.out.println("Thread-ul worker " + this.getName() + " s-a terminat...");
    }
}
