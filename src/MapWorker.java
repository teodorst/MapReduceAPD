import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by teodor on 20.11.2015.
 */

public class MapWorker extends Thread {

    public MapWorkPool mapWP;
    public HashMap<String, Vector<MapSolution>> destinationContainer;

    public MapWorker( MapWorkPool mapWP, HashMap<String, Vector<MapSolution>> destinationContainer ) {
        this.mapWP = mapWP;
        this.destinationContainer = destinationContainer;
    }

    public boolean insideWord( char letter ) {
        return (letter >= 'A' && letter <= 'Z') || (letter >= '0' && letter <= '9')
                || (letter >= 'a' && letter <= 'z');
    }

    public String readFragment(MapTask task) {
        String fragment = new String ();
        int startOffSet = task.getStartOffset();
        int dimension = task.getDimension();
        try {
            RandomAccessFile fileStream = new RandomAccessFile( task.getFileName(), "r");
            byte fragmentCharacters[] = new byte[ dimension ];
            fileStream.seek(startOffSet);
            fileStream.read(fragmentCharacters);

            fragment = new String(fragmentCharacters);

            // case before fragment
            if( insideWord(fragment.charAt(0)) && startOffSet > 0 ) {
                fileStream.seek(startOffSet-1);
                fragmentCharacters = new byte[1];
                fileStream.read( fragmentCharacters );
                if( insideWord( (char)fragmentCharacters[0] ) ) {
                    int index = 1;
                    while( insideWord( fragment.charAt( index ) ) ) {
                        index ++;
                    }
                    fragment = fragment.substring( index );
                }
            }

            //case after fragment
            if( insideWord(fragment.charAt(fragment.length()-1)) ) {
                fileStream.seek(startOffSet + dimension);
                fragmentCharacters = new byte[50];
                if( fileStream.read(fragmentCharacters) > 0  ) ;
                String stringToAppend = new String(fragmentCharacters);
                if( insideWord(stringToAppend.charAt(0)) ) {
                    int index = 1;
                    while( insideWord( stringToAppend.charAt(index) ) ) {
                        index ++;
                    }
                    fragment += stringToAppend.substring(0, index);
                }
            }
            fileStream.close();
        }
        catch (Exception e ) {
            System.out.println("Can't read from file! " + task.getFileName());
            e.printStackTrace();

        }
        //System.out.println(fragment.trim());

        return fragment.trim();
    }



    public void processTask( MapTask task ) {
        String fragment = readFragment(task);
        StringTokenizer token = new StringTokenizer(fragment, " ;:/?~\\.,><~`[]{}()!@#$%^&-_+'=*\"|\t\n\r");
        MapSolution solutionOfTask = new MapSolution();
        while( token.hasMoreTokens() ) {
            String currentWord = token.nextToken();
            if( currentWord.length() > 0 ) {
                solutionOfTask.addInSolution(currentWord);
            }
        }
        synchronized (destinationContainer) {
            Vector<MapSolution> previousSolutions = destinationContainer.get(task.getFileName());
            previousSolutions.add(solutionOfTask);
            destinationContainer.put(task.getFileName(), previousSolutions);
        }
    }


    public void run () {
       // System.out.println("Thread-ul worker " + this.getName() + " a pornit...");
        while(true) {
            MapTask task = mapWP.getWork();
            if( task == null )
                break;
            processTask(task);
        }
        //System.out.println("Thread-ul worker " + this.getName() + " s-a terminat...");
    }
}
