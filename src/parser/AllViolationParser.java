package parser;

import model.AllViolationData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AllViolationParser {

    public static List<AllViolationData> readViolationData()
            throws IOException {

        List<AllViolationData> violationList = new ArrayList<AllViolationData>();
        String filePath = "data/AllViolations.csv";
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line;
        int linecount = 0;
        while ((line = br.readLine()) != null) {

            linecount++;

            if (linecount > 1) {
                AllViolationData lineData = getAllViolationData(line);
                violationList.add(lineData);
            }
        }

        br.close();

        return violationList;
    }

    private static AllViolationData getAllViolationData(String line) {

        String[] tokens = line.split(",");
        AllViolationData data = new AllViolationData();

        data.setSerialID(Integer.parseInt(tokens[0]));
        data.setDate(tokens[1]);
        data.setRestaurantID(tokens[2]);
        data.getViolationEntry().setMinorViolationCount(
                Integer.parseInt(tokens[3]));
        data.getViolationEntry().setMajorViolationCount(
                Integer.parseInt(tokens[4]));
        data.getViolationEntry().setMajorViolationCount(
                Integer.parseInt(tokens[5]));

        return data;
    }
}
