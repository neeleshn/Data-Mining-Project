package feature_parsers;

import model.AllViolationData;
import parser.AllViolationParser;
import parser.RestaurantToYelpIdParser;
import utils.Date;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureInstance {

    String businessID;
    String yelpID;
    Date startDate;
    Date endDate;

    public FeatureInstance(String businessID, Date startDate, Date endDate) {

        this.businessID = businessID;
        this.startDate = startDate;
        this.endDate = endDate;

        // TODO: use map to populate yelpID
    }


    public static FeatureInstance getInstance(AllViolationData entry, List<AllViolationData> list) {

        String businessID = entry.getRestaurantID();
        Date endDate = entry.getDate();
        Date startDate = calculateStartDate(entry, list);

        return new FeatureInstance(businessID, startDate, endDate);
    }

    private static Date calculateStartDate(AllViolationData entry, List<AllViolationData> list) {

        Date nearestEarlierDate = Date.getBaseDate();
        Date currentDate = entry.getDate();

        for (AllViolationData data : list) {

            String businessID = data.getRestaurantID();
            Date date = data.getDate();

            // if restaurantID matches
            if (entry.getRestaurantID().equals(businessID)) {

                // if the date is earlier
                if (Date.isEarlier(currentDate, date)) {

                    // update date, IF necessary
                    if (Date.isLater(nearestEarlierDate, date))
                        nearestEarlierDate = date;
                }
            }
        }

        // TODO: handle case for: earlier date does not exist >> DONE
        if (nearestEarlierDate.equals(Date.getBaseDate())) {
            nearestEarlierDate = Date.getSixMonthsEarlier(currentDate);
        }

        return nearestEarlierDate;
    }


    // TODO: results can be stored in a file for faster IO
    public static Map<Integer, FeatureInstance> getMap_Instances() throws IOException {
        Map<Integer, FeatureInstance> map = new HashMap<>();
        List<AllViolationData> list = AllViolationParser.readViolationData();

        int index = 1;
        for (AllViolationData data : list) {
            //System.out.println(index++ + " / " + list.size());
            int serialID = data.getSerialID();
            FeatureInstance instance = getInstance(data, list);

            map.put(serialID, instance);
        }

        populateYelpIds(map);

        return map;
    }

    private static void populateYelpIds(Map<Integer, FeatureInstance> map) throws IOException {

        Map<String, String> yelpLookup = RestaurantToYelpIdParser.BusinessToYelpIDMapper();

        for (int serialID : map.keySet()) {

            FeatureInstance instance = map.get(serialID);
            String businessID = instance.businessID;

            // update yelpID
            instance.yelpID = yelpLookup.get(businessID);
        }
    }

}
