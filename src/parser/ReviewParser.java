package parser;

import com.csvreader.CsvReader;
import model.ReviewData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewParser {

    private static String filePath = "data/yelp_academic_dataset_review.csv";

    public static List<ReviewData> readReviews() {
        List<ReviewData> list = new ArrayList<ReviewData>();
        try {
            CsvReader reviews = new CsvReader(filePath);
            reviews.readHeaders();
            // Caching results for restaurantID - YelpID mappings
            Map map = RestaurantToYelpIdParser.BusinessToYelpIDMapper();

            while (reviews.readRecord()) {
                String userId = reviews.get("user_id");
                String reviewId = reviews.get("review_id");
                String text = reviews.get("text");
                int coolVotes = Integer.parseInt(reviews.get("votes.cool"));
                int funnyVotes = Integer.parseInt(reviews.get("votes.funny"));
                String businessID = reviews.get("business_id");
//                String restaurantId = yelpToRestaurentParser.getRestaurentIDFromYelpID(businessID);

                /* referencing cache for rest-yelp-ids */
                if (!map.containsKey(businessID)) {
                    continue;
                }

                String restaurantId = (String) map.get(businessID);

                int stars = Integer.parseInt(reviews.get("stars"));
                String date = reviews.get("date");
                int usefulVotes = Integer.parseInt(reviews.get("votes.useful"));

                ReviewData data = new ReviewData(userId, reviewId, text, coolVotes, usefulVotes, funnyVotes,
                        stars, businessID, restaurantId, date);

                list.add(data);
//                System.out.println(index++);
            }
            reviews.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
