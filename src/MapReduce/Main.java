package MapReduce;


import java.io.*;
import java.util.HashMap;
import java.util.Vector;

public class Main {

    public static void main (String [] args) {

        HashMap<String, Vector<MapSolution>> reduceTasks = new HashMap<String, Vector<MapSolution>>();
        BufferedReader inputFile = null;
        PrintWriter outputFile = null;
        try {
            inputFile = new BufferedReader( new FileReader( new File( args[1] ) ) );
            outputFile = new PrintWriter( new FileWriter( new File(args[2] ) ) );
            int offset = Integer.parseInt(inputFile.readLine(), 10);
            int numberOfFiles = Integer.parseInt(inputFile.readLine(), 10);
            String fileName;
            for ( int i = 0; i < numberOfFiles; i ++ ) {
                fileName = inputFile.readLine();
                reduceTasks.put(fileName, new Vector<MapSolution>());
                
            }
        }
        catch( IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't open the files");
        }

    }


}
