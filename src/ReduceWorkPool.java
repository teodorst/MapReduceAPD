import java.util.LinkedList;

/**
 * Created by teodor on 20.11.2015.
 */
public class ReduceWorkPool {

    private LinkedList<ReduceTask> tasks;
    private int numberOfThreads;
    private int waitingThreads = 0;
    private boolean ready = false; // mark if the pool still got tasks

    public ReduceWorkPool (int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        tasks = new LinkedList<ReduceTask>();
    }

    public synchronized ReduceTask getWork() {
        if (tasks.size() == 0) { // workpool gol
            waitingThreads ++;
            // no tasks in pool and no active worker
            if (waitingThreads == numberOfThreads) {
                ready = true;
                // map part is over. notify other threads
                notifyAll();
                return null;
            } else {
                while (!ready && tasks.size() == 0) {
                    try {
                        this.wait(); // threadul care apeleaza metoda intra in wait()
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }

                if (ready)
                    // the Map part is over
                    return null;

                waitingThreads --;
            }
        }

        return tasks.remove();
    }

    /**
     * Functie care introduce un task in workpool.
     * @param task - task-ul care trebuie introdus
     */
    synchronized void putWork(ReduceTask task) {
        tasks.add(task);
        //notify a waiting thread
        this.notify();
    }


}
