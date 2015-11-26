import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by teodor on 20.11.2015.
 */

public class MapSolution {

    private HashMap<Integer, Integer> lenghtMap;
    private Vector<String> maximalWords;
    private double rang;
    private String fileName;

    public MapSolution() {
        lenghtMap = new HashMap<Integer, Integer>();
        maximalWords = new Vector<String>();
        rang = 0;
    }

    public void addInSolution (String newWord) {
        int lenght = newWord.length();
        if( maximalWords.size() == 0 ) {
            maximalWords.add(newWord);
            lenghtMap.put(newWord.length(), 1);
        }
        else {
            Integer previousValue = lenghtMap.get(lenght);
            if( previousValue == null ) {
                lenghtMap.put(lenght, 1);
            }
            else {
                previousValue += 1;
                lenghtMap.put(lenght, previousValue);
            }
            if( newWord.length() > maximalWords.get(0).length() ) {
                maximalWords.clear();
                maximalWords.add(newWord);
            }
            else
            if( newWord.length() == maximalWords.get(0).length() ) {
                maximalWords.add(newWord);
            }
        }

    }

    public void addMapSolution( MapSolution newAddSolution) {
        addMaximalWords(newAddSolution.getMaximalWords());
        for( Map.Entry<Integer,Integer> item : newAddSolution.getLenghtMap().entrySet()  ) {
            addAppearences(item.getKey(), item.getValue());
        }
    }

    public void addAppearences (int wordLenght, int numberOfAppearances) {
        Integer previousValue = lenghtMap.get(wordLenght);
        if( previousValue == null ) {
            lenghtMap.put(wordLenght, numberOfAppearances);
        }
        else {
            lenghtMap.put(wordLenght, previousValue + numberOfAppearances);
        }
    }



    public void addMaximalWords(Vector<String> newWords) {
        if( maximalWords.size() == 0 ) {
            maximalWords.addAll(newWords);
        }
        else
            if( newWords.get(0).length() > maximalWords.get(0).length()) {
                maximalWords.clear();
                maximalWords.addAll(newWords);
            }
        else
            if( newWords.get(0).length() == maximalWords.get(0).length()) {
                for( int i = 0; i < newWords.size(); i ++ ) {
                    if( !maximalWords.contains(newWords.get(i)) ) {
                        maximalWords.add(newWords.get(i));
                    }
                }
            }
    }

    public HashMap<Integer, Integer> getLenghtMap() {
        return lenghtMap;
    }

    public Vector<String> getMaximalWords() {
        return maximalWords;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getRang() {
        return rang;

    }

    public String toString() {
        DecimalFormat myFormat = new DecimalFormat("#.##");
        myFormat.setRoundingMode(RoundingMode.DOWN);
        return fileName + ";" + myFormat.format(rang)
                + ";[" + maximalWords.get(0).length() + "," + maximalWords.size() + "]";
    }

    public void calculateRang(Vector<Integer> fibonacci) {
        float sum = 0;
        int numberOfWords = 0;
        for( Map.Entry<Integer, Integer> item : lenghtMap.entrySet() ) {
            sum += fibonacci.get(item.getKey() + 1) * item.getValue();
            numberOfWords += item.getValue();
        }
        DecimalFormat myFormat = new DecimalFormat("#.##");
        myFormat.setRoundingMode(RoundingMode.DOWN);

        rang = round(sum / numberOfWords, 2) ;
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.DOWN);
        return bd.doubleValue();
    }

}
