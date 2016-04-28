package model;

public class RestaurantToYelpIdData {
    private String restaurantID;
    private String yelpID;

    public RestaurantToYelpIdData() {
        restaurantID = "";
        yelpID = "";
    }

    public RestaurantToYelpIdData(String restID, String yelpID) {
        this.restaurantID = restID;
        this.yelpID = yelpID;
    }


    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getYelpID() {
        return yelpID;
    }

    public void setYelpID(String yelpID) {
        this.yelpID = yelpID;
    }

    @Override
    public String toString() {
        String prefix = "RestaurantID : Yelp ID :: ";
        String colon = " : ";
        String newLine = "\n";

        return prefix + restaurantID + colon + yelpID + newLine;
    }
}
