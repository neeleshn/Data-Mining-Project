package model;

import utils.TextAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class ReviewSet {

    private List<Review> reviewSet;
    private double averageLength;
    private double averageRating;
    private int reviewCount;
    private int reviewResponse;
    private double textAnalysisScore;
   
    public ReviewSet() {
        this.reviewSet = new ArrayList<>();
    }

    public static void FillEntriesInReviewSet(ReviewSet reviewSet, TextAnalyzer textAnalyser) {
        reviewSet.setAverageLength(getAverageLengthFromSet(reviewSet.getReviewSet()));
        reviewSet.setAverageRating(getAverageRating(reviewSet.getReviewSet()));
        reviewSet.setReviewCount(getReviewCount(reviewSet.getReviewSet()));
        reviewSet.setReviewResponse(getReviewResponseCount(reviewSet.getReviewSet()));
        reviewSet.setTextAnalysisScore(getTextAnalysisScore(reviewSet.getReviewSet(), textAnalyser));
    }

    // TODO: method that text analysis
    /*
        INPUT: List of Reviews for an inspection set
        OUTPUT: a numeric score, after taking into account the following:
         1. review's sentiment analysis score from StanfordNLP
         2. score's boosting considering the user that posted the review (TODO: in Review Class)
         3. score's boosting considering the kind of votes that review got
         4. More..
      */
    private static double getTextAnalysisScore(List<Review> reviewSet, TextAnalyzer textAnalyser) {
    	
    	double total =0.0;
    	int count=0;
    	for (Review review : reviewSet) {
    		
            double tempScore = textAnalyser.scoreReviewText(review);
            if(tempScore != -10.0){
            	count++;
            	total += tempScore;
            }
             
            
        }

        // take average of all reviews
        double avg = total/count;
        // faking results for now
        return avg;
    }

    private static int getReviewResponseCount(List<Review> reviewSet) {
        int responseSum = 0;

        for (Review review : reviewSet) {
            responseSum += review.getReviewResponseCount();
        }

        return responseSum;
    }

    private static int getReviewCount(List<Review> reviewSet) {

        return reviewSet.size();
    }

    private static double getAverageRating(List<Review> reviewSet) {
        double average;
        double ratingSum = 0;

        for (Review review : reviewSet) {
            ratingSum += review.getUserRating();
        }

        average = ratingSum / reviewSet.size();
        return average;
    }

    private static double getAverageLengthFromSet(List<Review> reviewSet) {
        String space = " ";
        double average;
        double totalLength = 0;

        for (Review review : reviewSet) {

            int currentLength = review.getText().split(space).length;
            totalLength += currentLength;
        }

        average = totalLength / reviewSet.size();
        return average;
    }

    public List<Review> getReviewSet() {
        return reviewSet;
    }

    public void setReviewSet(List<Review> reviewSet) {
        this.reviewSet = reviewSet;
    }

    public double getAverageLength() {
        return averageLength;
    }

    public void setAverageLength(double averageLength) {
        this.averageLength = averageLength;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getReviewResponse() {
        return reviewResponse;
    }

    public void setReviewResponse(int reviewResponse) {
        this.reviewResponse = reviewResponse;
    }

    public double getTextAnalysisScore() {
        return textAnalysisScore;
    }

    public void setTextAnalysisScore(double textAnalysisScore) {
        this.textAnalysisScore = textAnalysisScore;
    }
}
