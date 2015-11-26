import java.util.HashMap;

/**
 * Created by teodor on 20.11.2015.
 */


public class ReduceWorker extends Thread {

    private ReduceWorkPool reduceWP;
    private HashMap<String, MapSolution> destinationContainer;

    public ReduceWorker(ReduceWorkPool reduceWP, HashMap<String, MapSolution> destinationContainer ) {
        this.reduceWP = reduceWP;
        this.destinationContainer = destinationContainer;
    }

    public void processReduceTask( ReduceTask task) {
        MapSolution centralizedSolution = new MapSolution();
        for(MapSolution item : task.getMapResults() ) {
            centralizedSolution.addMapSolution( item );
        }
        destinationContainer.put(task.getFileName(), centralizedSolution);
    }

    public void run() {
        while (true) {
            ReduceTask task = reduceWP.getWork();
            if (task == null)
                break;
            processReduceTask(task);
        }
    }
}
