package wordleGenerator;/* Authored by Kushagra on 4/23/2016. */

import model.Review;
import model.ReviewSet;
import org.json.simple.parser.ParseException;
import utils.FileIO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static wordleGenerator.TopKWordsGenerator.getWordsFromSentence;

@SuppressWarnings("Duplicates")
public class TopKWordFeatureList {


    public static void main(String[] args) throws IOException, ParseException {


//        Map<Integer, Map<String, Integer>> featureListMap = getTextFeatureListMap(reviewSetMapper);



    }

    public static Map<Integer, Map<String, Integer>> getTextFeatureListMap(Map<Integer, ReviewSet> reviewSetMapper) throws IOException, ParseException {

        Map<Integer, Map<String, Integer>> featureListMap = new HashMap<>();

//        Map<Integer, FeatureInstance> instanceMap = FeatureInstance.getMap_Instances();
//        Map<Integer, ReviewSet> reviewSetMapper = buildReviewSetMap(instanceMap);

        String[] topKWords = getTopKWords();

        for (int serialID : reviewSetMapper.keySet()) {

            ReviewSet reviewSet = reviewSetMapper.get(serialID);
            List<Review> reviews = reviewSet.getReviewSet();
            Map<String, Integer> topKWordsFeatureList = buildTopKWordsFeature(topKWords);
            for (Review review : reviews) {
                String reviewText = review.getText();

                String[] reviewTextWords = getWordsFromSentence(reviewText);
                updateTopKWordsFeature(topKWordsFeatureList, reviewTextWords);
            }

            featureListMap.put(serialID, topKWordsFeatureList);
        }

        return featureListMap;
    }

    private static void storeMapToFile(Map<Integer, Map<String, Integer>> featureListMap, String[] topKWords) {

    }

    private static String getTopLine(String[] featureListMap) {
        return null;
    }

    private static void updateTopKWordsFeature(Map<String, Integer> wordCountMap, String[] reviewTextWords) {

        for (String word : reviewTextWords) {

            // only consider top k words
            if (wordCountMap.containsKey(word)) {

                int wordCount = wordCountMap.get(word);
                wordCountMap.put(word, wordCount + 1);      // increment count of the word found in review
            }
        }
    }

    // create an empty hashmap with placeholders for each of the topK words
    private static Map<String, Integer> buildTopKWordsFeature(String[] topKWords) {
        Map<String, Integer> map = new HashMap<>();

        for (String word : topKWords) {
            map.put(word, 0);
        }

        return map;
    }

    public static String[] getTopKWords() throws IOException {

        System.out.println("Getting top k words from cache...");

        String filePath = "data/wordle/topwords.txt";
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line;
        int index = 0;
        int totalLines = FileIO.countLines(filePath);
        System.out.println("Total Lines: " + totalLines);

        String[] topWords = new String[totalLines];

        while ((line = br.readLine()) != null) {

            topWords[index] = line;
            System.out.println(index++ + " / " + totalLines);
        }

        br.close();

        return topWords;
    }
}
