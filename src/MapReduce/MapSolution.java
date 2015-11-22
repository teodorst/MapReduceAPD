package MapReduce;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by teodor on 20.11.2015.
 */
public class MapSolution {

    HashMap<Integer, Integer> lenghtMap;
    ArrayList<String> maximalWords;

    public MapSolution() {
        lenghtMap = new HashMap<Integer, Integer>();
        maximalWords = new ArrayList<String>();
    }

    public void addInSolution (String newWord) {
        int lenght = newWord.length();
        Integer previousValue = lenghtMap.get(lenght);
        if( previousValue == null ) {
            lenghtMap.put(lenght, 1);
            maximalWords.add(newWord);
        }
        else {
            lenghtMap.put(lenght, previousValue + 1);
            if( !maximalWords.contains(newWord) ) {
                maximalWords.add(newWord);
            }
        }
    }

    public void addMaximalWord(String word) {
        maximalWords.add(word);
    }

}
