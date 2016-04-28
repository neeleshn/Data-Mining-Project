package utils;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.HashSet;

import org.tartarus.snowball.ext.englishStemmer;

import model.Review;

@SuppressWarnings("ConstantConditions")
public class TextAnalyzer {

	private static HashMap<String,Integer> reviewScoreMap; 
    public static double baseScore = 2;

    public TextAnalyzer() {
        //NLP.init();
    	readReviewScores();
    	System.out.println(reviewScoreMap.size());
    }

    @SuppressWarnings("unchecked")
	public static void readReviewScores(){
    	FileInputStream fis;
    	ObjectInputStream ois;
    	try{
    		fis = new FileInputStream("hadoop/post-processing/hygieneReviews.out");
    		ois = new ObjectInputStream(fis);
    		reviewScoreMap = (HashMap<String,Integer>) ois.readObject();
    		ois.close();
    		
    		System.out.println("Size of Hashmap: " + reviewScoreMap.size());
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
    	}
    	
		

    }
    public static double scoreReviewText(Review review) {
    	/*String newReview = hygieneComment(reviewText,dict);
    	if(newReview.equals("")) {
            return baseScore;
        } else {
        	return scoreHygienceRelatedText(newReview);
        }
        */
    	String reviewID = review.getReviewId();
    	double score;
    	if(reviewScoreMap.containsKey(reviewID)){
    		//System.out.println(reviewScoreMap.get(reviewID).getClass().getName());
    		double sentiment = (double)reviewScoreMap.get(reviewID); 
    		
    		score = sentiment;
    	}
    	else
    		score = baseScore;
    	return score;
        
    }

    public static String hygieneComment(String reviewText, HashSet<String> dict) {
    	
    	englishStemmer stemmer = new englishStemmer();
    	String[] sentences = reviewText.split("\\. ");
        String newReview = "";
        
        for (String sentence : sentences) {
            String[] words = sentence.split(" ");
            for (String eachWord : words) {
                stemmer.setCurrent(eachWord);
                if (stemmer.stem()) {
                    String stemWord = stemmer.getCurrent();
                    if (dict.contains(stemWord)) {
                        newReview += sentence + ". "; // build a new review with sentences related to hygiene only
                        break;
                    }
                }
            }
        }
    	
    	return newReview;     // turn off NLP
    }
    
    public static boolean isHygieneRelated(String reviewText, HashSet<String> dict){
    	for(String dictWord: dict){
    		if(reviewText.contains(dictWord)){
    			
    			return true;
    		}
    	}
    	return false;
    }
}
