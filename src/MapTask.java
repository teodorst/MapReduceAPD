import java.io.*;


public class MapTask {
    private String fileName;
    private int startOffset, dimension;

    public MapTask( String fileName, int startOffset, int dimension ) {
        this.fileName = fileName;
        this.startOffset = startOffset;
        this.dimension = dimension;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
    public String toString() {
        return  fileName + " " + startOffset + " +  " + dimension;
    }
}
