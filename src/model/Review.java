package model;

import utils.Date;

public class Review {

	private String reviewID;
    private String yelpID;

    private int userRating;
    private Date date;
    private String text;
    private int reviewResponseCount;
    // TODO: user profile

    public Review() {
        // Empty Constructor
    }

    public Review(String reviewID,String yelpID, int userRating, String dateString, String text) {
    	this.reviewID = reviewID;
        this.userRating = userRating;
        this.date = new Date(dateString);
        this.text = text;
        this.yelpID = yelpID;
    }
    
    public String getReviewId() {
        return reviewID;
    }

    public void setreviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getYelpID() {
        return yelpID;
    }

    public void setYelpID(String yelpID) {
        this.yelpID = yelpID;
    }

    public int getReviewResponseCount() {
        return reviewResponseCount;
    }

    public void setReviewResponseCount(int reviewResponseCount) {
        this.reviewResponseCount = reviewResponseCount;
    }
}
