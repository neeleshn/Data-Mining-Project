package feature_parsers;

import model.AllViolationData;
import parser.AllViolationParser;
import utils.Date;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PastPenaltyParser {

    public static Map<Integer, Double> buildPastViolationsMap(List<AllViolationData> entries) {
        Map<Integer, Double> map = new HashMap<>();

        for (AllViolationData entry : entries) {

            int penaltyCount = 0;
            int totalPenaltyScore = 0;

            String restID = entry.getRestaurantID();
            Date date = entry.getDate();

            for (AllViolationData lookupEntry : entries) {

                if (entry != lookupEntry) {

                    String currentRestID = lookupEntry.getRestaurantID();
                    Date currentDate = lookupEntry.getDate();

                    if (restID.equals(currentRestID)) {

                        if (Date.isEarlier(date, currentDate)) {
                            totalPenaltyScore += TargetVariableParser.calculateScore(lookupEntry.getViolationEntry(), 1, 1,
                                    1);
                            penaltyCount++;
                        }
                    }
                }
            }

            if (penaltyCount != 0) {
                map.put(entry.getSerialID(), (double) totalPenaltyScore / (double) penaltyCount);
            } else {
                map.put(entry.getSerialID(), 0d);   // TODO: ignore or use zero ?
            }
        }

        return map;
    }
}
