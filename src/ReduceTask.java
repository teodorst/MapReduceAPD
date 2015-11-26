import java.util.Vector;

/**
 * Created by teodor on 23.11.2015.
 */
public class ReduceTask {
    private String fileName;
    private Vector<MapSolution> mapResults;

    public ReduceTask( String fileName, Vector<MapSolution> mapResults) {
        this.fileName = fileName;
        this.mapResults = mapResults;
    }


    public String getFileName() {
        return fileName;
    }

    public Vector<MapSolution> getMapResults() {
        return mapResults;
    }
}
