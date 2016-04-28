package model;

public class ReviewData {
    private String userId;
    private String reviewId;
    private String text;
    private int coolVotes;
    private int funnyVotes;
    private String businessId;
    private String restaurantId;
    private int stars;
    private String date;
    private int usefulVotes;

    public ReviewData() {
        this.userId = "";
        this.reviewId = "";
        this.text = "";
        this.coolVotes = 0;
        this.funnyVotes = 0;
        this.businessId = "";
        this.restaurantId = "";
        this.stars = 0;
        this.date = "";
        this.usefulVotes = 0;
    }

    public ReviewData(String uId, String reviewId, String text, int cVotes, int uVotes, int fVotes, int stars,
                      String businessId, String restaurantId, String date) {
        this.userId = uId;
        this.reviewId = reviewId;
        this.text = text;
        this.coolVotes = cVotes;
        this.funnyVotes = fVotes;
        this.usefulVotes = uVotes;
        this.businessId = businessId;
        this.restaurantId = restaurantId;
        this.stars = stars;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCoolVotes() {
        return coolVotes;
    }

    public void setCoolVotes(int coolVotes) {
        this.coolVotes = coolVotes;
    }

    public int getFunnyVotes() {
        return funnyVotes;
    }

    public void setFunnyVotes(int funnyVotes) {
        this.funnyVotes = funnyVotes;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUsefulVotes() {
        return usefulVotes;
    }

    public void setUsefulVotes(int usefulVotes) {
        this.usefulVotes = usefulVotes;
    }
}
