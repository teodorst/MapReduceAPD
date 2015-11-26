import java.util.LinkedList;


public class MapWorkPool {
	private static int nrAparitii = 0;
	int numberOfThreads;
	int waitingThreads = 0;
	public boolean ready = false; // mark if the pool still got tasks
	LinkedList<MapTask> tasks;

	public MapWorkPool(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
		tasks = new LinkedList<MapTask>();
	}


	public synchronized MapTask getWork() {
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
						this.wait();
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
	 * @param sp - task-ul care trebuie introdus 
	 */
	synchronized void putWork(MapTask sp) {
		tasks.add(sp);
		//notify a waiting thread
		this.notify();

	}

}


