package feature_parsers;

import model.BusinessSet;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.FileIO;
import utils.JSONParserUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BusinessParser {

    protected static Map<Integer, BusinessSet> buildBusinessInstanceMap(Map<Integer, FeatureInstance> instanceMap, Map<String,
            BusinessSet> businessSetMapper) {

        Map<Integer, BusinessSet> map = new HashMap<>();

        for (int serialID : instanceMap.keySet()) {

            String yelpID = instanceMap.get(serialID).yelpID;
            BusinessSet businessSet = businessSetMapper.get(yelpID);

            map.put(serialID, businessSet);
        }

        return map;
    }

    protected static Map<String, BusinessSet> buildBusinessSetMap(Map<Integer, FeatureInstance> instanceMap) throws
            IOException, ParseException {

        Map<String, BusinessSet> map = new HashMap<>();

        String filePath = "data/yelp_academic_dataset_business.json";
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line;
        int index = 1;
        int totalLines = FileIO.countLines(filePath);
        while ((line = br.readLine()) != null) {
           // System.out.println(line);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(line);
            JSONObject jsonStringObject = (JSONObject) obj;

            String yelpID = parseYelpID(jsonStringObject);

            // if the entries have already NOT been calculated
            if (!map.containsKey(yelpID)) {
                BusinessSet businessSet = parseBusinessSet(jsonStringObject);

                if (businessSet == null) continue;

                map.put(yelpID, businessSet);
            }

           // System.out.println(index++ + " / " + totalLines);
        }

        br.close();

        return map;
    }

    private static String parseYelpID(JSONObject jsonStringObject) {
        return (String) jsonStringObject.get("business_id");
    }

    private static BusinessSet parseBusinessSet(JSONObject jsonStringObject) {
        BusinessSet businessSet = new BusinessSet();

        // TODO: Bad data
        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");
        if (attributes == null) return null;

        businessSet.setAlcoholic(JSONParserUtil.parseAlcoholic(jsonStringObject));
        businessSet.setWaiterService(JSONParserUtil.parseWaiterService(jsonStringObject));
        businessSet.setValetParking(JSONParserUtil.parseValetParking(jsonStringObject));
        businessSet.setRomantic(JSONParserUtil.parseRomantic(jsonStringObject));
        businessSet.setIntimate(JSONParserUtil.parseIntimate(jsonStringObject));
        businessSet.setTouristy(JSONParserUtil.parseTouristy(jsonStringObject));
        businessSet.setHipster(JSONParserUtil.parseHipster(jsonStringObject));
        businessSet.setUpscale(JSONParserUtil.parseUpscale(jsonStringObject));
        businessSet.setDeliveryAvailable(JSONParserUtil.parseDeliveryOption(jsonStringObject));
        businessSet.setGoodForDessert(JSONParserUtil.parseDessert(jsonStringObject));
        businessSet.setLatenight(JSONParserUtil.parseLateNight(jsonStringObject));
        businessSet.setPriceRange(JSONParserUtil.parsePriceRange(jsonStringObject));
        businessSet.setCuisineType(JSONParserUtil.parseCuisineType(jsonStringObject));
        businessSet.setStars(JSONParserUtil.parseStars(jsonStringObject));
        businessSet.setBusinessType(JSONParserUtil.parseBusinessType(jsonStringObject));
        businessSet.setNoiseLevel(JSONParserUtil.parseNoiseLevel(jsonStringObject));

        return businessSet;
    }
}
