package labWorkers;

import java.util.StringTokenizer;
import java.util.Vector;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

/**
 * Clasa ce reprezinta o solutie partiala pentru problema de rezolvat. Aceste
 * solutii partiale constituie task-uri care sunt introduse in workpool.
 */
class PartialSolution {
	int currentLine;
	int [][]tablaSah;
	int newQueen;

	public PartialSolution() {
	}
	
	public PartialSolution( int currentLine, int[][] tablaSah, int newQueen ) {
		this.currentLine = currentLine;
		this.tablaSah = tablaSah;
		tablaSah[currentLine][newQueen] = 1;
		this.newQueen = newQueen;
	}
	
	public boolean verificareSolutie() {
		int k, h;
		
		k = currentLine - 2;
		h = newQueen - 1;
		while( k > 0 && h > 0 ) {
			if( tablaSah[k][h] == 1 )
				return false;
			k --;
			h --;
		}
		k = currentLine - 2;
		h = newQueen + 1;
		while( k > 0 && h < 4 ) {
			if( tablaSah[k][h] == 1 )
				return false;
			k --;
			h ++;
		}
		return true;
	}
	
	public String toString() {
		return "";
	}
}

/**
 * Clasa ce reprezinta un thread worker.
 */
class Worker extends Thread {
	WorkPool wp;

	public Worker(WorkPool workpool) {
		this.wp = workpool;
	}

	/**
	 * Procesarea unei solutii partiale. Aceasta poate implica generarea unor
	 * noi solutii partiale care se adauga in workpool folosind putWork().
	 * Daca s-a ajuns la o solutie finala, aceasta va fi afisata.
	 */
	void processPartialSolution(PartialSolution ps) {
		int p,t;
//		for ( p = 0; p < 4; p ++ ) {
//			for ( t = 0; t < 4; t ++ ) {
//				System.out.print( ps.tablaSah[p][t] + " ");
//			}
//			System.out.print("\n");
//		}
		if( ps.currentLine == 2 ) {
			ps.currentLine ++;
			int i;
			for ( i = 0; i < 4; i ++ )
			{
				ps.tablaSah[ps.currentLine][i] = 1;
				ps.newQueen = i;
				if( ps.verificareSolutie() ) {
					System.out.println("find solution");
				}
			}
		}
		else {
			int i;
			for(i = 0; i < 4; i ++ ) {
				wp.putWork(new PartialSolution(ps.currentLine + 1, ps.tablaSah, i));
			}
		}
	}
	
	public void run() {
		System.out.println("Thread-ul worker " + this.getName() + " a pornit...");
		while (true) {
			PartialSolution ps = wp.getWork();
			if (ps == null)
				break;
			
			processPartialSolution(ps);
		}
		System.out.println("Thread-ul worker " + this.getName() + " s-a terminat...");
	}
}


public class ReplicatedWorkers {

	public static void main(String args[]) throws InterruptedException {
		WorkPool wp = new WorkPool(4);
		Thread p1 = new Worker(wp);
		Thread p2 = new Worker(wp);
		Thread p3 = new Worker(wp);
		Thread p4 = new Worker(wp);
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p1.join();
		p2.join();
		p3.join();
		p4.join();
		System.out.println(wp.getNrAparitii());
	}
	
}


