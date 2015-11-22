package MapReduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by teodor on 20.11.2015.
 */

public class MapWorker extends Thread {

    public String readFragment(MapTask task) {
        String fragment = null;
        try {
            BufferedReader fileStream = new BufferedReader(new InputStreamReader(new FileInputStream(new File( task.getFileName()))));
            char fragmentCharacters[] = new char[task.getFinishOffset() - task.getStartOffset()];
            fileStream.read(fragmentCharacters, task.getStartOffset(), task.getFinishOffset() - task.getStartOffset() );
            fragment = new String(fragmentCharacters);
        }
        catch (Exception e ) {
            System.out.println("Can't read from file! " + task.getFileName());
        }
        return fragment;
    }

    public void processTask( MapTask task ) {
        String fragment = readFragment(task);
        StringTokenizer token = new StringTokenizer(fragment, ";:/?~\\.,><~`[]{}()!@#$%^&-_+'=*\"| \t\n");
        MapSolution solutionOfTask = new MapSolution();
        while( token.hasMoreElements() ) {
            solutionOfTask.addInSolution(token.nextToken());
        }
    }
}
