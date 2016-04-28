package utils;

import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import feature_parsers.CategoryMap;

public class JSONParserUtil {

    public static String parseReviewText(JSONObject jsonStringObject) {
        return (String) jsonStringObject.get("text");
    }

    public static Date parseDate(JSONObject jsonStringObject) {

        String dateString = (String) jsonStringObject.get("date");
        return new Date(dateString);
    }

    public static int parseUserRating(JSONObject jsonStringObject) {
        return (int) (long) jsonStringObject.get("stars");
    }

    public static String getBusinessID(JSONObject jsonStringObject) {
        return (String) jsonStringObject.get("business_id");
    }
    
    public static String getReviewID(JSONObject jsonStringObject) {
        return (String) jsonStringObject.get("review_id");
    }
    

    public static int parseReviewResponse(JSONObject jsonStringObject) {
        int funnyCount = parseVoteTypes(jsonStringObject, "funny");
        int usefulCount = parseVoteTypes(jsonStringObject, "useful");
        int coolCount = parseVoteTypes(jsonStringObject, "cool");

        return funnyCount + usefulCount + coolCount;
    }

    public static int parseVoteTypes(JSONObject jsonStringObject, String voteType) {
        JSONObject votes = (JSONObject) jsonStringObject.get("votes");

        return (int) (long) votes.get(voteType);
    }

    // Business.json

    public static String parseNoiseLevel(JSONObject jsonStringObject) {
        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");
        if (attributes.get("Noise Level") == null)
            return "average";

        return (String) attributes.get("Noise Level");
    }

    public static String parseBusinessType(JSONObject jsonStringObject) {
        return (String) jsonStringObject.get("type");
    }

    public static int parseStars(JSONObject jsonStringObject) {

        return (int) (double) jsonStringObject.get("stars");
    }

    public static int parsePriceRange(JSONObject jsonStringObject) {
        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");

        if (attributes.get("Price Range\"") == null) return 1;
        return (int) (long) attributes.get("Price Range");
    }
    public static String parseCuisineType(JSONObject jsonStringObject) {
    	
    	JSONArray categories = (JSONArray) jsonStringObject.get("categories");
    	
		HashSet<String> catMap = new CategoryMap().getMap();
		
		for(Object entry: categories){
			
			String str = entry.toString();
			if(catMap.contains(str)){
				return str;
			}
		}
		return "Restaurant";
    }
     

    public static boolean parseLateNight(JSONObject jsonStringObject) {

        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");
        JSONObject goodFor = (JSONObject) attributes.get("Good For");

        if (goodFor == null) return false;
        if (goodFor.get("latenight") == null) return false;
        return (boolean) goodFor.get("latenight");
    }

    public static boolean parseDessert(JSONObject jsonStringObject) {

        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");
        JSONObject goodFor = (JSONObject) attributes.get("Good For");

        if (goodFor == null) return false;
        if (goodFor.get("dessert") == null) return false;
        return (boolean) goodFor.get("dessert");
    }

    public static boolean parseDeliveryOption(JSONObject jsonStringObject) {

        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");

        if (attributes.get("Delivery") == null)
            return false;

        return (boolean) attributes.get("Delivery");
    }

    public static boolean parseUpscale(JSONObject jsonStringObject) {
        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");
        JSONObject ambience = (JSONObject) attributes.get("Ambience");

        if (ambience == null) return false;
        if (ambience.get("upscale") == null) return false;
        return (boolean) ambience.get("upscale");
    }

    public static boolean parseHipster(JSONObject jsonStringObject) {
        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");
        JSONObject ambience = (JSONObject) attributes.get("Ambience");

        if (ambience == null) return false;
        if (ambience.get("hipster") == null) return false;
        return (boolean) ambience.get("hipster");
    }

    public static boolean parseTouristy(JSONObject jsonStringObject) {
        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");
        JSONObject ambience = (JSONObject) attributes.get("Ambience");

        if (ambience == null) return false;
        if (ambience.get("touristy") == null) return false;
        return (boolean) ambience.get("touristy");
    }

    public static boolean parseIntimate(JSONObject jsonStringObject) {
        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");
        JSONObject ambience = (JSONObject) attributes.get("Ambience");

        if (ambience == null) return false;
        if (ambience.get("intimate") == null) return false;
        return (boolean) ambience.get("intimate");
    }

    public static boolean parseRomantic(JSONObject jsonStringObject) {

        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");
        JSONObject ambience = (JSONObject) attributes.get("Ambience");

        if (ambience == null) return false;
        if (ambience.get("romantic") == null) return false;
        return (boolean) ambience.get("romantic");
    }

    public static boolean parseValetParking(JSONObject jsonStringObject) {
        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");
        JSONObject parking = (JSONObject) attributes.get("Parking");

        if (parking == null) return false;
        if (parking.get("valet") == null) return false;

        return (boolean) parking.get("valet");
    }

    public static boolean parseWaiterService(JSONObject jsonStringObject) {
        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");

        if (attributes.get("Waiter Service") == null) return false;

        return (boolean) attributes.get("Waiter Service");
    }

    public static boolean parseAlcoholic(JSONObject jsonStringObject) {
        JSONObject attributes = (JSONObject) jsonStringObject.get("attributes");
        if (attributes.get("Alcohol") == null) {
            return false;
        }

        String result = (String) attributes.get("Alcohol");
        return  (!result.contains("none"));
    }

}
