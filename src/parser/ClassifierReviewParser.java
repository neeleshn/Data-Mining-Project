package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Review;
import model.ReviewSet;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utils.Date;
import utils.FileIO;
import utils.JSONParserUtil;
import feature_parsers.FeatureInstance;

public class ClassifierReviewParser {


	public static Map<String, String> buildReviewSetMap(String filePath){

		Map<String, String> map = new HashMap<>();

		try{
			BufferedReader br = new BufferedReader(new FileReader(filePath));

			String line;
			while ((line = br.readLine()) != null) {
				ArrayList<String> review = getReviewEntry(line);
				map.put(review.get(0), review.get(1)+"\t"+review.get(2));
			}
			br.close();
		}catch(Exception e){

		}

		System.out.println("Map size: "+map.size());
		return map;
	}

	public static Map<String, String> buildTestReviewSetMap(String filePath){

		Map<String, String> map = new HashMap<>();

		try{
			BufferedReader br = new BufferedReader(new FileReader(filePath));

			String line;
			while ((line = br.readLine()) != null) {
				ArrayList<String> review = getReviewTestEntry(line);
				map.put(review.get(0), review.get(1));
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		System.out.println("Map size: "+map.size());
		return map;
	}

	public static ArrayList<String> getReviewTestEntry(String line) throws ParseException {

		ArrayList<String> reviewEntry = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(line);
		JSONObject jsonStringObject = (JSONObject) obj;
		String review_id = (String) jsonStringObject.get("review_id");
		String review_text = (String) jsonStringObject.get("text");
		reviewEntry.add(0, review_id);
		reviewEntry.add(1, review_text);

		// get businessID

		return reviewEntry;
	}


	public static ArrayList<String> getReviewEntry(String line) throws ParseException {

		ArrayList<String> reviewEntry = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(line);
		JSONObject jsonStringObject = (JSONObject) obj;
		String review_id = (String) jsonStringObject.get("review_id");
		String review_text = (String) jsonStringObject.get("text");
		long related= (long) jsonStringObject.get("related");
		reviewEntry.add(0, review_id);
		reviewEntry.add(1, review_text);
		reviewEntry.add(2, related+"");
		// get businessID

		return reviewEntry;
	}


}
