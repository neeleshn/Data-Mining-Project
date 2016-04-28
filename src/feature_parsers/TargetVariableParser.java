package feature_parsers;

/*
Input: penalty_score_formula
MAP : serialID -- ()
 */


import model.AllViolationData;
import model.ViolationEntry;
import parser.AllViolationParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TargetVariableParser {

    public static void printMap(Map<Integer, Double> map) {

        for (int serialID : map.keySet()) {

            double score = map.get(serialID);

        }
    }
    
    public static void printViolationMap() throws IOException {

    	Map<Integer, ViolationEntry> map = getMap_TargetVariables();
        for (int serialID : map.keySet()) {

            ViolationEntry score = map.get(serialID);

            System.out.println(serialID + "\t: " + score);
        }
    }

    // returns the map of serialID and penalty score
    public static Map<Integer, Double> getMap_TargetVariable(int v1, int v2, int v3) throws IOException {

        HashMap<Integer, Double> map = new HashMap<>();

        List<AllViolationData> rawViolationEntries = AllViolationParser.readViolationData();

        for (AllViolationData entry : rawViolationEntries) {

            int serialID = entry.getSerialID();
            double score = calculateScore(entry.getViolationEntry(), v1, v2, v3);

            map.put(serialID, score);
        }

        return map;
    }
    
    public static Map<Integer, ViolationEntry> getMap_TargetVariables() throws IOException {

        HashMap<Integer, ViolationEntry> map = new HashMap<>();

        List<AllViolationData> rawViolationEntries = AllViolationParser.readViolationData();

        for (AllViolationData entry : rawViolationEntries) {

            int serialID = entry.getSerialID();
            ViolationEntry score = entry.getViolationEntry();

            map.put(serialID, score);
        }

        return map;
    }
   
    public static double calculateScore(ViolationEntry violationEntry, int v1, int v2, int v3) {

        return v1 * violationEntry.getMinorViolationCount() + v2 * violationEntry.getMajorViolationCount() + v3 *
                violationEntry.getSevereViolationCount();
    }
}
