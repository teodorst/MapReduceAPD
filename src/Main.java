import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class Main {

    public static void main (String [] args) {

        HashMap<String, Vector<MapSolution>> reduceTasks = new HashMap<String, Vector<MapSolution>>();
        HashMap<String, MapSolution> reduceResults = new HashMap<String, MapSolution>();

        BufferedReader inputFile = null;
        PrintWriter outputFile = null;
        int i, numberOfThreads = Integer.parseInt(args[0], 10);

        MapWorkPool mapWP = new MapWorkPool(numberOfThreads);
        ReduceWorkPool reduceWP = new ReduceWorkPool(numberOfThreads);

        Vector<MapWorker> mapWorkers = new Vector<MapWorker>();
        Vector<ReduceWorker> reduceWorkers = new Vector<ReduceWorker>();

        Vector<String> files = new Vector<String>();
        Vector<Integer> fibonnaci = new Vector<Integer>();
        try {
            long start = System.currentTimeMillis();
            inputFile = new BufferedReader( new FileReader( new File( args[1] ) ) );
            outputFile = new PrintWriter( new FileWriter( new File(args[2] ) ) );
            int offset = Integer.parseInt(inputFile.readLine(), 10);
            int numberOfFiles = Integer.parseInt(inputFile.readLine(), 10);
            String fileName;
            for ( i = 0; i < numberOfFiles; i ++ ) {
                fileName = inputFile.readLine();
                files.add(fileName);
                reduceTasks.put( fileName, new Vector<MapSolution>() );
                reduceResults.put( fileName, new MapSolution());
                int startingOffset = 0;
                int finishOffset = offset;
                File file = new File(fileName);
                long fileLenght = file.length();
                while( startingOffset < fileLenght ) {
                    if ( finishOffset < fileLenght )
                        mapWP.putWork(new MapTask(fileName, startingOffset, offset));
                    else {
                        mapWP.putWork(new MapTask(fileName, startingOffset, (int)fileLenght - startingOffset));
                    }
                    startingOffset += offset;
                    finishOffset += offset;
                }
            }
            for( i = 0; i < numberOfThreads; i++ ) {
                MapWorker newWorker = new MapWorker(mapWP, reduceTasks);
                mapWorkers.add(newWorker);
                newWorker.start();
            }
            for( i = 0; i < numberOfThreads; i++ ) {
                mapWorkers.get(i).join();
            }


            for( Map.Entry<String, Vector<MapSolution>> item : reduceTasks.entrySet() )
                reduceWP.putWork(new ReduceTask(item.getKey(), item.getValue()));
            for( i = 0; i < numberOfThreads; i ++ ) {
                ReduceWorker newWorker = new ReduceWorker(reduceWP, reduceResults);
                reduceWorkers.add(newWorker);
                newWorker.start();
            }
            for( i = 0; i < numberOfThreads; i++ ) {
                reduceWorkers.get(i).join();
            }

            fibonnaci.add(0);
            fibonnaci.add(1);
            for( i = 2; i <= 92; i ++ ) {
                fibonnaci.add(fibonnaci.get(i - 2) + fibonnaci.get(i - 1));
            }

            for( Map.Entry<String, MapSolution> item : reduceResults.entrySet() ) {
                item.getValue().calculateRang(fibonnaci);
            }

            ArrayList<Map.Entry<String, MapSolution>> finalResults = new ArrayList<Map.Entry<String, MapSolution>>(reduceResults.entrySet());
            Collections.sort(finalResults, new Comparator<Map.Entry<String, MapSolution>>() {
                @Override
                public int compare(Map.Entry<String, MapSolution> t1, Map.Entry<String, MapSolution> t2) {
                    if( t1.getValue().getRang() < t2.getValue().getRang() ) {
                        return 1;
                    }
                    else if( t1.getValue().getRang() > t2.getValue().getRang() ) {
                        return -1;
                    }
                    else {
                        return t1.getKey().compareTo(t2.getKey());
                    }

                }
            });
            for( Map.Entry<String, MapSolution> item : finalResults ) {
                DecimalFormat myFormat = new DecimalFormat("#.00");
                myFormat.setRoundingMode(RoundingMode.DOWN);
                outputFile.println(
                        item.getKey() + ";" + myFormat.format(item.getValue().getRang()) + ";[" +
                        item.getValue().getMaximalWords().get(0).length() + "," + new LinkedHashSet<String> (item.getValue().getMaximalWords()).size() + "]"
                );
            }
            long finish = System.currentTimeMillis();
            System.out.println( ((float)(finish - start)) / 1000 );
            outputFile.close();

        }
        catch( IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't open the files");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
