package feature_parsers;

import model.Review;
import model.ReviewSet;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Date;
import utils.FileIO;
import utils.JSONParserUtil;
import utils.TextAnalyzer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReviewParser {


    private static void storetoFILE(Map<Integer, ReviewSet> reviewSetMapper) {
        // TODO: after complete testing for fasterIO
    }

    public static Map<Integer, ReviewSet> buildReviewSetMap(Map<Integer, FeatureInstance> instanceMap) throws IOException,
            ParseException {

        Map<Integer, ReviewSet> map = new HashMap<>();

        String filePath = "data/yelp_academic_dataset_review.json";
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line;
        int index = 1;
        int totalLines = FileIO.countLines(filePath);
        while ((line = br.readLine()) != null) {
            //System.out.println(line);
            Review reviewEntry = getReviewEntry(line);
            int serialID = locateSerialID(reviewEntry, instanceMap);

            insertToMap(serialID, reviewEntry, map);

            //System.out.println(index++ + " / " + totalLines);
        }

        br.close();
        TextAnalyzer textAnalyser = new TextAnalyzer();
        PopulateEntriesInMap(map, textAnalyser);
        return map;
    }

    private static void PopulateEntriesInMap(Map<Integer, ReviewSet> map, TextAnalyzer textAnalyser) {

        for (int serialID : map.keySet()) {
            ReviewSet rs = map.get(serialID);
            ReviewSet.FillEntriesInReviewSet(rs, textAnalyser);
        }
    }

    private static void insertToMap(int serialID, Review reviewEntry, Map<Integer, ReviewSet> map) {

        if (map.containsKey(serialID)) {
            ReviewSet reviewSet = map.get(serialID);
            reviewSet.getReviewSet().add(reviewEntry);
        } else {
            ReviewSet reviewSet = new ReviewSet();
            reviewSet.getReviewSet().add(reviewEntry);

            map.put(serialID, reviewSet);
        }
    }

    private static int locateSerialID(Review reviewEntry, Map<Integer, FeatureInstance> instanceMap) {
        Date currentDate = reviewEntry.getDate();
        String yelpID = reviewEntry.getYelpID();

        for (int serialID : instanceMap.keySet()) {

            FeatureInstance instance = instanceMap.get(serialID);
            Date startDate = instance.startDate;
            Date endDate = instance.endDate;

            // match restaurant
            if (instance.yelpID.equals(yelpID)) {
                // match dates
                if (Date.liesInBetween(startDate, endDate, currentDate)) {
                    return serialID;
                }
            }
        }

        // TODO: error ?
        return 0;
    }


    // Parse JSON line / CSV
    private static Review getReviewEntry(String line) throws ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(line);

        // get businessID
        JSONObject jsonStringObject = (JSONObject) obj;

        String reviewID = JSONParserUtil.getReviewID(jsonStringObject);
        String businessID = JSONParserUtil.getBusinessID(jsonStringObject);
        int userRating = JSONParserUtil.parseUserRating(jsonStringObject);
        Date date = JSONParserUtil.parseDate(jsonStringObject);
        String reviewText = JSONParserUtil.parseReviewText(jsonStringObject);
        int reviewResponse = JSONParserUtil.parseReviewResponse(jsonStringObject);

        Review reviewEntry = new Review();
        reviewEntry.setreviewID(reviewID);
        reviewEntry.setDate(date);
        reviewEntry.setText(reviewText);
        reviewEntry.setUserRating(userRating);
        reviewEntry.setYelpID(businessID);
        reviewEntry.setReviewResponseCount(reviewResponse);

        return reviewEntry;
    }
}
