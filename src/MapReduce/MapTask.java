package MapReduce;

import java.io.*;


public class MapTask {
    private String fileName;
    private int startOffset, finishOffset;

    public MapTask( String fileName, int startOffset, int finishOffset ) {
        this.fileName = fileName;
        this.startOffset = startOffset;
        this.finishOffset = finishOffset;
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

    public int getFinishOffset() {
        return finishOffset;
    }

    public void setFinishOffset(int finishOffset) {
        this.finishOffset = finishOffset;
    }
}
